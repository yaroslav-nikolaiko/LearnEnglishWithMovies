package learn.english.translator.core.impl.google;

import com.google.gson.Gson;
import learn.english.model.entity.WordInfo;
import learn.english.translator.Translator;
import learn.english.translator.core.dao.TranslatorDAO;
import learn.english.translator.core.impl.AbstractTranslator;
import learn.english.translator.utils.Utils;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yaroslav on 9/22/14.
 */
public class GoogleTranslator extends AbstractTranslator implements Translator {
    public static final String GOOGLE_TRANSLATOR_URL = "http://translate.google.com/translate_a/t?";
    private static Gson gson = new Gson();
    TranslatorDAO translatorDAO;
    String languageFrom;
    String languageTo;

    public GoogleTranslator(TranslatorDAO translatorDAO){
        this.translatorDAO = translatorDAO;
        this.languageFrom = Utils.languageFrom(translatorDAO.getDBName());
        this.languageTo = Utils.languageTo(translatorDAO.getDBName());
    }

    @Override
    protected List<String> getTranslation(List<String> textList) {
        // url example http://translate.google.com/translate_a/t?&text=hello&text=death&sl=en&tl=ru
        StringBuilder stringBuilder = new StringBuilder(GOOGLE_TRANSLATOR_URL);
        List<String> translated = new ArrayList<>();
        try {
        for (String text : textList) {

                text = URLEncoder.encode(text, "UTF-8");

            stringBuilder.append("client=p&text=").append(text);
            stringBuilder.append("&sl=").append(languageFrom);
            stringBuilder.append("&tl=").append(languageTo);

            String result = connect(stringBuilder.toString());
            GoogleTranslation translation = gson.fromJson(result, GoogleTranslation.class);

            String trans = "";
            for (Sentences s : translation.getSentences()) {
                trans += s.getTrans();
            }
            translated.add(trans);
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return translated;
    }

    String connect(String urlStr) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(urlStr);
        URLConnection urlConnection = url.openConnection();
        urlConnection.addRequestProperty("User-Agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        Reader reader = new InputStreamReader(urlConnection.getInputStream(),
                "UTF-8");
        BufferedReader bufferedReader = new BufferedReader(reader);

        int read;
        while ((read = bufferedReader.read()) != -1) {
            result.append((char) read);
        }

        return result.toString();
    }

    @Override
    public WordInfo singleWordTranslate(String text) {
        return null;
    }
}
