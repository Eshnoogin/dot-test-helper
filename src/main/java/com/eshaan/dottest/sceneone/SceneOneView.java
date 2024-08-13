package com.eshaan.dottest.sceneone;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SceneOneView extends HBox {

    private ArrayList<PlayerMovementListItemView> playerPositionInputs = new ArrayList<>();

    public SceneOneView() {
        super();
        render();
    }

    public void render() {
        getChildren().clear();

        setSpacing(150);

        // make vbox for holding the football field canvas and the save / check boxes
        VBox footballFieldBox = new VBox();
        footballFieldBox.setSpacing(15);
        footballFieldBox.setAlignment(Pos.TOP_CENTER);

        FootballFieldView footballFieldCanvas = new FootballFieldView(1200, 596);
        playerPositionInputs
                .add(new PlayerMovementListItemView(footballFieldCanvas, footballFieldCanvas.playerCoordinates));

        HBox saveCheckBox = new HBox();
        saveCheckBox.setAlignment(Pos.CENTER);
        saveCheckBox.setSpacing(10);

        Button saveAnswersButton = new Button("Save");
        Button checkAnswersButton = new Button("Check");

        saveCheckBox.getChildren().addAll(checkAnswersButton, saveAnswersButton);
        footballFieldBox.getChildren().addAll(footballFieldCanvas, saveCheckBox);

        // make vbox for holding the player location inputs
        VBox playerPosVBox = new VBox();
        playerPosVBox.maxHeight(20);
        playerPosVBox.setAlignment(Pos.TOP_CENTER);
        playerPosVBox.setSpacing(15);

        // render all player location inputs in playerPositionInputs
        for (PlayerMovementListItemView playerPositionInput : playerPositionInputs) {
            playerPosVBox.getChildren().add(playerPositionInput);
        }
        Button addPlayerPosButton = new Button("Add new position");

        // addPlayerPosButton.setOnAction(event -> onAddPlayerPosButtonPress());

        playerPosVBox.getChildren().addAll(addPlayerPosButton);
        getChildren().addAll(footballFieldBox, playerPosVBox);
    }

    // public void onAddPlayerPosButtonPress() {
    //     playerPositionInputs
    //             .add(new PlayerMovementListItemView(footballFieldCanvas, footballFieldCanvas.playerCoordinates));
    //     render();
    // }
}
