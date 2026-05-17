package org.example;

import org.example.GUI.MainFrame;
import org.example.GUI.lobby.LobbyPanel;
import org.example.GUI.start.StartPagePanel;
import org.example.Statics.Config;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.example.Statics.Config.*;

public class SnakeClientImp extends UnicastRemoteObject implements ISnakeClient {

    private final ScheduledExecutorService heartbeatScheduler = Executors.newSingleThreadScheduledExecutor();


    private static ISnakeServer server;
    private final static SnakeClientImp INSTANCE;

    public static SnakeClientImp getInstance() {
        return INSTANCE;
    }


    int state = 0;
    //0 login page
    //1 waiting
    //2 lobby
    //3 game

    int id = -1;//initially no id

    private static MainFrame mainFrame;

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

    //   private static StartPagePanel startPagePanel;

    private boolean gameStarted = false;

    protected SnakeClientImp() throws RemoteException {

    }

    public int getId() {
        return id;
    }

    public static ISnakeServer getServer() {
        return server;
    }

    @Override
    public void startGame(List<Snake> snakes) throws RemoteException {
        if (state == 3) {
            gameStarted = true;
        } else {
            System.out.println("couldn't start game");
        }
    }

    @Override
    public void updateGame(List<Snake> snakes) throws RemoteException {

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
    public void updateSnake(Snake s) throws RemoteException {
        SwingUtilities.invokeLater(() -> {
            mainFrame.getLobbyPanel().updatePlayer(s);
        });
    }

    @Override
    public void addASnake(Snake s) throws RemoteException {
        SwingUtilities.invokeLater(() -> {
            mainFrame.getLobbyPanel().addPlayer(s);
        });
    }

    @Override
    public void playerDisconnected(Snake s) throws RemoteException {
        SwingUtilities.invokeLater(()->{
         //   if(state==LOBBY){
                mainFrame.getLobbyPanel().removePlayer(s);
           // }
        });
    }

    @Override
    public void notifyChange(ArrayList<Snake> snakes) throws RemoteException {

        SwingUtilities.invokeLater(()->{
            if (gameStarted && state == 3) {
                mainFrame.getMainGamePanel().redrawSnakes(snakes);
            } else {//still in the lobby

            }
        });


    }




    @Override
    public void enableStartButton() throws RemoteException {

    }

    @Override
    public void disableStartButton() throws RemoteException {

    }

    public void connectToTheServer(String ip, String name) {
        try {


            server = (ISnakeServer) Naming.lookup("rmi://" + ip + ":" + PORT + "/snakeServer");


            if (id != -1) {
                server.disconnect(id);
            }

            id = server.connect(this, name);
            setId(id);
            if (id > -1) {

            }



//            heartbeatScheduler.scheduleAtFixedRate(() -> {
//                try {
//                    server.heartbeat(id);
//                } catch (Exception ignored) {
//                }
//            }, 1, 2, TimeUnit.SECONDS);

        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException | InterruptedException e) {
            throw new RuntimeException(e);
        }

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

    public void enterLobby() {
        // gui.remove(startPagePanel);

        // gui.add(Lobby)
        //gui.pack();
    }


    public void setDirection(int direction) {
        try {
            server.setDirection(id, direction);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(()->{
                mainFrame.setVisible(true);
        });
    }

    public void setId(int id) {
        this.id = id;
    }
}
