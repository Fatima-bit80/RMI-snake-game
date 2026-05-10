package org.example.game;

import org.example.Snake;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.example.Statics.Images.backGroundImage;

class GamePanel extends JPanel {

    ArrayList<Snake> snakes;

    public GamePanel(ArrayList<Snake> snakes) {
        this.snakes = snakes;
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);

        g2d.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);

        for (Snake snake : snakes) {
            snake.drawSnake(g2d, this);
        }


    }

    public void redrawSnakes(ArrayList<Snake> snakes) {
        this.snakes = snakes;
        repaint();
    }

}