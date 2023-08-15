package app.lyricsapp.view;

import app.lyricsapp.controller.LyricsAppController;
import app.lyricsapp.model.Favorites;
import app.lyricsapp.model.Song;
import app.lyricsapp.model.TextContentManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class FavorisPageView extends PageView {

    private final Scene scene;
    private final VBox FavoritesContentCenter = new VBox();

    private final HBox lyricsFavArea = new HBox();
    private final ImageView arrow;

    public FavorisPageView() throws IOException {

            Favorites favorites2 = new Favorites();
             //favorite Artist
            Paint rougePourpre = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, new Stop(0, Color.RED), new Stop(1, Color.PURPLE));
            Text ArtisteFavoris = new Text(favorites2.getFavoriteArtist(favorites2.get()).toUpperCase());
            ArtisteFavoris.setStyle("-fx-font-weight: bold;-fx-font-size: 20");
            ArtisteFavoris.setFill(rougePourpre);
            VBox vBox = new VBox();
            vBox.setAlignment(Pos.CENTER);

            HBox.setMargin(ArtisteFavoris,new Insets(20,0,0,50));

            //favorite Image
            Image image = new Image(new FileInputStream("ImageAssets/fleche-vers-le-haut.png"));
            FavoritesContentCenter.setPrefSize(885,700);
            arrow = new ImageView(image);
            arrow.setRotate(-90);
            arrow.setFitHeight(50);
            arrow.setFitWidth(50);
            arrow.setPreserveRatio(true);
            // set in root
            setListSongCenter(favorites2.get());
            HBox topBar = new HBox();
            Text vosFavoris = new Text(TextContentManager.getLangagesText().getFavoritePageTitle());
            vosFavoris.setFill(Color.WHITE);
            vosFavoris.setFont(new Font(40));
            vBox.getChildren().addAll(vosFavoris,ArtisteFavoris);


            HBox.setMargin(arrow,new Insets(0,385-(vosFavoris.getLayoutBounds().getWidth()/2),0,10));
            //rajouter à la top bar un element
            topBar.getChildren().addAll(arrow,vBox);
            topBar.setAlignment(Pos.CENTER_LEFT);
            BorderPane root = new BorderPane();
            root.setTop(topBar);
            ScrollPane scrollPaneCenter = new ScrollPane(FavoritesContentCenter);
            root.setCenter(scrollPaneCenter);
            positionElements();
            setPadding();
            root.setStyle("-fx-background-color: transparent;");
            scene = new Scene(root,900,700);
        Color orangeSable = Color.rgb(224, 170, 109);
        scene.setFill(orangeSable); //  #e0aa6d <-- we need this color

        arrow.setOnMouseClicked((click) ->{
            try {
                LyricsAppController.goToPlaylistsListPageView(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        arrow.setOnMouseEntered(event -> arrow.setStyle("-fx-opacity: 0.5;"));

        arrow.setOnMouseExited(event -> arrow.setStyle("-fx-opacity: 1;"));

        }



        //alignment,spacing,size
        private void positionElements(){

            FavoritesContentCenter.setPadding(new Insets(2,2,0,2));

        }

        // marge interne
        private void setPadding(){
            Insets padding = new Insets(10,10,10,10);
            lyricsFavArea.setPadding(padding);
        }

        public Scene getScene() {
            return scene;
        }




    Favorites favorites = new Favorites();
    public void setListSongCenter(List<Song> songs) throws IOException {



        for (Song song : songs){
            ImageView heart = (Favorites.isFavorite(song)) ? FavorisPageView.Heart() : FavorisPageView.brokenHeart();

            //cover
            ImageView cover = LyricsAppController.setCover(song.getCoverUrl());

            Text title = new Text(song.getTitle());
            Text artist = new Text(song.getArtist());

            //tile
            Tile.FavoriteTile tile = new Tile.FavoriteTile(title,artist,cover,heart);
            tile.getCenterBar().getChildren().add(1,heart);

            //clickEvent
            tile.getLeftCenterBar().setOnMouseClicked((click) ->{
                try {
                    LyricsAppController.goToLyricsPageView(song,getScene(),false,songs,-2);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            cover.setOnMouseClicked((click) ->{
                try {
                    LyricsAppController.goToLyricsPageView(song,getScene(),false,songs,-2);
                } catch (IOException e) {
                    throw new RuntimeException(e);
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
                        favorites.add(song);

                        ImageView heart2 = FavorisPageView.Heart();
                        heart.setImage(heart2.getImage());




                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            });


            tile.getCenterBar().setOnMouseEntered(event -> {



                tile.getCenterBar().setStyle("-fx-effect: dropshadow(three-pass-box, rgba(244, 64, 13, 0.5), 2, 10, 0, 0);");
            });

            tile.getCenterBar().setOnMouseExited(event -> {



                tile.getCenterBar().setStyle("");
                tile.getCenterBar().setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 4, 0, 0, 4);");


            });



            //////////////////////////////
            FavoritesContentCenter.getChildren().add(tile.getCenterBar());



            FavoritesContentCenter.setSpacing(10);



        }
    }
    //favCommand(favorites);
    public static ImageView brokenHeart() throws FileNotFoundException {
        Image image =new Image(new FileInputStream("ImageAssets/addToFavBlack.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        imageView.setPreserveRatio(true);
        return imageView;
    }

    public static ImageView Heart() throws FileNotFoundException {
        Image image =new Image(new FileInputStream("./ImageAssets/noFavWhite.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        imageView.setPreserveRatio(true);
        return imageView;
    }



    }

