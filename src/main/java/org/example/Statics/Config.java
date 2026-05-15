package org.example.Statics;

import org.example.FontLoader;

import java.awt.*;

public class Config {


    //common multiples of 16 32 48
    //height: 576,672,768
    //width: 864,960,1056,1152,1248,1344,1440,1536


    public static final int TOTAL_HEIGHT = 710;
    public static final int TOTAL_WIDTH = 1500;
    public static final int GAME_WIDTH = 1320;
    public static final int LEADERBOARD_WIDTH =  TOTAL_WIDTH -GAME_WIDTH;
    public static final int GAME_HEIGHT = 680;
    public static final int LEADERBOARD_HEIGHT = GAME_HEIGHT;
    public static final int MESSAGE_HEIGHT = TOTAL_HEIGHT-GAME_HEIGHT;
    public static final int TILE_SIZE = 40;
    public static final int START_PAGE_WIDTH = TOTAL_WIDTH/3+100;
    public static final int START_PAGE_HEIGHT =  GAME_HEIGHT*3/4+50;

    public static final int LOBBY_WIDTH = TOTAL_WIDTH/2;
    public static final int LOBBY_HEIGHT = GAME_HEIGHT;


    public static final int TEXT_OFFSET = TILE_SIZE/3;
    public static final int ROWS = GAME_HEIGHT/TILE_SIZE;
    public static final int COLUMNS = GAME_WIDTH/TILE_SIZE;

    public static final int PORT = 1099;

    public static final int UP =0;
    public static final int DOWN =2;
    public static final int LEFT =3;
    public static final int RIGHT =1;



    public static final int START = 0;
    public static final int LOBBY = 1;
    public static final int WAITING = 2;
    public static final int GAME = 3;

    public static final int MAX_NB_PLAYERS =4;

    private static final String lightGreenHex = "#A1BF8AFF";
    public static final Color LIGHT_GREEN_COLOR =  new Color(
            Integer.parseInt(lightGreenHex.substring(1, 3), 16),
            Integer.parseInt(lightGreenHex.substring(3, 5), 16),
            Integer.parseInt(lightGreenHex.substring(5, 7), 16),
            Integer.parseInt(lightGreenHex.substring(7, 9), 16)
    );



    private static final String darkGreenHex = "#253B14FF";
    public static final Color DARK_GREEN_COLOR =  new Color(
            Integer.parseInt(darkGreenHex.substring(1, 3), 16),
            Integer.parseInt(darkGreenHex.substring(3, 5), 16),
            Integer.parseInt(darkGreenHex.substring(5, 7), 16),
            Integer.parseInt(darkGreenHex.substring(7, 9), 16)
    );

    public static final String darkerGreenHex ="#192B0CFF";
    public static final Color DARKER_GREEN_COLOR = new Color(
            Integer.parseInt(darkerGreenHex.substring(1, 3), 16),
            Integer.parseInt(darkerGreenHex.substring(3, 5), 16),
            Integer.parseInt(darkerGreenHex.substring(5, 7), 16),
            Integer.parseInt(darkerGreenHex.substring(7, 9), 16)
    );

    public static final String readyGreenHex = "#429701FF";
    public static final Color READY_GREEN_HEX = new Color(
            Integer.parseInt(readyGreenHex.substring(1, 3), 16),
            Integer.parseInt(readyGreenHex.substring(3, 5), 16),
            Integer.parseInt(readyGreenHex.substring(5, 7), 16),
            Integer.parseInt(readyGreenHex.substring(7, 9), 16)
    );



    public static final String notReadyOrangeHex = "#FB6107FF";
    public static final Color NOT_READY_ORANGE_HEX = new Color(
            Integer.parseInt(notReadyOrangeHex.substring(1, 3), 16),
            Integer.parseInt(notReadyOrangeHex.substring(3, 5), 16),
            Integer.parseInt(notReadyOrangeHex.substring(5, 7), 16),
            Integer.parseInt(notReadyOrangeHex.substring(7, 9), 16)
    );

   public static final Font pixelFont = FontLoader.loadPixelFont(16f);


}