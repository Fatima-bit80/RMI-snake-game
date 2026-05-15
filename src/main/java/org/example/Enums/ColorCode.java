package org.example.Enums;

import java.awt.*;

public enum ColorCode {

    ROSE(0, "rose", "#E47A9CFF"),
    TOMATO(1, "tomato", "#FFA698FF"),
    YELLOW(2, "yellow", "#FFE28AFF"),
    GREEN(3, "green", "#41C676FF"),
    BLUE(4, "blue", "#B4E1FFFF"),
    PURPLE(5, "purple", "#AB87FFFF");

    private final int code;
    private final String displayName;
    private final String colorHex;

    ColorCode(int code, String displayName, String colorHex) {
        this.code = code;
        this.displayName = displayName;
        this.colorHex = colorHex;
    }

    public int getCode() {
        return code;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getColorHex() {
        return colorHex;
    }

    public static ColorCode fromCode(int code) {
        for (ColorCode c : values()) {
            if (c.code == code) return c;
        }
        throw new IllegalArgumentException("Invalid code");
    }

    public static Color getColor(String colorHex) {
        return new Color(
                Integer.parseInt(colorHex.substring(1, 3), 16),
                Integer.parseInt(colorHex.substring(3, 5), 16),
                Integer.parseInt(colorHex.substring(5, 7), 16),
                Integer.parseInt(colorHex.substring(7, 9), 16));
    }

}