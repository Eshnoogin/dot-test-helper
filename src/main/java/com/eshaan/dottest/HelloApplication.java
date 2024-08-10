package com.eshaan.dottest;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

import com.eshaan.dottest.sceneone.FootballFieldCoordinates;
import com.eshaan.dottest.sceneone.FootballFieldView;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Pane root = new Pane();

        FootballFieldView field = new FootballFieldView(1920, 840);
        VBox fieldVBox = new VBox(field);
        VBox.setMargin(field, new Insets(50, 50, 50, 50));
        root.getChildren().add(fieldVBox);

        Scene scene = new Scene(root, 2000, 1125);

        stage.setScene(scene);
        stage.show();

        stage.show();

    }

    public static void main(String[] args) {
        System.out.println(args);
        launch();
    }
}