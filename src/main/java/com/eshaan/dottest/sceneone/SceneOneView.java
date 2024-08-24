package com.eshaan.dottest.sceneone;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SceneOneView extends HBox {

    private ArrayList<PlayerMovementListItemView> playerPositionInputs = new ArrayList<>();

    VBox footballFieldBox;
    HBox saveCheckBox;
    FootballFieldView footballFieldCanvas;
    Button saveAnswersButton;
    Button checkAnswersButton;
    VBox playerPosVBox;
    Button addPlayerPosButton;

    public SceneOneView() {
        super();

        // make vbox for holding the football field canvas and the save / check boxes
        footballFieldBox = new VBox();
        footballFieldBox.setSpacing(15);
        footballFieldBox.setAlignment(Pos.TOP_CENTER);

        footballFieldCanvas = new FootballFieldView(1200, 596);
        playerPositionInputs
                .add(new PlayerMovementListItemView(footballFieldCanvas,
                        footballFieldCanvas.playerCoordinateList.get(0)));

        saveCheckBox = new HBox();
        saveCheckBox.setAlignment(Pos.CENTER);
        saveCheckBox.setSpacing(10);

        saveAnswersButton = new Button("Save");
        checkAnswersButton = new Button("Check");

        saveCheckBox.getChildren().addAll(checkAnswersButton, saveAnswersButton);
        footballFieldBox.getChildren().addAll(footballFieldCanvas, saveCheckBox);

        // make vbox for holding the player location inputs
        playerPosVBox = new VBox();
        playerPosVBox.maxHeight(20);
        playerPosVBox.setAlignment(Pos.TOP_CENTER);
        playerPosVBox.setSpacing(15);

        // render all player location inputs in playerPositionInputs
        for (PlayerMovementListItemView playerPositionInput : playerPositionInputs) {
            playerPosVBox.getChildren().add(playerPositionInput);
        }
        addPlayerPosButton = new Button("Add new position");

        addPlayerPosButton.setOnAction(event -> onAddPlayerPosButtonPress());

        playerPosVBox.getChildren().addAll(addPlayerPosButton);
        getChildren().addAll(footballFieldBox, playerPosVBox);

        setSpacing(150);
    }

    public void render() {
        // rerender football field
        footballFieldCanvas.redrawCanvas();

        // rerender vbox that contains player coord inputs
        ObservableList<Node> playerPosVBoxChildren = playerPosVBox.getChildren();
        int playerPosVBoxChildrenSize = playerPosVBoxChildren.size();
        int offset = 0;

        for (int i = 0; i < playerPosVBoxChildrenSize; i++) {
            if (playerPosVBoxChildren.get(i - offset).getUserData() != null
                    && playerPosVBoxChildren.get(i - offset).getUserData().equals("isPlayerMvmtLstItmView")) {
                playerPosVBoxChildren.remove(i - offset);
                offset++;
            }
        }

        for (PlayerMovementListItemView playerPositionInput : playerPositionInputs) {
            playerPosVBox.getChildren().add(playerPositionInput);
        }

        playerPosVBox.getChildren().add(playerPosVBox.getChildren().removeFirst());
    }

    public void onAddPlayerPosButtonPress() {
        FootballFieldCoordinates coordinates = new FootballFieldCoordinates(FootballFieldCoordinates.FIELD_SIDE.ONE, 0,
                null, 0, 0, null, null);
        footballFieldCanvas.playerCoordinateList.add(coordinates);
        playerPositionInputs
                .add(new PlayerMovementListItemView(footballFieldCanvas,
                        coordinates));
        render();
    }
}
