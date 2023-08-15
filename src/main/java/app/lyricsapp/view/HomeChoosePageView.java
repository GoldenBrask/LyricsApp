package app.lyricsapp.view;

import app.lyricsapp.controller.LyricsAppController;
import app.lyricsapp.model.TextContentManager;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class HomeChoosePageView extends PageView{

        private final Scene scene;
        private final VBox content = new VBox();

        private final Text titleArtistText = new Text(TextContentManager.getLangagesText().getSearchByTitleArtist());

        private final Text lyricsText = new Text(TextContentManager.getLangagesText().getSearchByLyrics());

        private final Text playlistsText = new Text(TextContentManager.getLangagesText().getPlaylists());
        private final HBox artistTitleBox = new HBox();


        private final HBox lyricsBox = new HBox();

        private final HBox playListsBox = new HBox();
        private final ImageView logoView;
        private final ImageView viewFrFlag;
        private final ImageView viewEnFlag;
        private final ImageView viewSPFlag;

    public HomeChoosePageView(){



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

                Image frFlag = new Image(new FileInputStream("./ImageAssets/frFlag.png"));
                viewFrFlag = new ImageView(frFlag);
                viewFrFlag.setFitHeight(50);
                viewFrFlag.setFitWidth(50);

                Image enFlag = new Image(new FileInputStream("ImageAssets/enFlag.png"));
                viewEnFlag = new ImageView(enFlag);
                viewEnFlag.setFitHeight(50);
                viewEnFlag.setFitWidth(50);


                Image SPFlag = new Image(new FileInputStream("ImageAssets/SPFlag.png"));
                viewSPFlag = new ImageView(SPFlag);
                viewSPFlag.setFitHeight(35);
                viewSPFlag.setFitWidth(50);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            modifyText();
            setStyle();
            setMargin();
            setBoxSize();
            animate();


            ///////////////////////////////////////////
            artistTitleBox.setOnMouseClicked((click) -> LyricsAppController.goToResearchByTitleLyricPageView(getScene()));

            lyricsBox.setOnMouseClicked((click) -> LyricsAppController.goToResearchByLyricsPageView(getScene()));

            playListsBox.setOnMouseClicked((click) ->{
                try {
                    LyricsAppController.goToPlaylistsListPageView(getScene());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            artistTitleBox.getChildren().addAll(titleArtistText);
            lyricsBox.getChildren().addAll(lyricsText);
            playListsBox.getChildren().addAll(playlistsText);
            content.getChildren().addAll(artistTitleBox,lyricsBox, playListsBox);
            //change languageMouseEvent
        try {
            ChangeLanguageMouseEvent();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
            // set in root
            BorderPane root = new BorderPane();
            root.setTop(logoView);
            root.setCenter(content);

            // décoration et remplissage
            positionElements();
            root.setStyle("-fx-background-color: transparent;");
            scene = new Scene(root,900,700);
            Color orangeSable = Color.rgb(234, 168, 98);
            scene.setFill(orangeSable); //  #e0aa6d <-- we need this color


        }


        //alignment,spacing,size
        private void positionElements(){
            BorderPane.setAlignment(logoView, Pos.CENTER);
            BorderPane.setAlignment(content,Pos.CENTER);
            content.setAlignment(Pos.CENTER);
            this.artistTitleBox.setAlignment(Pos.CENTER);
            this.lyricsBox.setAlignment(Pos.CENTER);
            this.playListsBox.setAlignment(Pos.CENTER);
        }

        private void modifyText(){
            titleArtistText.setFill(Color.rgb(182, 60, 63));
            titleArtistText.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 20));
            lyricsText.setFill(Color.rgb(182, 60, 63));
            lyricsText.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 20));
            playlistsText.setFill(Color.rgb(182, 60, 63));
            playlistsText.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 20));
        }

        private void setStyle() {
            artistTitleBox.setStyle("-fx-background-color: rgb(243, 238, 237);-fx-border-radius: 10;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 4, 0, 0, 4);-fx-cursor: hand;-fx-opacity: 0;");
            lyricsBox.setStyle("-fx-background-color: rgb(243, 238, 237);-fx-border-radius: 10;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 4, 0, 0, 4);-fx-cursor: hand;-fx-opacity: 0;");
            playListsBox.setStyle("-fx-background-color: rgb(243, 238, 237);-fx-border-radius: 10;-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 4, 0, 0, 4);-fx-cursor: hand;-fx-opacity: 0;");
            viewFrFlag.setStyle("-fx-cursor: hand;-fx-opacity: 1");
            viewEnFlag.setStyle("-fx-cursor: hand;-fx-opacity: 1");
            viewSPFlag.setStyle("-fx-cursor: hand;-fx-opacity: 1");
            hover(artistTitleBox);
            hover(lyricsBox);
            hover(playListsBox);
        }

        private void setMargin(){
            Insets insetHBox = new Insets(0,0,50,0);
            VBox.setMargin(artistTitleBox,insetHBox);
            VBox.setMargin(lyricsBox,insetHBox);
            VBox.setMargin(playListsBox,insetHBox);
        }

        private void setBoxSize(){
            setSize(artistTitleBox, 530);
            setSize(lyricsBox, 430);
            setSize(playListsBox, 330);
        }

        private void setSize(HBox box, int width){
            box.setMinHeight(60);
            box.setMaxHeight(60);
            box.setMinWidth(width);
            box.setMaxWidth(width);
        }

        public void hover(HBox box){
            box.hoverProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    box.setStyle("-fx-background-color: rgb(255,255,255);-fx-effect: dropshadow(three-pass-box, rgba(244, 64, 13, .5), 4, 0.5, 0, 6);-fx-cursor:hand;");
                } else {
                    box.setStyle("-fx-background-color: rgb(243, 238, 237);-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 4, 0, 0, 4);-fx-cursor:hand;");
                }
            });
        }

        public void ChangeLanguageMouseEvent() throws FileNotFoundException {

            HBox flagContainer = new HBox();
            flagContainer.setAlignment(Pos.CENTER);
            flagContainer.setSpacing(10);
            flagContainer.getChildren().addAll(viewFrFlag, viewEnFlag,viewSPFlag);
            content.getChildren().addAll(flagContainer);

            viewFrFlag.setOnMouseClicked((click) ->{
                try {
                    TextContentManager.switchLangage("FRENCH");
                    LyricsAppController.goToHomeChoosePageView(scene);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            viewEnFlag.setOnMouseClicked((click) ->{
                try {
                    TextContentManager.switchLangage("ENGLISH");
                    LyricsAppController.goToHomeChoosePageView(scene);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            viewSPFlag.setOnMouseClicked((click) ->{
                try {
                    TextContentManager.switchLangage("SPANISH");
                    LyricsAppController.goToHomeChoosePageView(scene);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        public Scene getScene() {
                return scene;
        }

    public void animate() {
        int duration = 800;

        animation(artistTitleBox,duration,0);
        animation(lyricsBox,duration,300);
        animation(playListsBox,duration,600);
    }

    public void animation(HBox box, int duration, int delay){
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(duration), box);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration), box);
        translateTransition.setFromY(50);
        translateTransition.setToY(0);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setDelay(Duration.millis(delay));
        translateTransition.setDelay(Duration.millis(delay));
        fadeTransition.play();
        translateTransition.play();
    }


    }


