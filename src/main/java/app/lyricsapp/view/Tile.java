package app.lyricsapp.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;

public class Tile {

    private HBox CenterBar = new HBox();
    private  HBox leftCenterBar = new HBox();


    public  Tile(Text title ,Text artist ,  ImageView cover) throws FileNotFoundException {

        Insets marginTopLogo = new Insets(8,0,8,10);
        ////////
        CenterBar.setBackground(new Background(new BackgroundFill(Color.rgb(243, 238, 237),
                new CornerRadii(20),null)));

        //setImageSize and Border_Radius
        setImage(cover);

        //set TextColor

        title.setFill(Color.rgb(182, 60, 63));
        title.setFont(Font.font("Verdana", FontWeight.BOLD,15));

        artist.setFill(Color.rgb(182, 60, 63));
        artist.setFont(Font.font("Verdana", FontWeight.BOLD,15));


        leftCenterBar.getChildren().addAll(title,artist);
        leftCenterBar.setSpacing(30);
        CenterBar.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.25), 4, 0, 0, 4);");
        CenterBar.getChildren().addAll(cover,leftCenterBar);
        CenterBar.setAlignment(Pos.CENTER_LEFT);
        leftCenterBar.setAlignment(Pos.CENTER_LEFT);

        //setMargin
        Insets insetHBox = new Insets(0,0,0,30);
        setMargin(marginTopLogo,cover,insetHBox,title,artist);


        /** all mouse event **/

    }
    public void setImage(ImageView cover){

        cover.setFitWidth(100);
        cover.setFitHeight(100);
        //ça rend les images arrondis et rajoute l'ombre
        Rectangle clip = new Rectangle(cover.getFitWidth(), cover.getFitHeight());
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        clip.setStroke(Color.BLACK);
        cover.setClip(clip);
        cover.setEffect(new DropShadow(10,Color.BLACK));
    }
    public void setMargin(Insets marginTopLogo, ImageView cover,Insets insetHBox,Text title ,Text artist ){
        //setMargin
        HBox.setMargin(cover,marginTopLogo);
        HBox.setMargin(title,insetHBox);
        HBox.setMargin(artist,insetHBox);
    }
    public HBox getLeftCenterBar() {
        return leftCenterBar;
    }

    public HBox getCenterBar() {
        return CenterBar;
    }



    // Sous-classe
    public static class FavoriteTile extends Tile {
       public FavoriteTile(Text title ,Text artist , ImageView cover,ImageView heart) throws FileNotFoundException {
           super(title,artist,cover);
          // getLeftCenterBar().getChildren().add();
           HBox.setMargin(heart,new Insets(0,0,0,30));
          // getCenterBar().getChildren().add(1,heart);
       }
    }


}

