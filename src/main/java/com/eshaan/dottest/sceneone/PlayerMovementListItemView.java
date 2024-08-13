package com.eshaan.dottest.sceneone;

import java.io.UnsupportedEncodingException;

import com.eshaan.dottest.ColorPallete;
import com.eshaan.dottest.sceneone.FootballFieldCoordinates.FIELD_HORIZONTAL_LOC;
import com.eshaan.dottest.sceneone.FootballFieldCoordinates.FIELD_VERTICAL_LOC;
import com.eshaan.dottest.sceneone.FootballFieldCoordinates.FIELD_VERTICAL_REFERENCE;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class PlayerMovementListItemView extends VBox {
    public static int TEXT_FIELD_WIDTH_PX = 35;
    public static int ITEM_SPACING_PX = 10;

    FootballFieldView footballField;
    FootballFieldCoordinates linkedCoordinates;

    public PlayerMovementListItemView(FootballFieldView footballField, FootballFieldCoordinates linkedCoordinates) {
        super();

        this.footballField = footballField;
        this.linkedCoordinates = linkedCoordinates;
        setSpacing(20);
        BackgroundFill bgf = new BackgroundFill(ColorPallete.GRAY, new CornerRadii(10), getInsets());
        Background bg = new Background(bgf);
        setBackground(bg);
        setMaxHeight(20); // vbox will take up entire vertical space unless you change it to a small
                          // number
        setPadding(new Insets(10));

        // set up gui layout for getting player's x value
        HBox playerHorizontalInfoBox = new HBox();
        playerHorizontalInfoBox.setSpacing(ITEM_SPACING_PX);
        playerHorizontalInfoBox.setAlignment(Pos.CENTER_LEFT);

        TextField playerStepsFromYdLn = new TextField();
        playerStepsFromYdLn.setMaxWidth(TEXT_FIELD_WIDTH_PX);

        Label stepLabelOne = new Label("steps");

        ChoiceBox<String> playerHorizontalPolarity = new ChoiceBox<>();
        for (FIELD_HORIZONTAL_LOC hloc : FIELD_HORIZONTAL_LOC.values()) {
            playerHorizontalPolarity.getItems().addAll(hloc.getDisplayValue());
        }

        TextField playerYdLn = new TextField();
        playerYdLn.setMaxWidth(TEXT_FIELD_WIDTH_PX);

        Label ydLnLabel = new Label("yard line.");

        // set up gui layout for getting player's y value
        HBox playerVerticalInfoBox = new HBox();
        playerVerticalInfoBox.setSpacing(ITEM_SPACING_PX);
        playerVerticalInfoBox.setAlignment(Pos.CENTER_LEFT);

        TextField playerStepsFromVertRefLn = new TextField();
        playerStepsFromVertRefLn.setMaxWidth(TEXT_FIELD_WIDTH_PX);

        Label stepLabelTwo = new Label("steps");

        ChoiceBox<String> playerVerticalPolarity = new ChoiceBox<>();
        for (FIELD_VERTICAL_LOC vloc : FIELD_VERTICAL_LOC.values()) {
            playerVerticalPolarity.getItems().addAll(vloc.getDisplayValue());
        }

        ChoiceBox<String> playerVertRfLn = new ChoiceBox<>();
        for (FIELD_VERTICAL_REFERENCE vref : FIELD_VERTICAL_REFERENCE.values()) {
            playerVertRfLn.getItems().addAll(vref.getDisplayValue());
        }

        // add event listeners
        playerStepsFromYdLn.textProperty()
                .addListener((observable, oldValue, newValue) -> onPlayerStepsFromYdLnChange(newValue));
        playerStepsFromVertRefLn.textProperty()
                .addListener((observable, oldValue, newValue) -> onPlayerStepsFromVertRefLnChange(newValue));
        playerYdLn.textProperty()
                .addListener((observable, oldValue, newValue) -> onPlayerYdLnChange(newValue));

        playerVerticalPolarity.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue,
                        String newValue) -> {
                    linkedCoordinates.verticalLoc = FootballFieldCoordinates.FIELD_VERTICAL_LOC
                            .fromDisplayValue(newValue);
                    footballField.redrawCanvas();
                });

        playerVertRfLn.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue,
                        String newValue) -> {
                    linkedCoordinates.vertRef = FootballFieldCoordinates.FIELD_VERTICAL_REFERENCE
                            .fromDisplayValue(newValue);
                    footballField.redrawCanvas();
                });

        playerHorizontalPolarity.getSelectionModel()
                .selectedItemProperty()
                .addListener((ObservableValue<? extends String> observable, String oldValue,
                        String newValue) -> {
                    linkedCoordinates.horizontalLoc = FootballFieldCoordinates.FIELD_HORIZONTAL_LOC
                            .fromDisplayValue(newValue);
                    footballField.redrawCanvas();
                });

        // build gui layout
        playerHorizontalInfoBox.getChildren().add(playerStepsFromYdLn);
        playerHorizontalInfoBox.getChildren().add(stepLabelOne);
        playerHorizontalInfoBox.getChildren().add(playerHorizontalPolarity);
        playerHorizontalInfoBox.getChildren().add(playerYdLn);
        playerHorizontalInfoBox.getChildren().add(ydLnLabel);
        getChildren().add(playerHorizontalInfoBox);

        playerVerticalInfoBox.getChildren().add(playerStepsFromVertRefLn);
        playerVerticalInfoBox.getChildren().add(stepLabelTwo);
        playerVerticalInfoBox.getChildren().add(playerVerticalPolarity);
        playerVerticalInfoBox.getChildren().add(playerVertRfLn);

        getChildren().add(playerVerticalInfoBox);
    }

    public void onPlayerYdLnChange(String value) {
        if (!value.equals("")) {
            try {
                linkedCoordinates.yrdLn = Integer.parseInt(value);
                footballField.redrawCanvas();
            } catch (NumberFormatException e) {

            }

        } else {
        }
    }

    public void onPlayerStepsFromVertRefLnChange(String value) {
        if (!value.equals("")) {
            try {
                linkedCoordinates.stepsFromVertRef = Float.parseFloat(value);
                footballField.redrawCanvas();
            } catch (NumberFormatException e) {

            }
        }
    }

    public void onPlayerStepsFromYdLnChange(String value) {
        if (!value.equals("")) {
            try {
                linkedCoordinates.stepsFromYrdLine = Float.parseFloat(value);
                footballField.redrawCanvas();
            } catch (NumberFormatException e) {

            }

        } else {
        }
    }

}
