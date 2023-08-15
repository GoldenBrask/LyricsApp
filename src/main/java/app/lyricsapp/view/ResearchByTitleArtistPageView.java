package app.lyricsapp.view;
import app.lyricsapp.controller.LyricsAppController;
import app.lyricsapp.model.*;
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

public class ResearchByTitleArtistPageView extends PageView{

        private final Scene scene;
        private final TextField titleTextField = new TextField();
        private final TextField artistTextField = new TextField();
        private final HBox top = new HBox();
        private final VBox content = new VBox();
        private final HBox artistTitleArea = new HBox();
        private final Text restrictLabel = new Text(TextContentManager.getLangagesText().getRestrictLabel());
        private final Region filler = new Region();

        private final HBox retrictLabelBox = new HBox();

        private CheckBox checkBox = new CheckBox();
        private final ImageView logoView;

        private final ImageView homeView;
        public boolean Popular;
        private final Button searchButton = LyricsAppController.button(TextContentManager.getLangagesText().getSearchLabel());
        private PlaylistsList play;
        private int index = -1;


        public ResearchByTitleArtistPageView(PlaylistsList play, int index){

        if (index != -1) this.play = play;
        this.index = index;
        //  logo
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


        /**Enleve l'autofocus sur les champs d'entré**/
        titleTextField.setFocusTraversable(false);
        artistTextField.setFocusTraversable(false);



        //setcheckBox
        checkBox.setStyle("-fx-font-size: 10pt; ");
        VBox.setMargin(checkBox,new Insets(60,0,0,450));
        checkBox.setOnAction(e -> {
            Popular = checkBox.isSelected();
            System.out.println("La casFe à cocher est " + (Popular ? "cochée" : "décochée"));
        });




        //set children
        top.getChildren().addAll(homeView,logoView);
        artistTitleArea.getChildren().addAll(titleTextField,artistTextField);
        retrictLabelBox.getChildren().addAll(restrictLabel,checkBox,filler,searchButton);
        content.getChildren().addAll(artistTitleArea,retrictLabelBox);

        HBox.setHgrow(filler, Priority.ALWAYS);

        // set in root
        BorderPane root = new BorderPane();
        root.setTop(top);
        root.setCenter(content);

        titleTextField.setOnKeyPressed(ke -> {if (ke.getCode().equals(KeyCode.ENTER)) {search();}});
        artistTextField.setOnKeyPressed(ke -> { if (ke.getCode().equals(KeyCode.ENTER)) {search();}});
        searchButton.setOnAction(e -> search());

        //Retour a l'acceuil
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

        // Text Field
        private void setPromptTexts(){
            artistTextField.setPromptText(TextContentManager.getLangagesText().getArtist());
            titleTextField.setPromptText(TextContentManager.getLangagesText().getTitle());
        }

        //alignment,spacing,size
        private void positionElements(){
            BorderPane.setAlignment(logoView, Pos.CENTER);
            BorderPane.setAlignment(content,Pos.CENTER);
            artistTitleArea.setAlignment(Pos.CENTER);
            artistTitleArea.setSpacing(20);
            retrictLabelBox.setAlignment(Pos.CENTER);
            searchButton.setPrefSize(100, 40);
            artistTextField.setPrefSize(300,50);
            titleTextField.setPrefSize(300,50);
        }

        // marge interne
        private void setPadding(){
            Insets padding = new Insets(10,10,10,10);
            Insets marging =  new Insets(12, 0, 0, 0);
            artistTitleArea.setPadding(padding);
            HBox.setMargin(restrictLabel,new Insets(0,0,0,140));
            HBox.setMargin(searchButton,new Insets(0,140,0,140));
            HBox.setMargin(homeView,new Insets(10,157.5,0,10));
            HBox.setMargin(checkBox,marging);
            VBox.setMargin(artistTitleArea,new Insets(50,0,0,0));

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

        public void search(){
            String titleInput = titleTextField.getText().replace(" ","%20").toLowerCase();
            String artistInput = artistTextField.getText().replace(" ", "%20").toLowerCase();

            switch (checkInputValidity(titleInput,artistInput)){
                case 0:

                    try {
                        InputStream stream = GetData.searchLyric(titleInput,artistInput);
                        if (this.index == -1)
                            LyricsAppController.goToResearchPageView(PopularSongTest(LyricsAppController.makeListSong(stream)) ,getScene());
                        else
                            LyricsAppController.goToResearchPageView(PopularSongTest(LyricsAppController.makeListSong(stream)),getScene(),play,index);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 1:
                    errorMessage(TextContentManager.getLangagesText().getEmptyFieldArtistTitle());
                    break;
                case 2:
                    errorMessage(TextContentManager.getLangagesText().getShortInput());
                    break;
                case 3:
                    errorMessage(TextContentManager.getLangagesText().getBannedWordTitle());
                    break;
                case 4 :
                    errorMessage(TextContentManager.getLangagesText().getBannedWordArtist());
                    break;
            }
        }
        private int checkInputValidity(String titleInput, String artistInput) {
        if (titleInput.isEmpty() || artistInput.isEmpty()) {
            return 1; // empty input error
        } else if (titleInput.length() < 2 || artistInput.length() < 2) {
            return 2; // input length error
        } else if (GetData.TestBanWord(titleInput)) {
            return 3; // banned word error
        }else if (GetData.TestBanWord(artistInput)) {
            return 4; // banned word error
        } else {
            return 0; // valid input
        }
    }
        private void errorMessage(String string){
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-font-size: 18px;");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> tooltip.hide())
        );
        tooltip.setText(string);
        tooltip.show(artistTitleArea.getScene().getWindow(),
                artistTitleArea.getScene().getWindow().getX() + artistTitleArea.getBoundsInParent().getMinX() + 280,
                artistTitleArea.getScene().getWindow().getY() + artistTitleArea.getBoundsInParent().getMaxY() + 140);
        tooltip.setAutoHide(true);
        timeline.play();
    }
        public void modifytext(){
            titleTextField.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 15));
            restrictLabel.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 20));
            restrictLabel.setFill(Color.WHITE);
            artistTextField.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 15));
        }
}

