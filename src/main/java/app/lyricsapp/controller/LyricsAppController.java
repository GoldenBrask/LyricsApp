package app.lyricsapp.controller;

import app.lyricsapp.model.GetData;
import app.lyricsapp.model.PlaylistsList;
import app.lyricsapp.model.ReadXMLFile;
import app.lyricsapp.model.Song;
import app.lyricsapp.view.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Objects;


public class LyricsAppController {

    public static List<Song> makeListSong(InputStream getData) throws IOException {

        List<Song> songs = ReadXMLFile.GetListSongs(getData);
        int i = 1;
        for (Song element : songs) {
            System.out.println("\n" + i + ") " + element.toString());
            i++;
        }
        if (songs.isEmpty()) {
            System.out.println("Song not found");
        }
        return  songs;
    }

    public static String DisplaySongLyrics(Song song) throws IOException {

            return Objects.requireNonNull(ReadXMLFile.GetSong(GetData.getLyric(song.getLyrickID(), song.getChecksum()))).toString3();
           // return Objects.requireNonNull(ReadXMLFile.GetSong(GetData.searchLyricDirect(song.getArtist(),song.getTitle()))).toString2();

    }

    public static void goToResearchByTitleLyricPageView(Scene scene) {
        ResearchByTitleArtistPageView research = new ResearchByTitleArtistPageView(null,-1);
        Window w = scene.getWindow();
        if (w instanceof Stage) {
            Stage s = (Stage) w;
            s.setScene(research.getScene());
        }
    }
    public static void goToResearchByTitleLyricPageView(Scene scene, PlaylistsList play, int index) {

        ResearchByTitleArtistPageView research = new ResearchByTitleArtistPageView(play,index);
        Window w = scene.getWindow();
        if (w instanceof Stage) {
            Stage s = (Stage) w;
            s.setScene(research.getScene());
        }
    }

    public static void goToResearchByLyricsPageView(Scene scene) {
        ResearchByLyricsPageView research = new ResearchByLyricsPageView(null,-1);
        Window w = scene.getWindow();
        if (w instanceof Stage) {
            Stage s = (Stage) w;
            s.setScene(research.getScene());
        }
    }
    public static void goToResearchByLyricsPageView(Scene scene, PlaylistsList play, int index) {
        ResearchByLyricsPageView research = new ResearchByLyricsPageView(play,index);
        Window w = scene.getWindow();
        if (w instanceof Stage) {
            Stage s = (Stage) w;
            s.setScene(research.getScene());
        }
    }

    public static void goToFavoritesPageView(Scene scene) throws IOException {
        FavorisPageView research = new FavorisPageView();
        Window w = scene.getWindow();
        if (w instanceof Stage) {
            Stage s = (Stage) w;
            s.setScene(research.getScene());
        }
    }

    public static void goToResearchPageView(List<Song> songs,Scene scene) throws IOException {
        ResearchPageView research = new ResearchPageView(null,-1);
        research.setListSongCenter(songs);

        Window w = scene.getWindow();
        if (w instanceof Stage) {
            Stage s = (Stage) w;
            s.setScene(research.getScene());
        }
    }
    public static void goToResearchPageView(List<Song> songs, Scene scene, PlaylistsList play, int index) throws IOException {
        ResearchPageView research = new ResearchPageView(play,index);
        research.setListSongCenter(songs);

        Window w = scene.getWindow();
        if (w instanceof Stage) {
            Stage s = (Stage) w;
            s.setScene(research.getScene());
        }
    }
    public static void goToHomeChoosePageView(Scene scene) throws IOException {
        HomeChoosePageView research = new HomeChoosePageView();
        Window w = scene.getWindow();
        if (w instanceof Stage) {
            Stage s = (Stage) w;
            s.setScene(research.getScene());
        }
    }

    public static void goToLyricsPageView(Song song,Scene scene,boolean bool,List<Song> songs,int index) throws IOException {

        LyricsPageView lyricsPageView = new LyricsPageView(setCover(song.getCoverForce()),song,songs,bool,index);
        lyricsPageView.setLyric(LyricsAppController.DisplaySongLyrics(song));
        //lyricsPageView.setCoverLyricsPageView(cover);
        lyricsPageView.setSong(song);
        lyricsPageView.setTitle(song.getTitle());
        lyricsPageView.setArtist(song.getArtist());

        Window w = scene.getWindow();
        if (w instanceof Stage) {
            Stage s = (Stage) w;
            s.setScene(lyricsPageView.getScene());
        }
    }
    public static void goToPlaylistsListPageView(Scene scene) throws IOException{

        PlaylistsPageView playlistsPageView = new PlaylistsPageView();
        Window w = scene.getWindow();
        if (w instanceof Stage) {
            Stage s = (Stage) w;
            s.setScene(playlistsPageView.getScene());
        }
    }

    public static void goToSinglePlayListPageView(int index,Scene scene) throws IOException{
        SinglePlaylistPageView singlePlaylistPageView = new SinglePlaylistPageView(index);
        Window w = scene.getWindow();
        if (w instanceof Stage) {
            Stage s = (Stage) w;
            s.setScene(singlePlaylistPageView.getScene());
        }
    }
    public static ImageView setCover(String url) throws FileNotFoundException {

        Image image;
        ImageView imageView;

        System.out.println(url);
        //
        if (url == null || (dontDisplay(url)) ) {

            image = new Image(new FileInputStream("ImageAssets/notFound.png"));
        }
        else {
            image = new Image(url);
        }
        imageView = new ImageView(image);
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    private static boolean dontDisplay(String url){
            if(!isLinkAlive(url)){
                return true;
            }
        return url.contains("images.amazon.com");
    }

    public static boolean isLinkAlive(String url) {
        try {
            URL link = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) link.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();
            return responseCode == HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            return false;
        }
    }

    public static Button button(String string){
        Button searchButton = new Button(string);
        searchButton.setStyle("-fx-background-color: rgb(243, 238, 237);-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 4, 0, 0, 4);;-fx-font-size:13;-fx-cursor:hand;-fx-font-weight: Bold;");
        searchButton.hoverProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                searchButton.setStyle("-fx-background-color: rgb(255,255,255);" + "-fx-effect: dropshadow(three-pass-box, rgba(244, 64, 13, .5), 4, 0.5, 0, 4);-fx-font-size:13;-fx-cursor:hand;-fx-font-weight: Bold;");
            } else {
                searchButton.setStyle("-fx-background-color: rgb(243, 238, 237);-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 4, 0, 0, 4);-fx-font-size:13;-fx-cursor:hand;-fx-font-weight: Bold;");
            }
        });
        return searchButton;
    }
}



