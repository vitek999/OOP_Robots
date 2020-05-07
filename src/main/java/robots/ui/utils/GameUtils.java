package robots.ui.utils;

import java.awt.*;

public class GameUtils { // !!! Странное название + странное назначение

    private final static double MEDIUM_CHARGE_COEFFICIENT = 0.7;
    private final static double LOW_CHARGE_COEFFICIENT = 0.3;
    private final static int EMPTY_CHARGE_VALUE = 0;

    public static Color chargeTextColor(int charge, int maxCharge) {
        Color chargeTextColor = Color.BLACK;

        double mediumCharge = MEDIUM_CHARGE_COEFFICIENT * maxCharge;
        double lowCharge = LOW_CHARGE_COEFFICIENT * maxCharge;

        if (charge >= mediumCharge) chargeTextColor = Color.GREEN;
        if (charge >= lowCharge && charge < mediumCharge) chargeTextColor = Color.YELLOW;
        if (charge >= EMPTY_CHARGE_VALUE && charge < lowCharge) chargeTextColor = Color.RED;

        return chargeTextColor;
    }

    public static String colorName(Color color) {
        if (Color.RED.equals(color)) {
            return "Красный";
        } else if(Color.BLUE.equals(color)) {
            return "Синий";
        }
        return "";
    }
}
