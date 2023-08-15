package app.lyricsapp.model;

public class TextContentManager {
    private static LangagesText langagesText = LangagesText.FRENCH;


    //Switches the langage
    public static void switchLangage(String langage){
        langagesText = LangagesText.valueOf(langage.toUpperCase());
    }

    public static  LangagesText getLangagesText() {
        return langagesText;
    }
}
