package org.example;

import org.example.Statics.Coordinate;
import org.example.Statics.Images;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.Statics.Config.*;

public class gameGUI extends JFrame {


    public gameGUI() throws IOException {
    }

    public static void main(String[] args) throws IOException {

        JFrame frame = new gameGUI();
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(Images.snakeImage[5][1]);


        ArrayList<Snake> snakes = new ArrayList<>();


        JPanel backgroundPanel = new BackgroundPanel(snakes);


        frame.add(backgroundPanel, BorderLayout.CENTER);
        backgroundPanel.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));

        JPanel leaderBoardPanel = new JPanel();
        leaderBoardPanel.setPreferredSize(new Dimension(LEADERBOARD_WIDTH, GAME_HEIGHT));
        leaderBoardPanel.setBackground(Color.BLACK);
        frame.add(leaderBoardPanel, BorderLayout.EAST);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);

    }


}
