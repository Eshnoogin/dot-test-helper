package com.eshaan.dottest.sceneone;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class SceneOneView extends HBox {

    public SceneOneView() {
        super();
        setSpacing(150);
        FootballFieldView footballFieldCanvas = new FootballFieldView(1200, 596);
        VBox footballFieldBox = new VBox();
        footballFieldBox.setSpacing(15);
        footballFieldBox.setAlignment(Pos.TOP_CENTER);

        HBox saveCheckBox = new HBox();
        saveCheckBox.setAlignment(Pos.CENTER);
        saveCheckBox.setSpacing(10);
        Button saveAnswersButton = new Button("Save");
        Button checkAnswersButton = new Button("Check");
        saveCheckBox.getChildren().addAll(checkAnswersButton, saveAnswersButton);

        footballFieldBox.getChildren().addAll(footballFieldCanvas, saveCheckBox);

        VBox playerPosVBox = new VBox();
        playerPosVBox.maxHeight(20);

        playerPosVBox.setAlignment(Pos.TOP_CENTER);
        playerPosVBox.setSpacing(15);

        PlayerMovementListItemView playerPos = new PlayerMovementListItemView();
        Button addPlayerPosButton = new Button("Add new position");

        playerPosVBox.getChildren().addAll(playerPos, addPlayerPosButton);
        getChildren().addAll(footballFieldBox, playerPosVBox);
    }

}
