package org.example.Statics;

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

    public static final int TEXT_OFFSET = TILE_SIZE/3;
    public static final int ROWS = GAME_HEIGHT/TILE_SIZE;
    public static final int COLUMNS = GAME_WIDTH/TILE_SIZE;

    public static final int PORT = 1099;

    public static final int UP =0;
    public static final int DOWN =2;
    public static final int LEFT =3;
    public static final int RIGHT =1;

}