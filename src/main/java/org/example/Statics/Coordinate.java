package org.example.Statics;

import java.io.Serializable;

import static org.example.Statics.Config.TILE_SIZE;

public class Coordinate implements Serializable {

    private int x, y, px, py;
    //x y -> tiles number
    //px py -> pixel number

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
        px =x*TILE_SIZE;
        py =y*TILE_SIZE;
    }

    public int getPx() {
        return px;
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        this.px=x*TILE_SIZE;
    }

    public int getPy() {
        return py;
    }

    public void setPy(int py) {
        this.py = py;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        this.py=y*TILE_SIZE;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return px == that.px && py == that.py;
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + px +   ", y=" + py +
                ", xTile=" + x +
                ", yTile=" + y +

                '}';
    }

    public int tailDirection(Coordinate coordinate) {//called on tail, takes the last body part
        if(x ==coordinate.x && y <coordinate.y) return 0;//up
        if(x ==coordinate.x && y >coordinate.y) return 2;//down
        if(x >coordinate.x && y ==coordinate.y) return 1;//right
        if(x <coordinate.x && y ==coordinate.y) return 3;//left

        return -1;
    }
}
