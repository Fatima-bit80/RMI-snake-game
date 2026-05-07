package org.example.Statics;

import org.example.Enums.ColorCode;
import org.example.Enums.SnakePartCode;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static org.example.Statics.Config.*;

public class Images {
    public static final Image ground = new ImageIcon("src/main/resources/background/ground.png").getImage();

    public static final Image corner0 = new ImageIcon("src/main/resources/background/grassCorner0.png").getImage();
    public static final Image corner1 = new ImageIcon("src/main/resources/background/grassCorner1.png").getImage();
    public static final Image corner2 = new ImageIcon("src/main/resources/background/grassCorner2.png").getImage();
    public static final Image corner3 = new ImageIcon("src/main/resources/background/grassCorner3.png").getImage();

  public static final Image side0 = new ImageIcon("src/main/resources/background/grassSide0.png").getImage();
    public static final Image side1 = new ImageIcon("src/main/resources/background/grassSide1.png").getImage();
    public static final Image side2 = new ImageIcon("src/main/resources/background/grassSide2.png").getImage();
    public static final Image side3 = new ImageIcon("src/main/resources/background/grassSide3.png").getImage();





    public static final BufferedImage backGroundImage = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_ARGB);

    static {
        Graphics g = backGroundImage.getGraphics();
        for (int i = 0; i < GAME_WIDTH; i += TILE_SIZE) {
            for (int j = 0; j < GAME_HEIGHT; j += TILE_SIZE) {
                if (i == 0) {
                    g.drawImage(side1, i, j, TILE_SIZE, TILE_SIZE, null);
                } else if (j == 0) {
                    g.drawImage(side2, i, j, TILE_SIZE, TILE_SIZE, null);
                } else if (i == (GAME_WIDTH - TILE_SIZE)) {
                    g.drawImage(side3, i, j, TILE_SIZE, TILE_SIZE, null);
                } else if (j == GAME_HEIGHT - TILE_SIZE) {
                    g.drawImage(side0, i, j, TILE_SIZE, TILE_SIZE, null);
                } else {
                    g.drawImage(ground, i, j, TILE_SIZE, TILE_SIZE, null);
                }
            }
        }

        g.drawImage(corner0, 0, 0, TILE_SIZE, TILE_SIZE, null);
        g.drawImage(corner3, 0, GAME_HEIGHT - TILE_SIZE, TILE_SIZE, TILE_SIZE, null);
        g.drawImage(corner1, GAME_WIDTH - TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, null);
        g.drawImage(corner2, GAME_WIDTH - TILE_SIZE, GAME_HEIGHT - TILE_SIZE, TILE_SIZE, TILE_SIZE, null);


    }

    public static final Image[][] snakeImage = new  Image[6][3];

    static {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 3; j++) {
                String colorName = ColorCode.fromCode(i).getDisplayName();
                String snakePart= SnakePartCode.fromCode(j).getSnakePart();
                System.out.println("src/main/resources/snake/"+colorName+"/"+snakePart+".png");
                snakeImage[i][j] = new ImageIcon("src/main/resources/snake/"+colorName+"/"+snakePart+".png").getImage();
            }
        }
    }


}
