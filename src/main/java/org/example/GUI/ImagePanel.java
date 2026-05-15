package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {

    private Image image;
    private int height,width;

    public ImagePanel(Image image,int width,int height) {

        this.image =image;
        this.height = height;
        this.width = width;
        setPreferredSize(new Dimension(width,height));
        setOpaque(false);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(
                image,
                0,
                0,
                width,
                height,
                this
        );
    }

    public void setImage(Image image) {
        this.image = image;
        repaint();
    }
}
