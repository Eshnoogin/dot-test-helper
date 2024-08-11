package com.eshaan.dottest.sceneone;

import com.eshaan.dottest.ColorPallete;

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

    public PlayerMovementListItemView() {
        super();
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
        playerHorizontalPolarity.getItems().addAll("inside", "outside");

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
        playerVerticalPolarity.getItems().addAll("in front of", "behind");

        ChoiceBox<String> playerVertRfLn = new ChoiceBox<>();
        playerVertRfLn.getItems().addAll("front", "front hash", "back hash", "back");

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

}
