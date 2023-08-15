package app.lyricsapp.view;

import app.lyricsapp.controller.DialogController;
import app.lyricsapp.controller.LyricsAppController;
import app.lyricsapp.model.PlayList;
import app.lyricsapp.model.PlaylistsList;
import app.lyricsapp.model.Song;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.IOException;

public class SinglePlaylistPageView extends PageView{


    private final Scene scene;
    private final VBox centerContent = new VBox();
    private HBox topBar;
    private Text myPlaylistText;
    private ImageView addPlaylistIcon;
    private ImageView goBackArrow;
    PlaylistsList play = new PlaylistsList();
    PlayList currentPlayList;
    int index;

    public SinglePlaylistPageView(int index) throws IOException {

        this.index = index;
        this.currentPlayList = play.get().get(index);

        //Page Title
        myPlaylistText = new Text(currentPlayList.getName());
        myPlaylistText.setFill(Color.WHITE);
        myPlaylistText.setFont(new Font(20));

        //Add Playlist Icon
        Image addImage = new Image(new FileInputStream("ImageAssets/addToPlaylistWhite.png"));
        addPlaylistIcon = new ImageView(addImage);
        addPlaylistIcon.setFitHeight(64);
        addPlaylistIcon.setFitWidth(64);

        //Go Back Code
        Image arrowImage = new Image(new FileInputStream("ImageAssets/fleche-vers-le-haut.png"));
        centerContent.setPrefSize(885,700);
        goBackArrow = new ImageView(arrowImage);
        goBackArrow.setRotate(-90);
        goBackArrow.setFitHeight(50);
        goBackArrow.setFitWidth(50);
        goBackArrow.setPreserveRatio(true);

        //
        BorderPane root = new BorderPane();
        topBar = new HBox();
        HBox.setMargin(myPlaylistText,new Insets(15,0,15,280));
        HBox.setMargin(goBackArrow,new Insets(25,80,15,0));
        HBox.setMargin(addPlaylistIcon,new Insets(25,0,15,300));
        topBar.setAlignment(Pos.CENTER);
        //TopBar
        topBar.getChildren().addAll(goBackArrow,myPlaylistText,addPlaylistIcon);
        root.setTop(topBar);
        setSongs();
        ScrollPane scrollPaneCenter = new ScrollPane(centerContent);
        root.setCenter(scrollPaneCenter);
        root.setStyle("-fx-background-color: transparent;");
        scene = new Scene(root,900,700);
        Color orangeSable = Color.rgb(234, 168, 98);
        scene.setFill(orangeSable);

        //Mouse Event
        goBackArrow.setOnMouseClicked((click) ->{
            try {

                LyricsAppController.goToPlaylistsListPageView(scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        addPlaylistIcon.setOnMouseClicked((click)->{
            DialogController.chooseResearchModeDialog(getScene(),this.play,this.index);
            //System.out.println(play.getSize());
            try {
                LyricsAppController.goToSinglePlayListPageView(index,this.scene);
            } catch (Exception e){
                //System.out.println("MEEEE");
                throw  new RuntimeException();

            }

        });


        goBackArrow.setOnMouseEntered(event -> goBackArrow.setStyle("-fx-opacity: 0.5;"));

        goBackArrow.setOnMouseExited(event -> goBackArrow.setStyle("-fx-opacity: 1;"));

    }



    private void setSongs() throws IOException{

        for (Song song : currentPlayList.getSongs()){
            //text
            Text title = new Text(song.getTitle());
            Text artist = new Text(song.getArtist());
            String UrlCover=song.getCoverForce();
            //cover
            ImageView cover = LyricsAppController.setCover(song.getCoverForce());
            song.setCoverUrl(UrlCover);
            cover.setFitWidth(100);
            cover.setFitHeight(100);

            //rubish
            Image Rubishimage =new Image(new FileInputStream("ImageAssets/delete.png"));
            ImageView rubish = new ImageView(Rubishimage);
            rubish.setFitWidth(50);
            rubish.setFitHeight(50);
            HBox.setMargin(rubish,new Insets(0,0,0,10));
            //Tile
            Tile tile = new Tile(title,artist, cover);
            tile.getCenterBar().getChildren().add(1,rubish);
            //Mouse Event
            cover.setOnMouseClicked((click) ->{
                try {
                    LyricsAppController.goToLyricsPageView(song,getScene(),true,currentPlayList.getSongs(),this.index);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            tile.getLeftCenterBar().setOnMouseClicked((click) ->{
                try {
                    LyricsAppController.goToLyricsPageView(song,getScene(),true,currentPlayList.getSongs(),this.index);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            rubish.setOnMouseClicked((click) ->{
                try {
                    currentPlayList.remove(song);
                    play.writeIntoFile("serializationFiles/Play");
                    LyricsAppController.goToSinglePlayListPageView(this.index,getScene());

                } catch (IOException e) {
                    throw new RuntimeException(e);
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
            centerContent.getChildren().add(tile.getCenterBar());
            centerContent.setSpacing(10);
            centerContent.setPadding(new Insets(8,0,0,0));


        }

    }
    public Scene getScene(){
        return this.scene;
    }


}
