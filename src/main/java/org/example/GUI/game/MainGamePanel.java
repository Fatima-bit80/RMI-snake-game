package org.example.GUI.game;

import org.example.GUI.GamePanel;
import org.example.GUI.MessagePanel;
import org.example.Model.Snake;
import org.example.Client.SnakeClientImp;
import org.example.Statics.Config;
import org.example.Statics.Coordinate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

import static org.example.Statics.Config.*;

public class MainGamePanel extends JPanel implements GamePanel {

    private final SnakeClientImp snakeClientImp;

    private final SnakesPanel gamePanel;
   // private final LeaderBoardPanel leaderBoardPanel;
    private final MessagePanel messagePanel;




    public MainGamePanel(SnakeClientImp  snakeClient) {
        snakeClientImp = snakeClient;

        setPreferredSize(new Dimension(GAME_WIDTH,TOTAL_HEIGHT));
        gamePanel= new SnakesPanel(new ArrayList<>());
        //todo leaderboard
        //leaderBoardPanel = new LeaderBoardPanel(new ArrayList<>());
        messagePanel = new MessagePanel(GAME_WIDTH,Config.MESSAGE_HEIGHT,"GAME STARTED");

        initializePanels();
        initializeFrame();

        add(gamePanel, BorderLayout.CENTER);
        //add(leaderBoardPanel, BorderLayout.EAST);
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
                        setDirection(UP);
                        break;

                    case KeyEvent.VK_S:
                        System.out.println("DOWN");
                        setDirection(DOWN);
                        break;

                    case KeyEvent.VK_A:
                        System.out.println("LEFT");
                        setDirection(LEFT);
                        break;

                    case KeyEvent.VK_D:
                        System.out.println("RIGHT");
                        setDirection(RIGHT);
                        break;

                    default:
                        System.out.println("UNKNOWN KEY CODE");
                        break;
                }
            }
        });    }

    private void initializePanels() {
        gamePanel.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
        //leaderBoardPanel.setPreferredSize(new Dimension(LEADERBOARD_WIDTH, GAME_HEIGHT));
    }

    public void initializeFrame(){
        setLayout(new BorderLayout());
        setVisible(true);
    }


    public void redrawSnakesAndFruits(Map<Integer, Snake> snakes, Map<Integer,Coordinate> fruits) {
        gamePanel.redrawSnakesAndFruits(snakes,fruits);
    }


//    public void updateLeaderBoard(ArrayList<String> scores){
//        leaderBoardPanel.updateLeaderBoard(scores);
//    }




    public void setDirection(int direction) {
        try {
           snakeClientImp.getServer().setDirection(snakeClientImp.getId(), direction);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void displayMessage(String message) {
        messagePanel.displayMessage(message);
    }

    public void reset(){
    }

}
