package org.example;

import org.example.Enums.ColorCode;
import org.example.Statics.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

import static org.example.Statics.Config.TEXT_OFFSET;
import static org.example.Statics.Config.TILE_SIZE;
import static org.example.Statics.Images.snakeImage;

public class Snake {

    public int id;
    public String name;
    public int playerNumber;
    public ArrayList<Coordinate> coordinates;
    public int headDir;
//    0 up
//    1 right
//    2 down
//    3 left



    public Snake(int playerNumber, ArrayList<Coordinate> coordinates, int headDir, int id, String name) {
        this.playerNumber = playerNumber        ;
        this.coordinates = coordinates;
        this.headDir = headDir;
        this.id = id;
        this.name = name;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(ArrayList<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public int getHeadDir() {
        return headDir;
    }

    public void setHeadDir(int headDir) {
        this.headDir = headDir;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void drawSnake(Graphics2D g2d, JPanel panel) {
        Coordinate head = coordinates.getFirst();
        Coordinate tail = coordinates.getLast();
        Coordinate beforeLast = coordinates.get(coordinates.size() - 2);

        writeName(g2d,panel);
        drawTail(g2d,tail,beforeLast,panel);

        drawHead(g2d,head,panel);




        for (Coordinate s : coordinates) {
            if (!s.equals(tail) && !s.equals(head))
                drawBody(g2d, s,panel);
        }
    }


    public void writeName(Graphics2D g2d, JPanel panel) {
        Coordinate head = coordinates.getFirst();

        int dy = TEXT_OFFSET+TILE_SIZE;
        if(headDir==0){
            dy=-TEXT_OFFSET;
        }


        g2d.setFont(new Font("Arial", Font.BOLD, 14));


        int x = head.px;
        int y = head.py+dy;

// black border
        g2d.setColor(Color.BLACK);
        g2d.drawString(name, x - 1, y );
        g2d.drawString(name, x + 1, y );
        g2d.drawString(name, x , y + 2);
        g2d.drawString(name, x , y - 2);

// main text
        String hex = ColorCode.fromCode(playerNumber).getColorHex();
        Color c = new Color(
                Integer.parseInt(hex.substring(1, 3), 16),
                Integer.parseInt(hex.substring(3, 5), 16),
                Integer.parseInt(hex.substring(5, 7), 16),
                Integer.parseInt(hex.substring(7, 9), 16)
        );        g2d.setColor(c);
        g2d.setColor(c);
        g2d.drawString(name, x, y);







// draw slightly above the head
        g2d.drawString(name, head.px, head.py +dy);
    }

    public void drawBody(Graphics2D g2d, Coordinate c,JPanel panel) {
        g2d.drawImage(snakeImage[playerNumber][1], c.px, c.py, TILE_SIZE, TILE_SIZE, panel);
    }


    public void drawTail(Graphics2D g2d, Coordinate tail,Coordinate beforeLast,JPanel panel) {
        int dir = tail.tailDirection(beforeLast);


        AffineTransform old = g2d.getTransform();

        g2d.rotate(Math.toRadians(dir * 90), tail.px + 0.5 * TILE_SIZE, tail.py + 0.5 * TILE_SIZE);


        g2d.drawImage(snakeImage[playerNumber][2], tail.px,  tail.py, TILE_SIZE, TILE_SIZE, panel);


        g2d.setTransform(old);
    }


    public void drawHead(Graphics2D g2d, Coordinate head,JPanel panel) {
        AffineTransform old = g2d.getTransform();
        g2d.rotate(Math.toRadians(headDir * 90), head.px + 0.5 * TILE_SIZE, head.py + 0.5 * TILE_SIZE);
        g2d.drawImage(snakeImage[playerNumber][0],  head.px,  head.py, TILE_SIZE, TILE_SIZE, panel);


        g2d.setTransform(old);
    }


    @Override
    public String toString() {
        return "Snake{" +
                "coordinates=" + coordinates +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", playerNumber=" + playerNumber +
                ", headDir=" + headDir +
                '}';
    }
}

