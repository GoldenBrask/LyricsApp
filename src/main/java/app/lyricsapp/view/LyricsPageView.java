package app.lyricsapp.view;

import app.lyricsapp.controller.DialogController;
import app.lyricsapp.controller.LyricsAppController;
import app.lyricsapp.model.Favorites;
import app.lyricsapp.model.PlayList;
import app.lyricsapp.model.PlaylistsList;
import app.lyricsapp.model.Song;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class LyricsPageView extends PageView {

    private final Scene scene;

    private final VBox flecheHeart = new VBox();

    private final VBox titleArtist = new VBox();

    private final Text title = new Text();

    private final Text artist = new Text();

    private final Text lyric = new Text();

    public ImageView cover = new ImageView();
    private final Region filler = new Region();
    private final Region filler2 = new Region();
    private final VBox favAndPlaylistBox = new VBox();
    public    ImageView arrow;
    public    ImageView goToFavorite;
    public    ImageView addToPlaylist;
    public    ImageView addToFavorite;
    public    ImageView removeFromFavorite;

    public    ImageView currentState;

    public Song song;
    public boolean favOrNot;
    public List<Song> songs;
    int index ;
    PlaylistsList play = new PlaylistsList();
    PlayList playList;

    public LyricsPageView(ImageView cover,Song song,List<Song> songs,boolean bool,int index) throws FileNotFoundException {
        this.songs = songs;
        this.favOrNot = bool;
        this.index = index;


        addToPlaylist = new ImageView(new Image(new FileInputStream("ImageAssets/addToPlaylistWhite.png")));
        addToPlaylist.setFitHeight(64);
        addToPlaylist.setFitWidth(64);
        addToPlaylist.setPreserveRatio(true);

        addToFavorite = new ImageView(new Image(new FileInputStream("ImageAssets/addToFavWhite.png")));
        addToFavorite.setFitHeight(54);
        addToFavorite.setFitWidth(54);
        addToFavorite.setPreserveRatio(true);

        removeFromFavorite = new ImageView(new Image(new FileInputStream("ImageAssets/noFavOrange.png")));
        removeFromFavorite.setFitHeight(54);
        removeFromFavorite.setFitWidth(54);
        removeFromFavorite.setPreserveRatio(true);

        currentState = Favorites.isFavorite(song) ? removeFromFavorite : addToFavorite;

        //setcover
        setCoverLyricsPageView(cover);

        //setTextStyle
        setText();
        //create images
        arrowImage();
        heartImage();

        favAndPlaylistBox.getChildren().addAll(addToPlaylist,currentState);
        //set margin
        VBox.setMargin(goToFavorite,new Insets(20,0,0,0));
        flecheHeart.getChildren().addAll(arrow, goToFavorite);
        HBox.setMargin(addToPlaylist,new Insets(0,0,0,10));
        titleArtist.getChildren().addAll(title,artist);

        HBox topBar = new HBox();
        HBox.setHgrow(filler,Priority.ALWAYS);
        HBox.setHgrow(filler2,Priority.ALWAYS);
        HBox.setMargin(favAndPlaylistBox,new Insets(0,20,20,0));
        favAndPlaylistBox.setAlignment(Pos.BOTTOM_CENTER);

        topBar.getChildren().addAll(flecheHeart,filler2,cover,titleArtist,filler,favAndPlaylistBox);

        //MouseEvent(arrowImage());
        lyric.setFont(Font.font(20));
        lyric.setTranslateY(-5);
        lyric.setTextAlignment(TextAlignment.CENTER);
        lyric.setWrappingWidth(900);

        //lyrics text
        AnchorPane lyricContainer = new AnchorPane();
        lyricContainer.setPrefWidth(900);
        lyricContainer.setPrefHeight(1000);

        lyricContainer.getChildren().addAll(lyric);
        lyricContainer.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255),new CornerRadii(0),null)));
        ScrollPane center = new ScrollPane();

        center.setContent(lyricContainer);
        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(center);

        setMargin();
        root.setStyle("-fx-background-color: transparent;");
        scene = new Scene(root,900,700);
        Color orangeSable = Color.rgb(234, 168, 98);
        scene.setFill(orangeSable);

       //MouseEvent
       MouseEventArrow(arrow);
       MouseEventHeart(goToFavorite);
       MouseEventPlaylist(addToPlaylist);
       favoriteSwitch(currentState);

    }

    public Scene getScene() {
        return scene;
    }

    public void setLyric(String lyric){
        this.lyric.setText(lyric);
    }

    public void setCoverLyricsPageView(ImageView imageView){

        this.cover = imageView;
        cover.setFitWidth(100);
        cover.setFitHeight(100);
        cover.setEffect(new DropShadow(10,Color.BLACK));
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public void setTitle (String title) {this.title.setText(title);}

    public void setArtist (String artist) {this.artist.setText(artist);}

    private void setMargin(){
        Insets insetTopBar = new Insets(15);
        Insets insetArtist = new Insets(10,0,0,20);
        HBox.setMargin(flecheHeart,insetTopBar);
        HBox.setMargin(cover,insetTopBar);
        HBox.setMargin(titleArtist,new Insets(30,0,0,15));
        HBox.setMargin(cover,new Insets(30,0,0,80));

    }
    public  ImageView arrowImage() throws FileNotFoundException {
        //ImageView arrow;
        Image image = new Image(new FileInputStream("ImageAssets/fleche-vers-le-haut.png"));
        arrow = new ImageView(image);
        arrow.setRotate(-90);
        arrow.setFitHeight(50);
        arrow.setFitWidth(50);
        arrow.setPreserveRatio(true);

        return arrow;

    }
    public void MouseEventArrow(ImageView image){
            arrow.setOnMouseClicked((click) ->{
                if(index == -1) {
                    try {
                        LyricsAppController.goToResearchPageView(songs,getScene());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else if(index == -2) {
                    try {
                        LyricsAppController.goToFavoritesPageView(getScene());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    try {
                        LyricsAppController.goToSinglePlayListPageView(index,getScene());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            });

        image.setOnMouseEntered(event -> {

            image.setStyle("-fx-opacity: 0.5;");

        });

        image.setOnMouseExited(event -> {

            image.setStyle("-fx-opacity: 1;");

        });
        image.setStyle("-fx-cursor: hand;");
    }

    public  ImageView heartImage() throws FileNotFoundException {

        //logo page favorite
        Image favorites = new Image(new FileInputStream("ImageAssets/Favori.png"));
        goToFavorite = new ImageView(favorites);
        goToFavorite.setFitHeight(50);
        goToFavorite.setFitWidth(50);
        goToFavorite.setPreserveRatio(true);

        return arrow;

    }

    public void MouseEventHeart(ImageView image){
        image.setOnMouseEntered(event -> {
            image.setStyle("-fx-opacity: 0.5;");
        });
        image.setOnMouseExited(event -> {
            image.setStyle("-fx-opacity: 1;");

        });
        //click sur l'icone favoris
        image.setOnMouseClicked((click) ->{
            try {
                LyricsAppController.goToFavoritesPageView(getScene());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        image.setStyle("-fx-cursor: hand;");
    }
    public void setText(){
        Paint rougePourpre = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE,
                new Stop(0, Color.RED), new Stop(1, Color.PURPLE));
        VBox.setMargin(artist,new Insets(0,0,0,0));
        title.setFill(Color.WHITE);
        title.setStyle("-fx-font-size: 2em;-fx-font-weight: BOLD");
        artist.setFill(rougePourpre);
        artist.setFont(Font.font(25));
    }
    public   void favoriteSwitch(ImageView heart){
        Favorites favorites = new Favorites();
        heart.setOnMouseClicked((click)-> {
            if (Favorites.isFavorite(song)) {
                try {
                    favorites.removeInSearch(song);
                    addToFavorite = new ImageView(new Image(new FileInputStream("ImageAssets/addToFavWhite.png")));
                    addToFavorite.setFitHeight(54);
                    addToFavorite.setFitWidth(54);
                    addToFavorite.setPreserveRatio(true);
                    heart.setImage(addToFavorite.getImage());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                try {
                    favorites.add(song);
                    removeFromFavorite = new ImageView(new Image(new FileInputStream("ImageAssets/noFavOrange.png")));
                    removeFromFavorite.setFitHeight(54);
                    removeFromFavorite.setFitWidth(54);
                    removeFromFavorite.setPreserveRatio(true);
                    heart.setImage(removeFromFavorite.getImage());
                    System.out.println("added");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        });
    }

    public void MouseEventPlaylist(ImageView imageView){
        imageView.setOnMouseClicked((click)-> {
            if(this.play.get().size() == 0)
                DialogController.createPlayListDialog(this.play,getScene());
            DialogController.choosePlayListDialog(song,this.play,getScene());

            /*if (this.index > -1){


                try {
                    this.playList.append(song);
                } catch (AlreadyExistingElementException e) {
                    DialogController.alreadyExistingElementAlert(song.getTitle()+TextContentManager.getLangagesText().getAlreadyExists()+this.playList.getName(),
                            TextContentManager.getLangagesText().getNewSong(),getScene());
                }
                try {
                    this.play.writeIntoFile("serializationFiles/Play");
                    LyricsAppController.goToSinglePlayListPageView(index,getScene());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            else {

            }*/
        });
    }





}
