package app.lyricsapp.view;

import app.lyricsapp.model.*;
import app.lyricsapp.controller.LyricsAppController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ResearchByLyricsPageView extends PageView{
    private final Scene scene;
    private final TextField lyricsTextField = new TextField();

    HBox top = new HBox();
    private final VBox content = new VBox();
    private final HBox lyricsFavArea = new HBox();
    private final ImageView logoView;
    private final ImageView homeView;
    public boolean Popular;
    private final Button searchButton = LyricsAppController.button(TextContentManager.getLangagesText().getSearchLabel());
    private final Text restrictLabel = new Text(TextContentManager.getLangagesText().getRestrictLabel());
    private final HBox retrictLabelBox = new HBox();
    private final Region filler = new Region();
    CheckBox checkBox = new CheckBox();
    private PlaylistsList play;
    private int index;



    public ResearchByLyricsPageView(PlaylistsList play, int index){

        if (index != -1) this.play = play;
        this.index = index;

        try {
            Image logo = new Image(new FileInputStream(
                    "./ImageAssets/Lyrics.png"
            ));
            logoView = new ImageView(logo);
            logoView.setX(260);
            logoView.setY(130);
            logoView.setFitHeight(455);
            logoView.setFitWidth(500);
            logoView.setPreserveRatio(true);

            Image home = new Image(new FileInputStream("./ImageAssets/home.png"));
            homeView = new ImageView(home);
            homeView.setFitHeight(32);
            homeView.setFitWidth(32);
            homeView.setX(10);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        //setcheckBox
        VBox.setMargin(checkBox,new Insets(60,0,0,450));
        /**Enleve l'autofocus sur les champs d'entré**/
        checkBox.setOnAction(e -> Popular = checkBox.isSelected());

        lyricsTextField.setFocusTraversable(false);

        //set children
        top.getChildren().addAll(homeView,logoView);
        lyricsFavArea.getChildren().addAll(lyricsTextField,searchButton);
        retrictLabelBox.getChildren().addAll(restrictLabel,checkBox,filler);
        content.getChildren().addAll(lyricsFavArea,retrictLabelBox);

        // set in root
        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(content);

        lyricsTextField.setOnKeyPressed(ke -> {if (ke.getCode().equals(KeyCode.ENTER)) {search();}});
        searchButton.setOnAction(e -> search());

        homeView.setOnMouseClicked((click) ->{
            try {
                LyricsAppController.goToHomeChoosePageView(getScene());

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        homeView.hoverProperty().addListener(e -> homeView.setStyle("-fx-cursor: hand"));

        // décoration et remplissage
        setPromptTexts();
        positionElements();
        setPadding();
        modifytext();
        root.setStyle("-fx-background-color: transparent;");
        scene = new Scene(root,900,700);
        Color orangeSable = Color.rgb(234, 168, 98);
        scene.setFill(orangeSable); //  #e0aa6d <-- we need this color

    }

    public void search() {
        String lyricsInput = lyricsTextField.getText().replace(" ","%20").toLowerCase();
        switch (checkInputValidity(lyricsInput)) {
            case 0:
                try {

                    InputStream stream = GetData.searchLyricText(lyricsInput);
                    //LyricsAppController.goToResearchPageView(PopularSongTest(LyricsAppController.makeListSong(stream)) ,getScene());
                    if (this.index == -1)
                        LyricsAppController.goToResearchPageView(PopularSongTest(LyricsAppController.makeListSong(stream)) ,getScene());
                    else
                        LyricsAppController.goToResearchPageView(PopularSongTest(LyricsAppController.makeListSong(stream)),getScene(),play,index);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 1: // handle empty input error
                errorMessage(TextContentManager.getLangagesText().getEmptyField());
                break;
            case 2: // handle input length error
                errorMessage(TextContentManager.getLangagesText().getShortInput());
                break;
            case 3: // handle banned word error
                errorMessage(TextContentManager.getLangagesText().getBannedWordLyrics());
                break;
        }
    }

    private void errorMessage(String string){
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-font-size: 18px;");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> tooltip.hide())
        );
        tooltip.setText(string);
        tooltip.show(lyricsTextField.getScene().getWindow(),
                lyricsTextField.getScene().getWindow().getX() + lyricsTextField.getBoundsInParent().getMinX() + 20,
                lyricsTextField.getScene().getWindow().getY() + lyricsTextField.getBoundsInParent().getMaxY() + 250);
        tooltip.setAutoHide(true);
        timeline.play();
    }

    private int checkInputValidity(String lyricsInput) {
        if (lyricsInput.isEmpty()) {
            return 1; // empty input error
        } else if (lyricsInput.length() < 2) {
            return 2; // input length error
        } else if (GetData.TestBanWord(lyricsInput)) {
            return 3; // banned word error
        } else {
            return 0; // valid input
        }
    }

    // Text Field
        private void setPromptTexts(){
            lyricsTextField.setPromptText(TextContentManager.getLangagesText().getLyrics());
        }

        //alignment,spacing,size
        private void positionElements(){
            BorderPane.setAlignment(logoView, Pos.CENTER);
            BorderPane.setAlignment(content,Pos.CENTER);
            lyricsFavArea.setAlignment(Pos.CENTER);
            lyricsTextField.setPrefSize(300,50);
            searchButton.setPrefSize(100, 40);
            retrictLabelBox.setAlignment(Pos.CENTER);
        }

        public void modifytext(){
            lyricsTextField.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 15));
            restrictLabel.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 20));
            restrictLabel.setFill(Color.WHITE);
            lyricsTextField.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 15));
        }

        // marge interne
        private void setPadding(){
            Insets padding = new Insets(0);
            Insets marging =  new Insets(12, 0, 0, 0);
            lyricsTextField.setPadding(padding);
            checkBox.setStyle("-fx-font-size: 10pt;");
            HBox.setMargin(lyricsTextField,new Insets(0,50,0,0));
            HBox.setMargin(restrictLabel,new Insets(10,10,0,225));
            HBox.setHgrow(filler, Priority.ALWAYS);
            HBox.setMargin(checkBox,marging);
            HBox.setMargin(homeView,new Insets(10,157.5,0,10));
            VBox.setMargin(lyricsFavArea,new Insets(100,0,0,0));
        }

        public Scene getScene() {
            return scene;
        }

        public List<Song> PopularSongTest(List<Song> songs){
            if(Popular){
                songs.removeIf(element -> element.getSongRank() < 7);
            }
            return songs;
        }
    }


