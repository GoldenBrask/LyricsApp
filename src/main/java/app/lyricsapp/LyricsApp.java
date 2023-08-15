package app.lyricsapp;

import app.lyricsapp.view.HomeChoosePageView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;


import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class LyricsApp extends Application {



    @Override
    public void start(Stage primaryStage) throws FileNotFoundException {

        HomeChoosePageView home = new HomeChoosePageView();
        try{

            primaryStage.setTitle("LyricsApp");
            primaryStage.setResizable(false);
            Image icon = new Image(new FileInputStream(
                    "./ImageAssets/icon.png"
            ));
            primaryStage.getIcons().add(icon);
            Scene scene = home.getScene();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch(Exception e){}


    }

    // internally calls the start() method of the Application class
    public static void main(String[] args) { launch(args);}
}
