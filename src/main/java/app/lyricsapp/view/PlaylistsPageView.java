package app.lyricsapp.view;

import app.lyricsapp.controller.DialogController;
import app.lyricsapp.controller.LyricsAppController;
import app.lyricsapp.model.Favorites;
import app.lyricsapp.model.PlayList;
import app.lyricsapp.model.PlaylistsList;
import app.lyricsapp.model.TextContentManager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.IOException;

public class PlaylistsPageView extends PageView{
   private final Scene scene;
   private final VBox centerContent = new VBox();
   private final Region filler = new Region();

   private HBox topBar;
   private Text myPlaylistText;
   private ImageView addPlaylistIcon;
   private ImageView goBackArrow;
   PlaylistsList play = new PlaylistsList();
   Favorites fav = new Favorites();
   public PlaylistsPageView() throws IOException{


      //Page Title
      myPlaylistText = new Text(TextContentManager.getLangagesText().getPlaylists());
      myPlaylistText.setFill(Color.WHITE);
      myPlaylistText.setStyle("-fx-font-size: 24;-fx-font-weight: BOLD");

      //Add Playlist Icon
      Image addImage = new Image(new FileInputStream("ImageAssets/addToPlaylistWhite.png"));
      addPlaylistIcon = new ImageView(addImage);
      addPlaylistIcon.setFitHeight(60);
      addPlaylistIcon.setFitWidth(60);
      addPlaylistIcon.setPreserveRatio(true);

      HBox.setMargin(addPlaylistIcon,new Insets(0,10,0,0));
      addPlaylistIcon.setStyle("-fx-cursor: hand");


      //Go Back Code
      Image arrowImage = new Image(new FileInputStream("ImageAssets/home.png"));
      centerContent.setPrefSize(885,700);
      goBackArrow = new ImageView(arrowImage);
      goBackArrow.setFitHeight(40);
      goBackArrow.setFitWidth(40);
      goBackArrow.setPreserveRatio(true);
      goBackArrow.setStyle("-fx-cursor: hand");
      HBox.setMargin(goBackArrow,new Insets(0,340-(myPlaylistText.getLayoutBounds().getWidth()/2),0,10));


      //
      BorderPane root = new BorderPane();
      topBar = new HBox();
      topBar.setPrefSize(900,90);
      topBar.setAlignment(Pos.CENTER_LEFT);
      topBar.getChildren().addAll(goBackArrow,myPlaylistText,filler,addPlaylistIcon);
      HBox.setHgrow(filler, Priority.ALWAYS);

      root.setTop(topBar);
      setPlayLists(play);
      ScrollPane scrollPaneCenter = new ScrollPane(centerContent);
      root.setCenter(scrollPaneCenter);
      root.setStyle("-fx-background-color: transparent;");
      scene = new Scene(root,900,700);
      Color orangeSable = Color.rgb(234, 168, 98);
      scene.setFill(orangeSable);


      //Mouse Event
      goBackArrow.setOnMouseClicked((click) ->{
         try {
            LyricsAppController.goToHomeChoosePageView(scene);
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      });
      addPlaylistIcon.setOnMouseClicked((click)->{
         DialogController.createPlayListDialog(play,getScene());
        // createPlayListDialog(play);
         System.out.println(play.getSize());
         try {
            LyricsAppController.goToPlaylistsListPageView(this.scene);
         } catch (Exception e){
            System.out.println("MEEEE");
            throw  new RuntimeException();

         }

      });

   }


   private void setPlayLists(PlaylistsList playLists) throws IOException{

      setFavorites();
      for (PlayList playList1 : playLists.get()){
         //text
         Text title = new Text(playList1.getName());
         Text artist = new Text(playList1.getSize()+TextContentManager.getLangagesText().getSongs());

         //cover
         Image image =new Image(new FileInputStream("./ImageAssets/notFound.png"));
         ImageView cover = new ImageView(image);
         cover.setFitWidth(100);
         cover.setFitHeight(100);

         //delete
         Image deleteImage =new Image(new FileInputStream("ImageAssets/delete.png"));
         ImageView delete = new ImageView(deleteImage);
         delete.setFitWidth(50);
         delete.setFitHeight(50);
         HBox.setMargin(delete,new Insets(0,0,0,10));
         //Tile
         Tile tile = new Tile(title,artist, cover);
         Image renameImage =new Image(new FileInputStream("ImageAssets/edit.png"));
         ImageView rename = new ImageView(renameImage);
         rename.setFitWidth(50);
         rename.setFitHeight(50);
         HBox.setMargin(delete,new Insets(0,30,0,10));
         Region filler2 = new Region();
         HBox.setHgrow(filler2, Priority.ALWAYS);
         tile.getCenterBar().getChildren().addAll(filler2,rename,delete);
         //Mouse Event
         cover.setOnMouseClicked((click) ->{
            try {
               LyricsAppController.goToSinglePlayListPageView(playLists.getPlaylistIndex(playList1),getScene());
            } catch (IOException e) {
               throw new RuntimeException(e);
            }
         });
         rename.setOnMouseClicked((click) ->{
            DialogController.renamePlayListDialog(playLists, playLists.getPlaylistIndex(playList1),getScene());
            try {
               LyricsAppController.goToPlaylistsListPageView(getScene());
            } catch (IOException e) {
               throw new RuntimeException(e);
            }
         });

         tile.getLeftCenterBar().setOnMouseClicked((click) ->{
            try {
               LyricsAppController.goToSinglePlayListPageView(playLists.getPlaylistIndex(playList1),getScene());
            } catch (IOException e) {
               throw new RuntimeException(e);
            }
         });

         delete.setOnMouseClicked((click) ->{
               DialogController.deleteElementAlert(getScene(),play,playList1);
         });
         tile.getCenterBar().setOnMouseEntered(event -> {
            tile.getCenterBar().setStyle("-fx-effect: dropshadow(three-pass-box, rgba(244, 64, 13, 0.5), 2, 10, 0, 0);");
         });

         tile.getCenterBar().setOnMouseExited(event -> {
            tile.getCenterBar().setStyle("");
            tile.getCenterBar().setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 4, 0, 0, 4);");
         });

         centerContent.getChildren().add(tile.getCenterBar());
         centerContent.setSpacing(10);
         centerContent.setPadding(new Insets(8,0,0,0));
      }

   }
   private void setFavorites() throws IOException{

      Text title = new Text(TextContentManager.getLangagesText().getFavorite());
      Text artist = new Text(fav.getSize()+TextContentManager.getLangagesText().getSongs());

      //cover
      Image image =new Image(new FileInputStream("ImageAssets/heart_1.png"));
      ImageView cover = new ImageView(image);
      cover.setFitWidth(100);
      cover.setFitHeight(100);


      //Tile
      Tile tile = new Tile(title,artist, cover);


      //Mouse Event
      cover.setOnMouseClicked((click) ->{
         try {
            LyricsAppController.goToFavoritesPageView(getScene());
         } catch (IOException e) {
            throw new RuntimeException(e);
         }
      });

      tile.getLeftCenterBar().setOnMouseClicked((click) ->{
         try {
            LyricsAppController.goToFavoritesPageView(getScene());
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
      centerContent.getChildren().add(tile.getCenterBar());

      centerContent.setSpacing(10);
   }
   public Scene getScene(){
      return this.scene;
   }




}
