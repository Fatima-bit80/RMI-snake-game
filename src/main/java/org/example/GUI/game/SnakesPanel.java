package org.example.GUI.game;

import org.example.Snake;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

import static org.example.Statics.Images.backGroundImage;

public class SnakesPanel extends JPanel implements Serializable {

    ArrayList<Snake> snakes;

    public SnakesPanel(ArrayList<Snake> snakes) {
        this.snakes = snakes;
        setPanel();
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g2d);

        g2d.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(), this);

        for (Snake snake : snakes) {
            snake.drawSnake(g2d);
        }


    }

    public void redrawSnakes(ArrayList<Snake> snakes) {
        this.snakes = snakes;
        setPanel();
        repaint();
    }

    public void setPanel() {
        for (Snake snake : snakes) {
            snake.setPanelToDrawOn(this);
        }
    }


}