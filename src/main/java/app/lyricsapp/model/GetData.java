package app.lyricsapp.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
public class GetData {

    static String banWordFinal ="about/after/all/also/an/and/another/any/are/as/at/be/because/been/before/being/between/both/but/by/came/can/come/could/did/do/does/each/else/for/from/get/got/had/has/have/he/her/here/him/himself/his/how/if/in/into/is/it/its/just/like/make/many/me/might/more/most/much/must/my/never/no/now/of/on/only/or/other/our/out/over/re/said/same/see/should/since/so/some/still/such/take/than/that/the/their/them/then/there/these/they/this/those/through/to/too/under/up/use/very/want/was/way/we/well/were/what/when/where/which/while/who/will/with/would/you/your/(/)/[/]/'/!/./:/;/,/|/~/?/!/\"";

    public static InputStream searchLyric() throws IOException {
        //SCANNER
        Scanner input = new Scanner(System.in);
        System.out.println("Artist : ");
        String urlLyricsApiArtist = input.nextLine().replace(" ","%20");

        if (urlLyricsApiArtist.isEmpty()|| urlLyricsApiArtist.length()<2||TestBanWord(urlLyricsApiArtist)){
            System.out.println("/! Enter a valid name !/");
            return searchLyric();
        }
        System.out.println("Title : ");
        String urlLyricsApiSong = input.nextLine().replace(" ","%20");

        while (urlLyricsApiSong.isEmpty() || urlLyricsApiSong.length()<2||TestBanWord(urlLyricsApiSong)){
            System.out.println("/! Enter a valid title !/");
            System.out.println("Title : ");
            urlLyricsApiSong = input.nextLine().replace(" ","%20");
        }

        String url = "http://api.chartlyrics.com/apiv1.asmx/SearchLyric?artist=" + urlLyricsApiArtist + "&song=" + urlLyricsApiSong;
        //Etablit la connection et renvoie le flux de données
        return connect(url).getInputStream();
    }
    public static InputStream searchLyric(String title,String artist) throws IOException {

        String url = "http://api.chartlyrics.com/apiv1.asmx/SearchLyric?artist=" + artist + "&song=" + title;
        //Etablit la connection et renvoie le flux de données
        return connect(url).getInputStream();
    }
    public static InputStream searchLyricDirect(String name,String title) throws IOException {
        name= name.replace(" ","%20");
        title = title.replace(" ","%20");
        String url = "http://api.chartlyrics.com/apiv1.asmx/SearchLyricDirect?artist=" + name + "&song=" +title;
        //Etablit la connection et renvoie le flux de données
        return connect(url).getInputStream();
    }


    public static InputStream getLyric(Integer id, String checksum) throws IOException {
        String url = "http://api.chartlyrics.com/apiv1.asmx/GetLyric?lyricId=" + id + "&lyricCheckSum=" + checksum;
        //Etablit la connection et renvoie le flux de données**
        return connect(url).getInputStream();
    }


    public static InputStream searchLyricText() throws IOException {

        Scanner input = new Scanner(System.in);
        System.out.println("Lyrics : ");

        String lyricText =  input.nextLine().replace(" ","%20");
        if (lyricText.isEmpty() || lyricText.length()<2||TestBanWord(lyricText)){
            System.out.println("/! Please enter at least one word !/");
            return searchLyricText();
        }

        String url = "http://api.chartlyrics.com/apiv1.asmx/SearchLyricText?lyricText=" + lyricText;

        //Etablit la connection et renvoie le flux de données
        return connect(url).getInputStream();
    }
    public static InputStream searchLyricText(String lyrics) throws IOException {

        String url = "http://api.chartlyrics.com/apiv1.asmx/SearchLyricText?lyricText=" + lyrics;

        //Etablit la connection et renvoie le flux de données
        return connect(url).getInputStream();
    }

    public static HttpURLConnection connect(String url) throws IOException {
        URL api = new URL(url);
        return (HttpURLConnection) api.openConnection();
    }

    public static Boolean TestBanWord(String words) {

        String[] items = banWordFinal.split("/");
        int count = 0;
        String[] splitedWords = words.split("%20");

        for (String item : items) {
            for (String word : splitedWords){
                if(word.equals(item) ){
                    count++;
                }
            }
        }
        return count == splitedWords.length;
    }
}