package app.lyricsapp.controller;

import app.lyricsapp.model.*;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DialogController {

    public  static Image icon;

    static {
        try {
            icon = new  Image(new FileInputStream("./ImageAssets/icon.png"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public DialogController() throws FileNotFoundException {
    }

    //Adding a song From SinglePlayListPageView
    public static void chooseResearchModeDialog(Scene scene, PlaylistsList play, int index) {
    // get the parent window
        Window parentWindow = scene.getWindow();
        List<String> choices = Arrays.asList(TextContentManager.getLangagesText().getArtistAndTitle(), TextContentManager.getLangagesText().getLyrics());
        String defaultChoice = TextContentManager.getLangagesText().getArtistAndTitle();
        ChoiceDialog<String> dialog = new ChoiceDialog<>(defaultChoice, choices);
        dialog.setTitle(TextContentManager.getLangagesText().getResearchMode());
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        dialog.initOwner(parentWindow);
        stage.getIcons().add(icon); // To add an icon
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText(TextContentManager.getLangagesText().getResearchMode() + " :");
        BackgroundFill bckgFill = new BackgroundFill(Color.rgb(234, 168, 98), null, null);
        Background background = new Background(bckgFill);
        dialog.getDialogPane().setBackground(background);
        //delete button
        dialog.getDialogPane().getButtonTypes().remove(1);
        ButtonType deleteButtonType = new ButtonType(TextContentManager.getLangagesText().getCancelText(), ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(deleteButtonType);

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(choice -> {
            if (choice.equals(TextContentManager.getLangagesText().getArtistAndTitle())) {

                app.lyricsapp.controller.LyricsAppController.goToResearchByTitleLyricPageView(scene, play, index);
            } else {
                app.lyricsapp.controller.LyricsAppController.goToResearchByTitleLyricPageView(scene, play, index);
            }
        });

    }

    public static void createPlayListDialog(PlaylistsList list,Scene scene) {
        int nameInt = list.getSize()+1;
        // get the parent window
        Window parentWindow = scene.getWindow();
        TextInputDialog dialog = new TextInputDialog("Playlist"+ nameInt );
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon); // To add an icon
        dialog.setTitle(TextContentManager.getLangagesText().getNewPlaylist());
        dialog.setContentText(TextContentManager.getLangagesText().getPlaylistName());
        dialog.setGraphic(null);
        dialog.setHeaderText(null);
        dialog.initOwner(parentWindow);
        BackgroundFill bckgFill = new BackgroundFill(Color.rgb(234,168,98), null, null);
        Background background = new Background(bckgFill);
        dialog.getDialogPane().setBackground(background);

        //delete button
        dialog.getDialogPane().getButtonTypes().remove(1);
        ButtonType deleteButtonType = new ButtonType(TextContentManager.getLangagesText().getCancelText(), ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(deleteButtonType);


        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            try{
                list.append(name);
                successAlert(name+TextContentManager.getLangagesText().getHasBeenAddedSuccessfully(),
                        TextContentManager.getLangagesText().getNewPlaylist(),scene);
            }catch (AlreadyExistingElementException e){
                alreadyExistingElementAlert(name+" "+TextContentManager.getLangagesText().getAlreadyExists()+ " "+TextContentManager.getLangagesText().getPlaylists(),TextContentManager.getLangagesText().getTitle(),scene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        });

    }
    public static void renamePlayListDialog(PlaylistsList play,int index,Scene scene) {
        int nameInt = play.getSize()+1;
        // get the parent window
        Window parentWindow = scene.getWindow();
        TextInputDialog dialog = new TextInputDialog("Playlist"+ nameInt );
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon); // To add an icon
        dialog.setTitle(TextContentManager.getLangagesText().getRename());
        dialog.setContentText(TextContentManager.getLangagesText().getPlaylistName());
        dialog.setGraphic(null);
        dialog.setHeaderText(null);
        dialog.initOwner(parentWindow);
        BackgroundFill bckgFill = new BackgroundFill(Color.rgb(234,168,98), null, null);
        Background background = new Background(bckgFill);
        dialog.getDialogPane().setBackground(background);

        //delete button
        dialog.getDialogPane().getButtonTypes().remove(1);
        ButtonType deleteButtonType = new ButtonType(TextContentManager.getLangagesText().getCancelText(), ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(deleteButtonType);


        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            try{
                //list.append(name);
                if(play.isAlreadyInPlaylistsList(new PlayList(name))) throw new AlreadyExistingElementException();
                play.get().get(index).setName(name);
                play.writeIntoFile("serializationFiles/Play");
                successAlert(TextContentManager.getLangagesText().getHasBeenRenamed(),
                        TextContentManager.getLangagesText().getNewPlaylist(),scene);

            }catch (AlreadyExistingElementException e){
                alreadyExistingElementAlert(name+" "+TextContentManager.getLangagesText().getAlreadyExists()+" "+TextContentManager.getLangagesText().getPlaylists(),TextContentManager.getLangagesText().getTitle(),scene);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }
    public static void choosePlayListDialog(Song song, PlaylistsList play,Scene scene){
        // get the parent window
        Window parentWindow = scene.getWindow();
        PlayList defaultChoice = play.get().get(0);
        ChoiceDialog<PlayList> dialog = new ChoiceDialog<PlayList>(defaultChoice,play.get());
        dialog.initOwner(parentWindow);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        stage.getIcons().add(icon); // To add an icon
        dialog.setTitle(TextContentManager.getLangagesText().getPlaylist());
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText(TextContentManager.getLangagesText().getPlaylist()+" :");
        BackgroundFill bckgFill = new BackgroundFill(Color.rgb(234,168,98), null, null);
        Background background = new Background(bckgFill);
        dialog.getDialogPane().setBackground(background);
        //delete button
        dialog.getDialogPane().getButtonTypes().remove(1);
        ButtonType deleteButtonType = new ButtonType(TextContentManager.getLangagesText().getCancelText(), ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(deleteButtonType);

        Optional<PlayList> result = dialog.showAndWait();
        result.ifPresent(choice -> {
            int playlistIndex = play.getPlaylistIndex(choice);
            PlayList playlist = play.get().get(playlistIndex);
            try {
                playlist.append(song);

                successAlert(song.getTitle() + TextContentManager.getLangagesText().getHasBeenAddedSuccessfully() ,
                        TextContentManager.getLangagesText().getNewSong(),scene);
            } catch (AlreadyExistingElementException e) {
                alreadyExistingElementAlert(song.getTitle() + TextContentManager.getLangagesText().getAlreadyExists() + playlist.getName(),
                        TextContentManager.getLangagesText().getNewSong(),scene);
            }
            try {
                play.writeIntoFile("serializationFiles/Play");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });





    }
    public static void successAlert(String message,String title,Scene scene){
        // get the parent window
        Window parentWindow = scene.getWindow();

        Alert alert = new Alert(Alert.AlertType.NONE);


        ButtonType deleteButtonType = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
       alert.getDialogPane().getButtonTypes().add(deleteButtonType);
        //alert.setContentText(title);
        alert.initOwner(parentWindow);
        alert.setTitle(title);
        alert.setContentText(message);

        alert.setHeaderText(null);
        // Get the dialog pane
        DialogPane dialogPane = alert.getDialogPane();

        // Set the background color using CSS
        dialogPane.setStyle("-fx-background-color: #eaa862;");
        alert.showAndWait();
        alert.setOnCloseRequest((event -> {
            alert.close();
        }));



    }
    public static void alreadyExistingElementAlert(String message,String title,Scene scene){
        Alert alert = new Alert(Alert.AlertType.NONE);

        ButtonType deleteButtonType = new ButtonType("OK", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getDialogPane().getButtonTypes().add(deleteButtonType);
        Window parentWindow = scene.getWindow();
        alert.initOwner(parentWindow);
        alert.setTitle(title);
        alert.setContentText(message);

        // Get the dialog pane
        DialogPane dialogPane = alert.getDialogPane();

        // Set the background color using CSS
        dialogPane.setStyle("-fx-background-color: #eaa862;");

        alert.showAndWait();


    }
    public static void  deleteElementAlert(Scene scene,PlaylistsList play,PlayList playList1){

        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle(TextContentManager.getLangagesText().getConfirmDelete());


        ButtonType confirmButtonType = new ButtonType(TextContentManager.getLangagesText().getConfirmDelete(), ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteButtonType = new ButtonType(TextContentManager.getLangagesText().getCancelText(), ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, deleteButtonType);
        Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
        Window parentWindow = scene.getWindow();
        stage.initOwner(parentWindow);
        stage.getIcons().add(icon);
        dialog.setGraphic(null);
        dialog.setHeaderText(null);
        BackgroundFill bckgFill = new BackgroundFill(Color.rgb(234,168,98), null, null);
        Background background = new Background(bckgFill);
        dialog.getDialogPane().setBackground(background);



        // Add listener to enable the delete button only if the user confirms the deletion
        dialog.getDialogPane().lookupButton(confirmButtonType).addEventFilter(ActionEvent.ACTION, event -> {


        });

        // Create a VBox to hold the message and the delete/cancel buttons
        VBox vbox = new VBox();
        vbox.getChildren().add(new Label(TextContentManager.getLangagesText().getAreYouSure()));

        // Add the VBox to the dialog pane
        dialog.getDialogPane().setContent(vbox);

        // Show the dialog box and wait for the user to close it
        Optional<ButtonType> result = dialog.showAndWait();

        // Check if the user confirmed the deletion
        if (result.isPresent() && result.get() == confirmButtonType) {
            // Perform the deletion
            try {
                play.remove(playList1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                LyricsAppController.goToPlaylistsListPageView(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }



    }


}
