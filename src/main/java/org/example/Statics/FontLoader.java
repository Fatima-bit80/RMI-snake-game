package org.example.Statics;

import java.awt.*;
import java.io.InputStream;

public class FontLoader {

    public static Font loadPixelFont(float size) {
        try {
            InputStream is = FontLoader.class.getResourceAsStream(
                    "/fonts/PressStart2P-Regular.ttf"
            );

            Font font = Font.createFont(Font.TRUETYPE_FONT, is);
            return font.deriveFont(size);

        } catch (Exception e) {
            e.printStackTrace();

            // fallback font
            return new Font("Monospaced", Font.BOLD, (int) size);
        }
    }
}