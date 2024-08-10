package com.eshaan.dottest.sceneone;

import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class FootballFieldView extends Canvas {
    public static final int STEPS_PER_5_YRD = 8; // 8 to 5 step size lines displayed by default
    public static final float STEP_RESOLUTION = 0.25f; // step size is divided into 4
    public static final float FIELD_WIDTH_YDS = 100f;
    public static final float FIELD_HEIGHT_YDS = 52.5f;
    public static final int HASH_DISTANCE_FNT_YDS = 20;
    public static final int YRD_LN_NUMBER_Y_OFFSET = 10; // yard line text px offset(to the left)
    public static final int PLAYER_RAD_PX = 5;

    public static final int VERTICAL_LINES_PER_FIELD = (int) (FIELD_WIDTH_YDS / 5 * STEPS_PER_5_YRD);
    public static final int HORIZONTAL_LINES_PER_FIELD = (int) (FIELD_HEIGHT_YDS / 5 * STEPS_PER_5_YRD);
    public static final float HASH_DISTANCE_BETWEEN_YDS = 2 * (FIELD_HEIGHT_YDS / 2 - HASH_DISTANCE_FNT_YDS);

    public int widthPx;
    public int heightPx;

    public int pixelsPerVerticalStepLine;
    public int pixelsPerHorizontalStepLine;

    private FootballFieldCoordinates playerCoordinates;
    private boolean doPlayerRender = true;

    public FootballFieldView(double maxWidth, double maxHeight) {
        super();

        pixelsPerVerticalStepLine = (int) (maxWidth / VERTICAL_LINES_PER_FIELD);
        pixelsPerHorizontalStepLine = (int) (maxHeight / HORIZONTAL_LINES_PER_FIELD);

        // redo width and height for errors introduced when rounding
        widthPx = pixelsPerVerticalStepLine * VERTICAL_LINES_PER_FIELD;
        heightPx = pixelsPerHorizontalStepLine * HORIZONTAL_LINES_PER_FIELD;

        setWidth(widthPx);
        setHeight(heightPx);

        GraphicsContext gc = getGraphicsContext2D();
        redrawCanvas(gc);
    }

    public void redrawCanvas(GraphicsContext gc) {
        // draw step lines
        drawGrid(gc, pixelsPerVerticalStepLine, pixelsPerHorizontalStepLine, VERTICAL_LINES_PER_FIELD,
                HORIZONTAL_LINES_PER_FIELD, 1, Color.GRAY);

        // draw half yard lines
        drawGrid(gc, pixelsPerVerticalStepLine * (STEPS_PER_5_YRD / 2),
                pixelsPerHorizontalStepLine * (STEPS_PER_5_YRD / 2), VERTICAL_LINES_PER_FIELD,
                HORIZONTAL_LINES_PER_FIELD, 1, Color.PINK);

        // draw yard lines
        drawGrid(gc, pixelsPerVerticalStepLine * STEPS_PER_5_YRD, 0,
                VERTICAL_LINES_PER_FIELD,
                0, 2, Color.BLACK);

        // draw hash lines
        int fntHashPosPx = (HASH_DISTANCE_FNT_YDS / 5 * STEPS_PER_5_YRD); // represented as # of steps
        int bckHashPosPx = (int) (HASH_DISTANCE_BETWEEN_YDS / 5 * STEPS_PER_5_YRD) + fntHashPosPx; // represented as #
                                                                                                   // of steps
        fntHashPosPx *= pixelsPerHorizontalStepLine; // converted to pxls
        bckHashPosPx *= pixelsPerHorizontalStepLine; // converted to pxls

        int pixelsPerYardLine = pixelsPerVerticalStepLine * STEPS_PER_5_YRD;

        for (int i = pixelsPerYardLine; i <= (VERTICAL_LINES_PER_FIELD / STEPS_PER_5_YRD - 1)
                * pixelsPerYardLine; i += pixelsPerYardLine) { // start at second yard line(5) and increment by one yard
                                                               // line each time
            gc.setStroke(Color.BLACK);
            gc.setLineWidth(1);
            gc.strokeLine(i - pixelsPerVerticalStepLine, fntHashPosPx, i + pixelsPerVerticalStepLine, fntHashPosPx);
            gc.strokeLine(i - pixelsPerVerticalStepLine, bckHashPosPx, i + pixelsPerVerticalStepLine, bckHashPosPx);

        }

        // draw yard line numbers
        gc.setFont(new Font("Arial", 20));
        gc.setFill(Color.BLACK);

        for (int i = 1; i < VERTICAL_LINES_PER_FIELD / STEPS_PER_5_YRD; i++) {
            int xPosition = i * pixelsPerVerticalStepLine * STEPS_PER_5_YRD;
            int yardNumber = i * 5;
            yardNumber = (yardNumber > 50) ? 50 - ((i - 10) * 5) : yardNumber;

            gc.fillText(String.valueOf(yardNumber), xPosition - YRD_LN_NUMBER_Y_OFFSET, fntHashPosPx - 20);
            gc.fillText(String.valueOf(yardNumber), xPosition - YRD_LN_NUMBER_Y_OFFSET, heightPx - fntHashPosPx + 40);
        }

        // draw player
        FootballFieldCoordinates coords = new FootballFieldCoordinates(FootballFieldCoordinates.FIELD_SIDE.ONE,
                4, FootballFieldCoordinates.FIELD_HORIZONTAL_LOC.INSIDE, 35,
                2, FootballFieldCoordinates.FIELD_VERTICAL_LOC.BEHIND,
                FootballFieldCoordinates.FIELD_VERTICAL_REFERENCE.FRONT_HASH);

        Point2D point = coords.convertToPx(this);
        System.out.println("PRINTING PX POSES");
        System.out.println(point.getX());
        System.out.println(point.getY());

        if (doPlayerRender) {
            gc.setFill(Color.RED);
            gc.strokeRect(point.getX(), point.getY(), pixelsPerVerticalStepLine,
                    pixelsPerHorizontalStepLine);
            System.out.println(pixelsPerVerticalStepLine);
            System.out.println(pixelsPerHorizontalStepLine);
        }

        // draw outline
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(5);
        gc.strokeRect(0, 0, widthPx, heightPx);
    }

    private void drawGrid(GraphicsContext gc, int pixelSpacingX, int pixelSpacingY, int amountX, int amountY,
            double lineWidth, Color color) {

        for (int i = 0; i <= amountX; i++) {
            gc.setLineWidth(lineWidth);
            gc.setStroke(color);
            gc.strokeLine(i * pixelSpacingX, 0, i * pixelSpacingX, heightPx);
        }

        for (int i = 0; i <= amountY; i++) {
            gc.setLineWidth(lineWidth);
            gc.setStroke(color);
            gc.strokeLine(0, i * pixelSpacingY, widthPx, i * pixelSpacingY);
        }
    }

    // protected void setPlayerPosXPx(int playerPosXPx) {
    // this.playerPosXPx = playerPosXPx;
    // this.doPlayerRender = true;
    // }

    // protected void setPlayerPosYPx(int playerPosYPx) {
    // this.playerPosYPx = playerPosYPx;
    // this.doPlayerRender = true;
    // }
}
