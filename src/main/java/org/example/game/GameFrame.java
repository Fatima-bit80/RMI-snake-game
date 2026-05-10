package org.example.game;

import org.example.Snake;
import org.example.Statics.Coordinate;
import org.example.Statics.Images;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import static org.example.Statics.Config.*;

public class GameFrame extends JFrame {


    private final GamePanel gamePanel;
    private final LeaderBoardPanel leaderBoardPanel;


    public GameFrame() {
        gamePanel= new GamePanel(new ArrayList<>());
        leaderBoardPanel = new LeaderBoardPanel(new ArrayList<>());

        initializePanels();
        initializeFrame();

        add(gamePanel, BorderLayout.CENTER);
        add(leaderBoardPanel, BorderLayout.EAST);

        pack();
        setLocationRelativeTo(null);
        setResizable(false);

    }

    private void initializePanels() {
        gamePanel.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        leaderBoardPanel.setPreferredSize(new Dimension(LEADERBOARD_WIDTH, GAME_HEIGHT));
    }

    public void initializeFrame(){
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Images.snakeImage[5][1]);
        setVisible(true);
    }


    public void redrawSnakes(ArrayList<Snake> snakes) {
        gamePanel.redrawSnakes(snakes);
    }

    public void updateLeaderBoard(ArrayList<String> scores){
        leaderBoardPanel.updateLeaderBoard(scores);
    }

    public static void main(String[] args) {
      GameFrame game =  new GameFrame();


        ArrayList<String> scores = new ArrayList<>();

        scores.add("Fatima:120");
        scores.add("Ali:95");
        scores.add("Sara:80");
        scores.add("nitv:120");


        ArrayList<Snake> snakes = new ArrayList<>();
        Coordinate x1 = new Coordinate(2,1);
        Coordinate x2 = new Coordinate(2,2);
        Coordinate x3 = new Coordinate(3,2);
        Coordinate x4 = new Coordinate(3,3);
        ArrayList<Coordinate> coordinateList = new ArrayList<>();
        coordinateList.add(x1);
        coordinateList.add(x2);
        coordinateList.add(x3);
        coordinateList.add(x4);

        snakes.add(new Snake(1,coordinateList,0,7,"fatima"));


        Coordinate y1 = new Coordinate(10,8);
        Coordinate y2 = new Coordinate(10,7);
        Coordinate y3 = new Coordinate(9,7);
        Coordinate y4 = new Coordinate(8,7);
        Coordinate y5 = new Coordinate(8,8);

        Coordinate y6 = new Coordinate(8,9);
        Coordinate y7 = new Coordinate(7,9);
        Coordinate y8 = new Coordinate(6,9);


        ArrayList<Coordinate> cy = new ArrayList<>();
        cy.add(y1);
        cy.add(y2);
        cy.add(y3);
        cy.add(y4);
        cy.add(y5);
        cy.add(y6);
        cy.add(y7);
        cy.add(y8);

        snakes.add(new Snake(0,cy,2,8,"fffffATIMA NADDAHHHH"));

















        game.updateLeaderBoard(scores);
        game.redrawSnakes(snakes);
    }



}
