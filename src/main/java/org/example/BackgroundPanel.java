package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.example.Statics.Images.backGroundImage;

class BackgroundPanel extends JPanel {

    ArrayList<Snake> snakes;

    public BackgroundPanel(ArrayList<Snake> snakes) {
        this.snakes = snakes;
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);

        g2d.drawImage(backGroundImage, 0, 0, this);

        for (Snake snake : snakes) {
            snake.drawSnake(g2d, this);
        }


    }

}