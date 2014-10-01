package learn.english.core.realtime.service;

import learn.english.core.service.ContentService;
import learn.english.model.dto.AdvanceSubtitles;
import learn.english.model.dto.LiveSample;
import learn.english.model.entity.Dictionary;
import learn.english.model.entity.MediaItem;
import learn.english.model.utils.Category;
import learn.english.parser.exception.ParserException;
import learn.english.parser.subtitles.SubtitlesParser;
import learn.english.parser.subtitles.bridge.Subtitles;
import learn.english.parser.subtitles.bridge.SubtitlesUnit;
import learn.english.translator.Translator;
import learn.english.translator.TranslatorManager;
import learn.english.vlc.VlcStatusData;
import lombok.Getter;
import lombok.Setter;

import javax.ejb.*;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

/**
 * Created by yaroslav on 9/26/14.
 */
@Stateful
@TransactionManagement(value = TransactionManagementType.BEAN)
public class LiveSubtitlesProcessor {
    int counter; //TODO: remove this
    @EJB ContentService contentService;
    @EJB TranslatorManager translatorManager;

    @Getter @Setter
    Dictionary dictionary;

    String videoFileName;
    String state;
    Integer time;

    Long systemTime;
    AdvanceSubtitles advanceSubtitles;

    //TODO: remove this
    public void execute(VlcStatusData vlcStatus){
        System.out.println("Time : "+vlcStatus.getTime());
        System.out.println("State : "+vlcStatus.getState());
        if(vlcStatus.getMetaInfo()!=null && vlcStatus.getMetaInfo().getInfos()!=null){
            for (VlcStatusData.Item item : vlcStatus.getMetaInfo().getInfos())
                System.out.println(item.getName()+" = "+item.getValue());
        }


        System.out.println("Server Counter : "+counter++);
    }

    public void vlcEvent(VlcStatusData vlcStatus){
        System.out.println("Time : "+vlcStatus.getTime());
        System.out.println("State : "+vlcStatus.getState());
        if(dictionary==null)
            return;
        String filename = vlcStatus.getMetaInfo().getInfos().stream()
                .filter(i->"filename".equals(i.getName()))
                .findFirst()
                .orElse(new VlcStatusData.Item("", "")).getValue();
        if( videoFileName == null || ! videoFileName.equals(filename)){
            videoFileName = filename;
            constructSubtitles(filename);
        }
        this.time = vlcStatus.getTime();
        this.state = vlcStatus.getState();
        this.systemTime = System.currentTimeMillis();
    }

    public LiveSample sample(){
        return new LiveSample(videoFileName, timeFrame());
    }

    public AdvanceSubtitles getAdvanceSubtitles() {
        return advanceSubtitles;
    }

    void constructSubtitles(String filename) {
/*        MediaItem item = dictionary.getMediaItems().stream()
                .filter(i -> filename.equals(i.getFilename()))
                .findFirst().get();*/
        MediaItem item = null;
        for (MediaItem i : dictionary.getMediaItems()) {
            if(filename.equals(i.getFilename())){
                item = i;
                break;
            }
        }
        if(item == null)
            return;

        byte[] content = contentService.get(item.getId());
        Subtitles subtitles = parseSubtitles(content);
        this.advanceSubtitles = new AdvanceSubtitles();
        this.advanceSubtitles.setVideoFileName(videoFileName);

        Set<String> newWords = item.getWords().stream().filter(w -> w.getCategory().equals(Category.NEW_WORD)).flatMap(w -> w.getWords().stream())
                .collect(toSet());

        subtitles.getData().entrySet().stream().forEach(
                entry->advanceSubtitles.getData().
                put(entry.getKey(), advanceUnit(entry.getValue(), newWords))  );
    }

    AdvanceSubtitles.Unit advanceUnit(SubtitlesUnit unit, Set<String> newWords){
        AdvanceSubtitles.Unit advanceUnit = new AdvanceSubtitles.Unit();


        advanceUnit.setFullText(unit.getFullText());
        advanceUnit.setNewWords_translationMap(translationMap(unit,newWords));
        advanceUnit.setPartialTranslatedText(partialTranslation(unit, newWords));

        return advanceUnit;
    }

    String partialTranslation(SubtitlesUnit unit, Set<String> newWords) {
        Translator translator = translatorManager.translator(dictionary.getLearningLanguage().toString(), dictionary.getNativeLanguage().toString());

        String text = unit.getFullText() ;
        Set<String> newWordsInUnit = unit.getWords().stream()
                .filter(newWords::contains)
                .collect(toSet());
        for (String word : newWordsInUnit)
            text = text.replaceAll(word, String.format("<FONT COLOR=\"#a298fa\">%s</FONT>(%s)", word, translator.singleWordTranslate(word).shortSummary()));

        return text;
    }

    Map<String, String> translationMap(SubtitlesUnit unit, Set<String> newWords) {
        Translator translator = translatorManager.translator(dictionary.getLearningLanguage().toString(), dictionary.getNativeLanguage().toString());

        return unit.getWords().stream()
                .filter(newWords::contains)
                .peek(String::toLowerCase)
                .distinct()
                .collect(toMap(
                        w -> w,
                        w -> translator.singleWordTranslate(w).summary())
                );
    }

    Integer timeFrame(){
        if(time==null)
            return -1;
        if(! "playing".equals(state))
            return advanceSubtitles.getData().floorKey(time);
        long currentTime = System.currentTimeMillis();
        int deltaTime_sec = (int)((currentTime - systemTime)/1000);
        return  advanceSubtitles.getData().floorKey(time + deltaTime_sec);
    }

    Subtitles parseSubtitles(byte[] content){
        try {
            return  (Subtitles) SubtitlesParser.defaultParser().parse(content);
        } catch (ParserException e) {
            e.printStackTrace();
        }
        return new Subtitles(new TreeMap<>());
    }

    @Remove
    public void destroy(){
        //NOP
        System.out.println("LiveSubtitlesProcessor bean destroyed");
    }
}
