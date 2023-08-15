package app.lyricsapp.view;

import app.lyricsapp.controller.DialogController;
import app.lyricsapp.model.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ResearchPageView extends PageView{

    private Scene scene;

    private final TextField titleTextField = new TextField();

    private final TextField artistTextField = new TextField();

    private final TextField lyricsTextField = new TextField();

    private final BorderPane topBar = new BorderPane();

    private final HBox artistTitleArea = new HBox();

    private final HBox lyricsArea = new HBox();

    private final HBox popularAndSearchBox = new HBox();

    private final VBox searchBox = new VBox();
    private final VBox buttonBox = new VBox();

    private final Button goToPlaylistButton = app.lyricsapp.controller.LyricsAppController.button(TextContentManager.getLangagesText().getGoToPlaylist());

    private final Button switchB = app.lyricsapp.controller.LyricsAppController.button(TextContentManager.getLangagesText().getSearchByLyrics());

    private final Region filler = new Region();
    private final Text restrictLabel = new Text(TextContentManager.getLangagesText().getRestrictLabel());

    private final CheckBox checkBox = new CheckBox();

    private final Button searchButton = app.lyricsapp.controller.LyricsAppController.button(TextContentManager.getLangagesText().getSearchLabel());

    private ImageView logoView;

    private final VBox listSongCenter = new VBox();

    public boolean Popular;

    public boolean mod=true;
    PlaylistsList play = new PlaylistsList();
    PlayList playList;
    int index = -1;

    public ResearchPageView(PlaylistsList playLists, int index){
        if (index != -1){
            this.play = playLists;
            this.index = index;
            this.playList = playLists.get().get(index);
        }

        //  logo
        try {
            Image logo = new Image(new FileInputStream("./ImageAssets/LyricsPageView.png"),230,100,false,false);
            logoView = new ImageView(logo);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //TODO REMOVE THIS AT THE END

        logoView.setOnMouseClicked((click) ->{
            try {

                app.lyricsapp.controller.LyricsAppController.goToHomeChoosePageView(getScene());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        /**TOP BAR*/
        topBar.setLeft(logoView);
        topBar.setCenter(searchBox);
        topBar.setRight(buttonBox);


        /**STYLE*/
        restrictLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px");
        restrictLabel.setFill(Color.WHITE);
        HBox.setHgrow(filler, Priority.ALWAYS);


        /** Set children*/
        artistTitleArea.getChildren().addAll(titleTextField,artistTextField);
        lyricsArea.getChildren().addAll(lyricsTextField);
        searchBox.getChildren().addAll(artistTitleArea, popularAndSearchBox);
        popularAndSearchBox.getChildren().addAll(restrictLabel,checkBox,filler,searchButton);
        buttonBox.getChildren().addAll(switchB,goToPlaylistButton);


        // set in root
        setProperty();
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        listSongCenter.setPrefSize(885,700);
        ScrollPane scroll = new ScrollPane();
        scroll.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255),new CornerRadii(1),null)));
        scroll.setContent(listSongCenter);
        root.setCenter(scroll);
        root.setStyle("-fx-background-color: transparent;");
        scene = new Scene(root,900,700);
        Color orangeSable = Color.rgb(234, 168, 98);
        scene.setFill(orangeSable);
        listSongCenter.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255),new CornerRadii(1),null)));

    }

    public Scene getScene() {
        return scene;
    }

    private void setPromptTexts(){
        artistTextField.setPromptText(TextContentManager.getLangagesText().getArtist());
        titleTextField.setPromptText(TextContentManager.getLangagesText().getTitle());
        lyricsTextField.setPromptText(TextContentManager.getLangagesText().getLyrics());
    }


    private void setMargin(){
        VBox.setMargin(popularAndSearchBox,new Insets(10,55,10,55));
    }
    private void setAlignement(){
        artistTitleArea.setAlignment(Pos.CENTER);
        lyricsArea.setAlignment(Pos.CENTER);
        searchButton.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setAlignment(Pos.CENTER_LEFT);
        popularAndSearchBox.setAlignment(Pos.CENTER);
        searchBox.setAlignment(Pos.BOTTOM_CENTER);
        restrictLabel.setTextAlignment(TextAlignment.LEFT);
    }

    private void actionEvent(){
        /**Redirect to playlist*/
        goToPlaylistButton.setOnMouseClicked((click) ->{
            try {
                //app.lyricsapp.controller.LyricsAppController.goToPlaylistsListPageView(getScene());
                        app.lyricsapp.controller.LyricsAppController.goToPlaylistsListPageView(getScene());
                //LyricsAppController.goToPlaylistsListPageView(getScene());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        /**Restrict to popular song only*/
        checkBox.setOnAction(e -> { Popular = checkBox.isSelected();});

        /** Switch between the search mode*/
        switchB.setOnMouseClicked((click) -> {
            searchBox.getChildren().clear();
            if (mod) {
                switchB.setText(TextContentManager.getLangagesText().getSearchByTitleArtist());
                searchBox.getChildren().addAll(lyricsArea, popularAndSearchBox);
                searchButton.setOnAction(e -> {searchLyrics();});

            } else {
                switchB.setText(TextContentManager.getLangagesText().getSearchByLyrics());
                searchBox.getChildren().addAll(artistTitleArea, popularAndSearchBox);
                searchButton.setOnAction(e -> {searchArtistTitle();});
            }
            mod = !mod;
            System.out.println("switch");
        });

        /** Search */
        titleTextField.setOnKeyPressed(ke -> {if (ke.getCode().equals(KeyCode.ENTER)) {searchArtistTitle();}});
        artistTextField.setOnKeyPressed(ke -> { if (ke.getCode().equals(KeyCode.ENTER)) {searchArtistTitle();}});
        lyricsTextField.setOnKeyPressed(ke -> {if (ke.getCode().equals(KeyCode.ENTER)) {searchLyrics();}});
        searchButton.setOnAction(e -> {searchArtistTitle();});

    }

    private  void setSize(){
        lyricsTextField.setPrefSize(350,40);
        switchB.setPrefWidth(200);
        goToPlaylistButton.setPrefWidth(200);
        buttonBox.setPrefSize(210,100);
        buttonBox.setSpacing(10);
        artistTitleArea.setSpacing(10);
        artistTextField.setPrefSize(170,40);
        titleTextField.setPrefSize(170,40);
        lyricsTextField.setAlignment(Pos.TOP_LEFT);
    }

    private void setProperty(){
        setMargin();
        setSize();
        setPromptTexts();
        actionEvent();
        setAlignement();
    }

    Favorites favorites = new Favorites();
    public void setListSongCenter(List<Song> songs) throws IOException {
        Insets marginTopLogo = new Insets(8,0,8,10);
        String borderstyle="-fx-border-color:white;-fx-border-width:4;-fx-border-style:solid;-fx-border-radius:8;border-insets:-4";
        for (Song song : songs){
            //imageview Playlist
            Image addPlaylist = new Image(new FileInputStream("ImageAssets/addToPlaylistBlack.png"));
            ImageView PlaylistImage = new ImageView(addPlaylist);
            PlaylistImage.setFitHeight(35);
            PlaylistImage.setFitWidth(35);
            PlaylistImage.setPreserveRatio(true);
            HBox.setMargin(PlaylistImage,new Insets(40,0,0,35));


            ///////////
            HBox CenterBar = new HBox();
            HBox leftCenterBar = new HBox();
            HBox.setMargin(leftCenterBar,new Insets(5,0,0,0));
            CenterBar.setBackground(new Background(new BackgroundFill(Color.rgb(243, 238, 237),new CornerRadii(20),null)));
            String UrlCover=song.getCoverForce();
            //cover

            ImageView cover =   app.lyricsapp.controller.LyricsAppController.setCover(song.getCoverForce());
            song.setCoverUrl(UrlCover);
            cover.setFitWidth(100);
            cover.setFitHeight(100);

            //ça rend les images arrondis et rajoute l'ombre
            Rectangle clip = new Rectangle(cover.getFitWidth(), cover.getFitHeight());
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            clip.setStroke(Color.BLACK);
            cover.setClip(clip);
            cover.setEffect(new DropShadow(10,Color.BLACK));

            //tentative de mettre une bordure (pas réussie)
            BorderPane borderPane = new BorderPane(cover);
            borderPane.setStyle(borderstyle);
            HBox.setMargin(cover,marginTopLogo);
            Text title = new Text(song.getTitle());
            title.setFill(Color.rgb(182, 60, 63));
            title.setFont(Font.font("Verdana", FontWeight.BOLD,15));
            Text artist = new Text(song.getArtist());
            artist.setFill(Color.rgb(182, 60, 63));
            artist.setFont(Font.font("Verdana", FontWeight.BOLD,15));

            ImageView heart = (Favorites.isFavorite(song)) ? FavorisPageView.Heart() : FavorisPageView.brokenHeart();
            /**ça affiche la bordure mais Mal decommente le pour essayer**/
            leftCenterBar.getChildren().addAll(title,artist);
            leftCenterBar.setSpacing(30);
            CenterBar.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 4, 0, 0, 4);");

           // leftCenterBar.getChildren().add(cover);
            CenterBar.getChildren().addAll(cover,heart,PlaylistImage,leftCenterBar);

            Insets insetHBox = new Insets(40,0,0,30);
            Insets insetHeart = new Insets(40,5,0,30);
            HBox.setMargin(title,insetHBox);
            HBox.setMargin(artist,insetHBox);
            HBox.setMargin(heart,insetHeart);
            /** all mouse event **/
            //clickEvent
            leftCenterBar.setOnMouseClicked((click) ->{
                try {
                    app.lyricsapp.controller.LyricsAppController.goToLyricsPageView(song,getScene(),false,songs,-1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });

            cover.setOnMouseClicked((click) ->{
                try {
                    app.lyricsapp.controller.LyricsAppController.goToLyricsPageView(song,getScene(),false,songs,-1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            PlaylistImage.setOnMouseClicked((click)-> {

                if (this.index != -1){

                    try {
                        this.playList.append(song);
                    } catch (AlreadyExistingElementException e) {
                        DialogController.alreadyExistingElementAlert(song.getTitle()+TextContentManager.getLangagesText().getAlreadyExists()+this.playList.getName(),
                                TextContentManager.getLangagesText().getNewSong(),getScene());

                    }

                    try {
                        this.play.writeIntoFile("serializationFiles/Play");
                        app.lyricsapp.controller.LyricsAppController.goToSinglePlayListPageView(index,getScene());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }
                else {
                    if(this.play.get().size() == 0)
                        DialogController.createPlayListDialog(this.play,getScene());
                    DialogController.choosePlayListDialog(song,this.play,getScene());

                }


            });

            heart.setOnMouseClicked((click)-> {
                if (Favorites.isFavorite(song)) {
                    try {
                        favorites.removeInSearch(song);

                        ImageView Bheart = FavorisPageView.brokenHeart();
                        heart.setImage(Bheart.getImage());


                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    try {
                        //song.setCoverUrl(UrlCover);
                        favorites.add(song);

                        ImageView heart2 = FavorisPageView.Heart();
                        heart.setImage(heart2.getImage());


                        //sons.add(song);

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            });


            CenterBar.setOnMouseEntered(event -> {


                //CenterBar.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-style: solid; -fx-border-radius: 10px;");
                CenterBar.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(244, 64, 13, 0.5), 2, 10, 0, 0);");
            });

            CenterBar.setOnMouseExited(event -> {



                CenterBar.setStyle("");
                CenterBar.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 4, 0, 0, 4);");


            });

            listSongCenter.getChildren().add(CenterBar);



            listSongCenter.setSpacing(10);

            listSongCenter.setPadding(new Insets(2,2,0,2));

        }
    }
    public List<Song> PopularSongTest(List<Song> songs){
        if(Popular){
            songs.removeIf(element -> element.getSongRank() < 7);
        }
        return songs;
    }

    public void searchArtistTitle(){
        String titleInput = titleTextField.getText().replace(" ","%20").toLowerCase();
        String artistInput = artistTextField.getText().replace(" ", "%20").toLowerCase();

        switch (checkInputValidityOfArtistTitle(titleInput,artistInput)){
            case 0:

                try {
                    InputStream stream = GetData.searchLyric(titleInput,artistInput);
                    if (this.index == -1)
                        app.lyricsapp.controller.LyricsAppController.goToResearchPageView(PopularSongTest(app.lyricsapp.controller.LyricsAppController.makeListSong(stream)) ,getScene());
                    else
                        app.lyricsapp.controller.LyricsAppController.goToResearchPageView(PopularSongTest(app.lyricsapp.controller.LyricsAppController.makeListSong(stream)),getScene(),play,index);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 1:
                errorMessage(TextContentManager.getLangagesText().getEmptyFieldArtistTitle());
                System.out.println("oui");
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

    public void searchLyrics() {
        String lyricsInput = lyricsTextField.getText().replace(" ","%20").toLowerCase();
        switch (checkInputValidityOfLyrics(lyricsInput)) {
            case 0:
                try {
                    InputStream stream = GetData.searchLyricText(lyricsInput);
                    //LyricsAppController.goToResearchPageView(PopularSongTest(LyricsAppController.makeListSong(stream)) ,getScene());
                    if (this.index == -1)
                        app.lyricsapp.controller.LyricsAppController.goToResearchPageView(PopularSongTest(app.lyricsapp.controller.LyricsAppController.makeListSong(stream)) ,getScene());
                    else
                        app.lyricsapp.controller.LyricsAppController.goToResearchPageView(PopularSongTest(app.lyricsapp.controller.LyricsAppController.makeListSong(stream)),getScene(),play,index);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case 1: // handle empty input error
                errorMessageLyrics(TextContentManager.getLangagesText().getEmptyField());
                break;
            case 2: // handle input length error
                errorMessageLyrics(TextContentManager.getLangagesText().getShortInput());
                break;
            case 3: // handle banned word error
                errorMessageLyrics(TextContentManager.getLangagesText().getBannedWordLyrics());
                break;
        }
    }

    private int checkInputValidityOfArtistTitle(String titleInput, String artistInput) {
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

    private int checkInputValidityOfLyrics(String lyricsInput) {
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


    private void errorMessage(String string){
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-font-size: 18px;");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> tooltip.hide())
        );
        tooltip.setText(string);
        tooltip.show(artistTitleArea.getScene().getWindow(),
                artistTitleArea.getScene().getWindow().getX() + artistTitleArea.getBoundsInParent().getMinX() + 280,
                artistTitleArea.getScene().getWindow().getY() + artistTitleArea.getBoundsInParent().getMaxY() + 90);
        tooltip.setAutoHide(true);
        timeline.play();
    }

    private void errorMessageLyrics(String string){
        Tooltip tooltip = new Tooltip();
        tooltip.setStyle("-fx-font-size: 18px;");
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> tooltip.hide())
        );
        tooltip.setText(string);
        tooltip.show(lyricsTextField.getScene().getWindow(),
                lyricsTextField.getScene().getWindow().getX() + lyricsTextField.getBoundsInParent().getMinX() + 280,
                lyricsTextField.getScene().getWindow().getY() + lyricsTextField.getBoundsInParent().getMaxY() + 90);
        tooltip.setAutoHide(true);
        timeline.play();
    }


}


