package org.example.GUI.game;

import org.example.GUI.GamePanel;
import org.example.GUI.MessagePanel;
import org.example.ISnakeServer;
import org.example.Snake;
import org.example.SnakeClientImp;
import org.example.Statics.Config;
import org.example.Statics.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static org.example.Statics.Config.*;

public class MainGamePanel extends JPanel implements GamePanel {

    private final SnakeClientImp snakeClientImp;

    private final SnakesPanel gamePanel;
    private final LeaderBoardPanel leaderBoardPanel;
    private final MessagePanel messagePanel;




    public MainGamePanel(SnakeClientImp  snakeClient) {
        snakeClientImp = snakeClient;

        setPreferredSize(new Dimension(TOTAL_WIDTH,GAME_HEIGHT));
        gamePanel= new SnakesPanel(new ArrayList<>());
        leaderBoardPanel = new LeaderBoardPanel(new ArrayList<>());
        messagePanel = new MessagePanel(Config.TOTAL_WIDTH,Config.MESSAGE_HEIGHT,"GAME STARTED");

        initializePanels();
        initializeFrame();

        add(gamePanel, BorderLayout.CENTER);
        add(leaderBoardPanel, BorderLayout.EAST);
        add(messagePanel, BorderLayout.SOUTH);

        addKeyListeners();


    }



    private void addKeyListeners() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {

                    case KeyEvent.VK_W:
                        System.out.println("UP");
                        snakeClientImp.setDirection(UP);
                        break;

                    case KeyEvent.VK_S:
                        System.out.println("DOWN");
                        snakeClientImp.setDirection(DOWN);
                        break;

                    case KeyEvent.VK_A:
                        System.out.println("LEFT");
                        snakeClientImp.setDirection(LEFT);
                        break;

                    case KeyEvent.VK_D:
                        System.out.println("RIGHT");
                        snakeClientImp.setDirection(RIGHT);
                        break;

                    default:
                        System.out.println("UNKNOWN KEY CODE");
                        break;
                }
            }
        });    }

    private void initializePanels() {
        gamePanel.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        leaderBoardPanel.setPreferredSize(new Dimension(LEADERBOARD_WIDTH, GAME_HEIGHT));
    }

    public void initializeFrame(){
        setLayout(new BorderLayout());
        setVisible(true);
    }


    public void redrawSnakes(ArrayList<Snake> snakes) {
        gamePanel.redrawSnakes(snakes);
    }

    public void updateLeaderBoard(ArrayList<String> scores){
        leaderBoardPanel.updateLeaderBoard(scores);
    }

//    public static void main(String[] args) throws InterruptedException {
//
//        JFrame frame = new JFrame();
//
//        MainGamePanel game =  new MainGamePanel(null, -1);
//
//        frame.add(game);
//
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//
//        frame.show();
//
//        ArrayList<String> scores = new ArrayList<>();
//
//        scores.add("Fatima:120");
//        scores.add("Ali:95");
//        scores.add("Sara:80");
//        scores.add("nitv:120");
//
//
//        ArrayList<Snake> snakes = new ArrayList<>();
//        Coordinate x1 = new Coordinate(2,1);
//        Coordinate x2 = new Coordinate(2,2);
//        Coordinate x3 = new Coordinate(3,2);
//        Coordinate x4 = new Coordinate(3,3);
//        ArrayList<Coordinate> coordinateList = new ArrayList<>();
//        coordinateList.add(x1);
//        coordinateList.add(x2);
//        coordinateList.add(x3);
//        coordinateList.add(x4);
//
//        snakes.add(new Snake(1,coordinateList,0,7,"fatima", GAME,false));
//
//
//        Coordinate y1 = new Coordinate(10,8);
//        Coordinate y2 = new Coordinate(10,7);
//        Coordinate y3 = new Coordinate(9,7);
//        Coordinate y4 = new Coordinate(8,7);
//        Coordinate y5 = new Coordinate(8,8);
//
//        Coordinate y6 = new Coordinate(8,9);
//        Coordinate y7 = new Coordinate(7,9);
//        Coordinate y8 = new Coordinate(6,9);
//
//
//        ArrayList<Coordinate> cy = new ArrayList<>();
//        cy.add(y1);
//        cy.add(y2);
//        cy.add(y3);
//        cy.add(y4);
//        cy.add(y5);
//        cy.add(y6);
//        cy.add(y7);
//        cy.add(y8);
//
//        snakes.add(new Snake(0,cy,2,8,"fffffATIMA NADDAHHHH",GAME,false));
//
//
//        System.out.println(snakes.getLast().coordinates);
//        game.updateLeaderBoard(scores);
//
//        game.redrawSnakes(snakes);
//        game.displayMessage("game is set");
//
//
//        int time = 50;
//        Thread.sleep(time);
//
//
//        while (true) {
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//
//            snakes.getLast().grow();
//            Thread.sleep(time);
//
//
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//
//            snakes.getLast().grow();
//            Thread.sleep(time);
//
//
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//
//            snakes.getLast().setDirection(RIGHT);
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//
//            snakes.getLast().grow();
//            Thread.sleep(time);
//
//
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//            snakes.getLast().setDirection(UP);
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//            snakes.getLast().setDirection(LEFT);
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//
//            snakes.getLast().setDirection(DOWN);
//            snakes.getLast().moveForward();
//            Thread.sleep(time);
//        }
//    }
//

    @Override
    public void displayMessage(String message) {
        messagePanel.displayMessage(message);
    }

    public void reset(){
    }

}
