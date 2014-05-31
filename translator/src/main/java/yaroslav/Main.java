package yaroslav;

import yaroslav.subtitles.SubtitlesParser;
import yaroslav.subtitles.parser.subtitleFile.Caption;
import yaroslav.subtitles.parser.subtitleFile.TimedTextObject;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.*;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * Created by yaroslav on 5/14/14.
 */
public class Main {
//    private String languageTo = "ru";
//    private String languageFrom = "en";
//    private String text = "Hello again";
//
//    private String pattern = "http://translate.google.com/translate_a/t?client=t&text=%s&hl=en&sl=%s&tl=%s&ie=UTF-8&oe=UTF-8&multires=1&otf=1&pc=1&trs=1&ssel=3&tsel=6&sc=1";



    public static void main(String[] args) throws IOException {

        System.getProperties().put("http.proxyHost", "proxy2.cht");
        System.getProperties().put("http.proxyPort", "3128");

//        String fileName = "Game of Thrones - 4x01 - Two Swords.HDTV.KILLERS.en.srt";
        String fileName = "small_file.srt";
        SubtitlesParser parser = new SubtitlesParser(fileName);
        TimedTextObject tto = parser.getTto();


        List<String> content = new ArrayList<>();
        int counter=0;
        for(Caption caption : tto.captions.values()) {
            //System.out.println(caption.content);
            content.add(caption.content);
            counter ++;
            if(counter==5)
                break;

        }

        translateYandex(content, false);
        //xmlParser();
    }


    public static void translateYandex(List<String> textList, boolean append) throws UnsupportedEncodingException {
        String languageTo = "ru";
        //String languageFrom = "en";

        String filepath = "yandex_result.xml";
        String apiKey = "trnsl.1.1.20140531T124615Z.286c638c7c6d12c2.7b0a3f481468b227ad9021cbdc2bee694ec79d35";


        String pattern = "https://translate.yandex.net/api/v1.5/tr/translate?key=%s&lang=%s&text=%s";
        String textSeparator = "%3F&text=";
        //text = URLEncoder.encode(text, "UTF-8");
        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append(URLEncoder.encode(textList.remove(0), "UTF-8"));
        for(String part : textList) {
            textBuilder.append(textSeparator + URLEncoder.encode(part, "UTF-8"));
        }

        String url = String.format(pattern, apiKey, languageTo, textBuilder);

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(url);
        System.out.println("WTF URL is:  "+webTarget.getUri());

        byte[] bArray;
        FileOutputStream os = null;
        try {
            bArray = webTarget.request().get(byte[].class);
            os = new FileOutputStream(filepath, append);
            os.write(bArray);
            os.flush();

        } catch (IOException e) {
            String message = MessageFormat.format("Failed to save {0} file", filepath);
            System.exit(1);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    System.exit(1);
                }
            }
        }

    }

    public static void xmlParser() {
        String filename = "yandex_result.xml";
        XmlObject obj = unmarshaller(filename);
        System.out.println(obj);
    }

    static XmlObject unmarshaller(String xmlFileName ){
        InputStream input = null;
        XmlObject result = null;
        try {
            input = new FileInputStream(xmlFileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlObject.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            result = (XmlObject) jaxbUnmarshaller.unmarshal(input);
        } catch (IOException ex) {
            //logger.error(MessageFormat.format("File {0} not found ", xmlFileName), ex);
            ex.printStackTrace();
        } catch (JAXBException e) {
            //TODO log
            e.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    //TODO log
                }
            }
        }
        return result;
    }

    static void marshaller(String xmlFileName, XmlObject object){
        try {
            File file = new File(xmlFileName);
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlObject.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            // output pretty printed
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            jaxbMarshaller.marshal(object, file);
        } catch (JAXBException e) {
            //TODO log
            e.printStackTrace();
        }
    }

}
