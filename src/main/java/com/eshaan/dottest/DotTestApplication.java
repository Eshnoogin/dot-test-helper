package com.eshaan.dottest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.eshaan.dottest.sceneone.SceneOneView;

public class DotTestApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        SceneOneView sceneone = new SceneOneView();
        sceneone.setPadding(new Insets(25));

        // Set the scene
        Scene scene = new Scene(sceneone, 2000, 1125);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}