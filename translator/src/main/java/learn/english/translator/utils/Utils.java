package learn.english.translator.utils;

/**
 * Created by yaroslav on 9/22/14.
 */
public final class Utils {
    public static String dataBaseName(String languageFrom, String languageTo){
        return languageFrom+"-"+languageTo;
    }
    public static String languageTo(String dataBaseName){
        return dataBaseName.split("-")[1];
    }

    public static String languageFrom(String dataBaseName){
        return dataBaseName.split("-")[0];
    }
}
