package org.example.Client;

import org.example.GUI.MainFrame;
import org.example.GUI.game.MainGamePanel;
import org.example.GUI.lobby.LobbyPanel;
import org.example.GUI.start.StartPagePanel;
import org.example.Server.ISnakeServer;
import org.example.Model.Snake;
import org.example.Statics.Coordinate;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Map;

import static org.example.Statics.Config.*;

public class SnakeClientImp extends UnicastRemoteObject implements ISnakeClient {

   // private final ScheduledExecutorService heartbeatScheduler = Executors.newSingleThreadScheduledExecutor();


    private static ISnakeServer server;
    private final static SnakeClientImp INSTANCE;

    private static MainFrame mainFrame;


    private static int state = 0; //0 start, 1 lobby, 2 game
    private static int id = -1;//initially no id
    private static boolean gameStarted = false;




    static {
        try {
            INSTANCE = new SnakeClientImp();
            SwingUtilities.invokeLater(() -> {
                try {
                    mainFrame = new MainFrame(INSTANCE);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    protected SnakeClientImp() throws RemoteException {

    }




    @Override
    public void displayMessage(String message) throws RemoteException {



        // mainFrame.getCurrentPanel().displayMessage(message);

        SwingUtilities.invokeLater(() -> {

            System.out.println(message);

            switch (state) {
                case START:
                    mainFrame.getStartPagePanel().displayMessage(message);
                    break;
                case LOBBY:
                    mainFrame.getLobbyPanel().displayMessage(message);
                    break;
                case GAME:
                    mainFrame.getMainGamePanel().displayMessage(message);
                    break;

            }

        });
    }


    //lobby
    @Override
    public void addToLobby(Map<Integer, Snake> players, ArrayList<Integer> ids, ArrayList<String> lobbyMessages) throws RemoteException, InterruptedException {

        if (state == 0) {

            SwingUtilities.invokeLater(() -> {
                state = LOBBY;

                mainFrame.getLobbyPanel().setSnakes(players);
                mainFrame.getLobbyPanel().setMessages(lobbyMessages);
                mainFrame.getLobbyPanel().updateTopPanel();
                mainFrame.showPage(LobbyPanel.class.getSimpleName());
            });

        }

    }
    @Override
    public void addASnakeToLobby(Snake s) throws RemoteException {
        SwingUtilities.invokeLater(() -> {
            mainFrame.getLobbyPanel().addPlayer(s);
        });
    }
    @Override
    public void updateASnakeInLobby(Snake s) throws RemoteException {
        SwingUtilities.invokeLater(() -> {
            mainFrame.getLobbyPanel().updatePlayer(s);
        });
    }
    @Override
    public void ASnakeDisconnected(Snake s) throws RemoteException {
        SwingUtilities.invokeLater(()->{
            //   if(state==LOBBY){
            mainFrame.getLobbyPanel().removePlayer(s);
            // }
        });
    }



    //game
    @Override
    public void startGame(Map<Integer, Snake> snakes,Map<Integer,Coordinate> fruits) throws RemoteException {


        SwingUtilities.invokeLater(() -> {
            state =GAME;
            gameStarted = true;
            mainFrame.showPage(MainGamePanel.class.getSimpleName());



            try {
                updateGame(snakes,fruits);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }



        });

    }
    @Override
    public void updateGame(Map<Integer, Snake> snakes, Map<Integer, Coordinate> fruits) throws RemoteException {
        SwingUtilities.invokeLater(() -> {

            mainFrame.getMainGamePanel().redrawSnakesAndFruits(snakes,fruits);
            mainFrame.getMainGamePanel().requestFocusInWindow();

        });
    }






    public void disconnect() {

        if (server != null && id != -1) {

            try {
                server.disconnect(id);

                if(state==LOBBY||state==GAME){

                    reset();
                }

            } catch (RemoteException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void reset() throws RemoteException {

        server = null;
        SwingUtilities.invokeLater(()->{
            mainFrame.showPage(StartPagePanel.class.getSimpleName());
            state = 0;
            id=-1;
            gameStarted = false;
            try {
                mainFrame.resetUI();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @Override
    public void die()throws RemoteException {


       SwingUtilities.invokeLater(()->{

           try {
               displayMessage("YOU LOST!!!!!!!!!");
               Thread.sleep(1000);


               reset();
               disconnect();
           } catch (RemoteException e) {
               throw new RuntimeException(e);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
           mainFrame.showPage(StartPagePanel.class.getSimpleName());
       }) ;

    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
                mainFrame.setVisible(true);
        });
    }



    //getters and setters
    public static SnakeClientImp getInstance() {
        return INSTANCE;
    }
    public static void setServer(ISnakeServer server) {
        SnakeClientImp.server = server;
    }
    public static ISnakeServer getServer() {
        return server;
    }
    public synchronized void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }

}
