package org.example;

import org.example.Enums.ColorCode;
import org.example.Statics.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.Serializable;
import java.util.ArrayList;

import static org.example.Statics.Config.TEXT_OFFSET;
import static org.example.Statics.Config.TILE_SIZE;
import static org.example.Statics.Images.snakeImage;

public class Snake implements Serializable {

    public int id;
    public String name;
    public int playerNumber;
    public ArrayList<Coordinate> coordinates;
    public int headDir;

    private transient JPanel panelToDrawOn;
//    0 up
//    1 right
//    2 down
//    3 left


    public int state = 0;
    //0 -> start
    //1 -> lobby
    //2 -> waiting
    //3 -> game

    public boolean ready = false;


    public Snake(int playerNumber, ArrayList<Coordinate> coordinates, int headDir, int id, String name, int state, boolean ready) {
        this.playerNumber = playerNumber;
        this.coordinates = coordinates;
        this.headDir = headDir;
        this.id = id;
        this.name = name;
        this.state = state;
        this.ready = ready;
    }

    public void setPanelToDrawOn(JPanel panelToDrawOn) {
        //  this.panelToDrawOn = panelToDrawOn;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void drawSnake(Graphics2D g2d) {
        if (panelToDrawOn != null) {

            Coordinate head = coordinates.getFirst();
            Coordinate tail = coordinates.getLast();
            Coordinate beforeLast = coordinates.get(coordinates.size() - 2);

            writeName(g2d);
            drawTail(g2d, tail, beforeLast);

            drawHead(g2d, head);


            for (Coordinate s : coordinates) {
                if (!s.equals(tail) && !s.equals(head))
                    drawBody(g2d, s);
            }
        }
    }


    public void writeName(Graphics2D g2d) {
        if (panelToDrawOn != null) {

            Coordinate head = coordinates.getFirst();

            int dy = TEXT_OFFSET + TILE_SIZE;
            if (headDir == 0) {
                dy = -TEXT_OFFSET;
            }


            g2d.setFont(new Font("Arial", Font.BOLD, 14));


            int x = head.getPx();
            int y = head.getPy() + dy;

// black border
            g2d.setColor(Color.BLACK);
            g2d.drawString(name, x - 1, y);
            g2d.drawString(name, x + 1, y);
            g2d.drawString(name, x, y + 2);
            g2d.drawString(name, x, y - 2);

// main text
            String hex = ColorCode.fromCode(playerNumber).getColorHex();
            Color c = new Color(
                    Integer.parseInt(hex.substring(1, 3), 16),
                    Integer.parseInt(hex.substring(3, 5), 16),
                    Integer.parseInt(hex.substring(5, 7), 16),
                    Integer.parseInt(hex.substring(7, 9), 16)
            );
            g2d.setColor(c);
            g2d.setColor(c);
            g2d.drawString(name, x, y);


// draw slightly above the head
            g2d.drawString(name, head.getPx(), head.getPy() + dy);
        }
    }

    public void drawBody(Graphics2D g2d, Coordinate c) {
        if (panelToDrawOn != null) {
            g2d.drawImage(snakeImage[playerNumber][1], c.getPx(), c.getPy(), TILE_SIZE, TILE_SIZE, panelToDrawOn);
        }
    }


    public void drawTail(Graphics2D g2d, Coordinate tail, Coordinate beforeLast) {

        if (panelToDrawOn != null) {

            int dir = tail.tailDirection(beforeLast);


            AffineTransform old = g2d.getTransform();

            g2d.rotate(Math.toRadians(dir * 90), tail.getPx() + 0.5 * TILE_SIZE, tail.getPy() + 0.5 * TILE_SIZE);


            g2d.drawImage(snakeImage[playerNumber][2], tail.getPx(), tail.getPy(), TILE_SIZE, TILE_SIZE, panelToDrawOn);


            g2d.setTransform(old);
        }
    }


    public void drawHead(Graphics2D g2d, Coordinate head) {
        if (panelToDrawOn != null) {
            AffineTransform old = g2d.getTransform();
            g2d.rotate(Math.toRadians(headDir * 90), head.getPx() + 0.5 * TILE_SIZE, head.getPy() + 0.5 * TILE_SIZE);
            g2d.drawImage(snakeImage[playerNumber][0], head.getPx(), head.getPy(), TILE_SIZE, TILE_SIZE, panelToDrawOn);


            g2d.setTransform(old);
        }
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

    public void setDirection(int direction) {

        if (Math.abs(direction - headDir) % 2 == 1) {
            headDir = direction;
        }

    }

    public void moveForward() {
        coordinates.removeLast();
        grow();

    }

    public void grow() {

        Coordinate oldFirst = coordinates.getFirst();
        Coordinate newFirst = new Coordinate(oldFirst.getX(), oldFirst.getY());
        if (headDir == 0) {
            newFirst.setY(oldFirst.getY() - 1);
        } else if (headDir == 1) {
            newFirst.setX(oldFirst.getX() + 1);
        } else if (headDir == 2) {
            newFirst.setY(oldFirst.getY() + 1);
        } else if (headDir == 3) {
            newFirst.setX(newFirst.getX() - 1);
        }
        coordinates.addFirst(newFirst);

        panelToDrawOn.repaint();


    }
}

