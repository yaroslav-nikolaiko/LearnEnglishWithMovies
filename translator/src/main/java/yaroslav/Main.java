package yaroslav;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.io.*;
import java.net.*;
import java.text.MessageFormat;


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

        System.out.println("System proxy host: " + System.getProperties().get("http.proxyHost"));
        System.out.println("System proxy port: " + System.getProperties().get("http.proxyPort"));

        String systemProxy = System.getProperties().get("http.proxyHost").toString();
        if( ! "null".equals(systemProxy)){
            System.getProperties().put("http.proxyHost", "proxy2.cht");
            System.getProperties().put("http.proxyPort", "3128");
        }

        String ipAddress = "173.194.39.160";
        InetAddress inet = InetAddress.getByName(ipAddress);

        System.out.println("Sending Ping Request to " + ipAddress);
        System.out.println(inet.isReachable(5000) ? "Host is reachable" : "Host is NOT reachable");

        String languageTo = "ru";
        String languageFrom = "en";
        String text = "Hello again";

        String filepath = "google_result";


        String pattern = "http://translate.google.com/translate_a/t?client=t&text=%s&hl=en&sl=%s&tl=%s&ie=UTF-8&oe=UTF-8&multires=1&otf=1&pc=1&trs=1&ssel=3&tsel=6&sc=1";
        text = URLEncoder.encode(text, "UTF-8");

        String url = String.format(pattern, text, languageFrom, languageTo);

        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target(url);
        System.out.println("WTF URL is:  "+webTarget.getUri());

        byte[] bArray;
        FileOutputStream os = null;
        try {
            bArray = webTarget.request().get(byte[].class);
            os = new FileOutputStream(filepath);
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


//        URL oracle = new URL("http://translate.google.com/translate_a/t?client=t&text=Hello%20again&hl=en&sl=en&tl=ru&ie=UTF-8&oe=UTF-8&multires=1&otf=1&pc=1&trs=1&ssel=3&tsel=6&sc=1");
//        URLConnection yc = oracle.openConnection();
//        BufferedReader in = new BufferedReader(new InputStreamReader(
//                yc.getInputStream()));
//        String inputLine;
//        while ((inputLine = in.readLine()) != null)
//            System.out.println(inputLine);
//        in.close();



//            String url = "http://google.com";
//
//            URL obj = new URL(url);
//            HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
//
//            BufferedReader in = new BufferedReader(new InputStreamReader(
//                    conn.getInputStream()));


    }
}
