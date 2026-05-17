package org.example.GUI.game;

import org.example.Model.Snake;
import org.example.Statics.Coordinate;
import org.example.Statics.Images;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.example.Statics.Config.*;
import static org.example.Statics.Images.backGroundImage;

public class SnakesPanel extends JPanel implements Serializable {

    ArrayList<Snake> snakes= new ArrayList<>();
    Map<Integer,Coordinate> fruits = new HashMap<>();

    public SnakesPanel(ArrayList<Snake> snakes) {
        this.snakes = snakes;
        setPreferredSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);

        System.out.println("Painting snakes: " + snakes.size());

        g2d.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);


        for(int fruitType:fruits.keySet()){
            Coordinate coordinate = fruits.get(fruitType);
            g2d.drawImage(Images.fruits[fruitType],coordinate.getPx(),coordinate.getPy(),TILE_SIZE,TILE_SIZE,this);
        }

        for (Snake snake : snakes) {
            snake.drawSnake(g2d);
        }



    }

    public void redrawSnakesAndFruits(Map<Integer, Snake> remoteSnakes,Map<Integer, Coordinate> fruits) {


      snakes = new ArrayList<>();
      for (Snake s:remoteSnakes.values()) {

          snakes.add(new Snake(s,this));
      }

      this.fruits = fruits;
        repaint();
        revalidate();
    }






}