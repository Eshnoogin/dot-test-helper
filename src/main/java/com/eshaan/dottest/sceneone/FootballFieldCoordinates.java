package com.eshaan.dottest.sceneone;

import javafx.geometry.Point2D;

public class FootballFieldCoordinates {
    public static enum FIELD_HORIZONTAL_LOC {
        OUTSIDE("outside"),
        INSIDE("inside");

        private final String displayValue;

        FIELD_HORIZONTAL_LOC(String displayValue) {
            this.displayValue = displayValue;
        }
    
        public String getDisplayValue() {
            return displayValue;
        }

        public static FIELD_HORIZONTAL_LOC fromDisplayValue(String displayValue) {
            for (FIELD_HORIZONTAL_LOC loc : FIELD_HORIZONTAL_LOC.values()) {
                if (loc.getDisplayValue().equals(displayValue)) {
                    return loc;
                }
            }
            throw new IllegalArgumentException("Unknown display value: " + displayValue);
        }
    }

    public static enum FIELD_VERTICAL_LOC {
        BEHIND("behind"),
        FRONT("in front of");

        private final String displayValue;

        FIELD_VERTICAL_LOC(String displayValue) {
            this.displayValue = displayValue;
        }
    
        public String getDisplayValue() {
            return displayValue;
        }

        public static FIELD_VERTICAL_LOC fromDisplayValue(String displayValue) {
            for (FIELD_VERTICAL_LOC loc : FIELD_VERTICAL_LOC.values()) {
                if (loc.getDisplayValue().equals(displayValue)) {
                    return loc;
                }
            }
            throw new IllegalArgumentException("Unknown display value: " + displayValue);
        }
    }

    public static enum FIELD_SIDE {
        ONE("one"),
        TWO("two");

        private final String displayValue;

        FIELD_SIDE(String displayValue) {
            this.displayValue = displayValue;
        }
    
        public String getDisplayValue() {
            return displayValue;
        }

        public static FIELD_SIDE fromDisplayValue(String displayValue) {
            for (FIELD_SIDE loc : FIELD_SIDE.values()) {
                if (loc.getDisplayValue().equals(displayValue)) {
                    return loc;
                }
            }
            throw new IllegalArgumentException("Unknown display value: " + displayValue);
        }
    }

    public static enum FIELD_VERTICAL_REFERENCE {
        FRONT("front"),
        BACK("back"),
        FRONT_HASH("front hash"),
        BACK_HASH("back hash");

        private final String displayValue;

        FIELD_VERTICAL_REFERENCE(String displayValue) {
            this.displayValue = displayValue;
        }
    
        public String getDisplayValue() {
            return displayValue;
        }

        public static FIELD_VERTICAL_REFERENCE fromDisplayValue(String displayValue) {
            for (FIELD_VERTICAL_REFERENCE loc : FIELD_VERTICAL_REFERENCE.values()) {
                if (loc.getDisplayValue().equals(displayValue)) {
                    return loc;
                }
            }
            throw new IllegalArgumentException("Unknown display value: " + displayValue);
        }
    }

    public FIELD_SIDE side;
    public float stepsFromYrdLine;
    public FIELD_HORIZONTAL_LOC horizontalLoc;
    public int yrdLn;
    public float stepsFromVertRef;
    public FIELD_VERTICAL_LOC verticalLoc;
    public FIELD_VERTICAL_REFERENCE vertRef;

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
