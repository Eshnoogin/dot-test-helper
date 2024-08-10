package com.eshaan.dottest.sceneone;

import javafx.geometry.Point2D;

public class FootballFieldCoordinates {
    public static enum FIELD_HORIZONTAL_LOC {
        OUTSIDE,
        INSIDE
    }

    public static enum FIELD_VERTICAL_LOC {
        BEHIND,
        FRONT
    }

    public static enum FIELD_SIDE {
        ONE,
        TWO
    }

    public static enum FIELD_VERTICAL_REFERENCE {
        FRONT,
        BACK,
        FRONT_HASH,
        BACK_HASH
    }

    FIELD_SIDE side;
    float stepsFromYrdLine;
    FIELD_HORIZONTAL_LOC horizontalLoc;
    int yrdLn;
    float stepsFromVertRef;
    FIELD_VERTICAL_LOC verticalLoc;
    FIELD_VERTICAL_REFERENCE vertRef;

    public FootballFieldCoordinates(FIELD_SIDE side,
            float stepsFromYrdLine, FIELD_HORIZONTAL_LOC horizontalLoc, int yrdLn,
            float stepsFromVertRef, FIELD_VERTICAL_LOC verticalLoc, FIELD_VERTICAL_REFERENCE vertRef) {

        this.side = side;
        this.stepsFromYrdLine = stepsFromYrdLine;
        this.horizontalLoc = horizontalLoc;
        this.yrdLn = yrdLn;
        this.stepsFromVertRef = stepsFromVertRef;
        this.verticalLoc = verticalLoc;
        this.vertRef = vertRef;
    }

    public Point2D convertToPx(FootballFieldView footballField) {
        // find distance from side one in yrds
        float distanceFromClosestYdLn = 5 * stepsFromYrdLine / FootballFieldView.STEPS_PER_5_YRD;
        float distanceFromRespectiveSide = yrdLn
                + ((horizontalLoc == FIELD_HORIZONTAL_LOC.INSIDE) ? 1 : -1) * distanceFromClosestYdLn;
        float distanceFromSideOne;
        if (side == FIELD_SIDE.ONE) {
            distanceFromSideOne = distanceFromRespectiveSide;
        } else {
            distanceFromSideOne = FootballFieldView.FIELD_WIDTH_YDS - distanceFromRespectiveSide;
        }

        // find distance from front in yrds
        float distanceFromClosestVertRefLn = ((verticalLoc == FIELD_VERTICAL_LOC.BEHIND) ? 1 : -1) * 5
                * stepsFromVertRef / FootballFieldView.STEPS_PER_5_YRD;
        float distanceFromFront;
        if (vertRef == FIELD_VERTICAL_REFERENCE.FRONT) {
            distanceFromFront = distanceFromClosestVertRefLn;
        } else if (vertRef == FIELD_VERTICAL_REFERENCE.FRONT_HASH) {
            distanceFromFront = FootballFieldView.HASH_DISTANCE_FNT_YDS + distanceFromClosestVertRefLn;
        } else if (vertRef == FIELD_VERTICAL_REFERENCE.BACK_HASH) {
            distanceFromFront = FootballFieldView.FIELD_HEIGHT_YDS - FootballFieldView.HASH_DISTANCE_FNT_YDS
                    + distanceFromClosestVertRefLn;
        } else {
            distanceFromFront = FootballFieldView.FIELD_HEIGHT_YDS + distanceFromClosestVertRefLn;
        }

        System.out.println(distanceFromSideOne);
        System.out.println(distanceFromFront);

        // convert mesurements to pixels
        int playerPosX = (int) (FootballFieldView.STEPS_PER_5_YRD * distanceFromSideOne / 5
                * footballField.pixelsPerVerticalStepLine);
        int playerPosY = (int) (FootballFieldView.STEPS_PER_5_YRD * distanceFromFront / 5
                * footballField.pixelsPerHorizontalStepLine);

        return new Point2D(playerPosX, playerPosY);
    }

}
