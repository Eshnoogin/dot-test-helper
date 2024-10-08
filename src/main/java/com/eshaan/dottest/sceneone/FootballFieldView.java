package com.eshaan.dottest.sceneone;

import java.util.ArrayList;

import com.eshaan.dottest.ColorPallete;

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
    public static final int PLAYER_RAD_PX = 3;
    public static final int ARROW_SPACE_PX = 5; // space between the actual point and where the line starts; space
                                                // horizontally so math is easier
    public static final int ARROW_LEN_PX = 10; // length of the pointer of the arrow
    public static final float ARROW_ANGLE_RAD = 2.52f;

    public static final int VERTICAL_LINES_PER_FIELD = (int) (FIELD_WIDTH_YDS / 5 * STEPS_PER_5_YRD);
    public static final int HORIZONTAL_LINES_PER_FIELD = (int) (FIELD_HEIGHT_YDS / 5 * STEPS_PER_5_YRD);
    public static final float HASH_DISTANCE_BETWEEN_YDS = 2 * (FIELD_HEIGHT_YDS / 2 - HASH_DISTANCE_FNT_YDS);

    public int widthPx;
    public int heightPx;

    public int pixelsPerVerticalStepLine;
    public int pixelsPerHorizontalStepLine;

    public ArrayList<FootballFieldCoordinates> playerCoordinateList;

    GraphicsContext gc;

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
        this.gc = gc;

        playerCoordinateList = new ArrayList<>();

        playerCoordinateList
                .add(new FootballFieldCoordinates(FootballFieldCoordinates.FIELD_SIDE.ONE, 0, null, 0, 0, null, null));

        redrawCanvas();
    }

    public void redrawCanvas() {
        gc.setFill(ColorPallete.GREEN);
        gc.fillRect(0, 0, widthPx, heightPx);
        // draw step lines
        drawGrid(gc, pixelsPerVerticalStepLine, pixelsPerHorizontalStepLine, VERTICAL_LINES_PER_FIELD,
                HORIZONTAL_LINES_PER_FIELD, 1, ColorPallete.GRAY);

        // draw half yard lines
        drawGrid(gc, pixelsPerVerticalStepLine * (STEPS_PER_5_YRD / 2),
                pixelsPerHorizontalStepLine * (STEPS_PER_5_YRD / 2), VERTICAL_LINES_PER_FIELD,
                HORIZONTAL_LINES_PER_FIELD, 1, ColorPallete.RED);

        // draw yard lines
        drawGrid(gc, pixelsPerVerticalStepLine * STEPS_PER_5_YRD, 0,
                VERTICAL_LINES_PER_FIELD,
                0, 2, ColorPallete.BLACK);

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
        Point2D lastPoint = null;
        for (FootballFieldCoordinates playerCoordinates : playerCoordinateList) {
            if ((playerCoordinates.side != null) && (playerCoordinates.horizontalLoc != null)
                    && (playerCoordinates.verticalLoc != null) && (playerCoordinates.vertRef != null)) {

                Point2D point = playerCoordinates.convertToPx(this);

                gc.setFill(Color.RED);
                gc.fillOval(point.getX() - PLAYER_RAD_PX, point.getY() - PLAYER_RAD_PX, 2 * PLAYER_RAD_PX,
                        2 * PLAYER_RAD_PX);

                // draw arrow to point to next drawn point
                if (lastPoint != null) {
                    // line math: https://www.desmos.com/calculator/ycueqa0gbh
                    int x1 = (int) point.getX();
                    int y1 = (int) point.getY();

                    int x0 = (int) lastPoint.getX();
                    int y0 = (int) lastPoint.getY();

                    double m = (x0 != x1) ? (double) (y0 - y1) / (x0 - x1) : Double.POSITIVE_INFINITY;
                    double b = -m * x1 + y1;
                    int s = x1 - x0;
                    double d1 = Math.sqrt(Math.pow(x0 - x1, 2) + Math.pow(y0 - y1, 2));

                    double theta1 = Math.atan2(y1 - y0, x1 - x0);

                    double t = ARROW_SPACE_PX / d1;
                    double pt = 1 - t;

                    double startx = s * t + x0;
                    double starty = m * startx + b;

                    double p0x = s * pt + x0;
                    double p0y = m * p0x + b;

                    if (x0 == x1) {
                        p0x = x1;
                        p0y = y1 - ARROW_SPACE_PX;
                        startx = x1;
                        starty = y0 + ARROW_SPACE_PX;
                    }

                    double arrowx0 = ARROW_LEN_PX * Math.cos(ARROW_ANGLE_RAD + theta1) + p0x;
                    double arrowy0 = ARROW_LEN_PX * Math.sin(ARROW_ANGLE_RAD + theta1) + p0y;
                    double arrowx1 = ARROW_LEN_PX * Math.cos((2 * Math.PI - ARROW_ANGLE_RAD) + theta1) + p0x;
                    double arrowy1 = ARROW_LEN_PX * Math.sin((2 * Math.PI - ARROW_ANGLE_RAD) + theta1) + p0y;

                    gc.strokeLine(startx, starty, p0x, p0y);
                    gc.strokeLine(p0x, p0y, arrowx0, arrowy0);
                    gc.strokeLine(p0x, p0y, arrowx1, arrowy1);
                }
                lastPoint = point;
            } else {
            }
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
}
