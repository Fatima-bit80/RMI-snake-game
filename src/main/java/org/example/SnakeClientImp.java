package org.example;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import static org.example.Statics.Config.PORT;

public class SnakeClientImp extends UnicastRemoteObject implements ISnakeClient {

    private final static SnakeClientImp INSTANCE;

    private static ISnakeServer server;
    public static JFrame gui;

    int state = 0;
    //0 login page
    //1 waiting
    //2 lobby
    //3 game

    int id = -1;//initially no id


    static {
        try {
            INSTANCE = new SnakeClientImp();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    private static StartPagePanel startPagePanel;

    private boolean gameStarted = false;

    protected SnakeClientImp() throws RemoteException {
    }

    @Override
    public void startGame(List<Snake> snakes) throws RemoteException {


        gameStarted = true;
    }

    @Override
    public void displayMessage(String message) throws RemoteException {

        System.out.println(message);
    }

    @Override
    public void addToWaitingList() throws RemoteException {

    }

    @Override
    public void addToLobby() throws RemoteException {

    }

    @Override
    public void notifyChange(List<Snake> snakes) throws RemoteException {
        if (gameStarted) {

        } else {//still in the lobby

        }

    }

    @Override
    public void changeLabelText(String tetx) throws RemoteException {

        if (state == 0) {
            startPagePanel.getMessageArea().setText(tetx);
        }
    }

    public void connectToTheServer(String ip, String name) {
        try {


            server = (ISnakeServer) Naming.lookup("rmi://" + ip + ":" + PORT + "/snakeServer");


            if (id != -1) {
                server.disconnect(id);
            }
            id = server.connect(this, name);
            if(id>-1){

            }

        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }


    public static void main(String[] args) {

     gui = new JFrame();
        startPagePanel = new StartPagePanel(INSTANCE);

        gui.setTitle("Snake Online - Connect");

        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                System.out.println("Window is closing");
                INSTANCE.disconnect();

                gui.dispose(); // closes window
                System.exit(0); // optional
            }
        });
        gui.add(startPagePanel);
        gui.pack();
        gui.setVisible(true);
        gui.setLocationRelativeTo(null);

        gui.setResizable(false);

        //todo change title acc to game state
    }


    public void disconnect() {

        if (server != null && id != -1) {

            try {
                server.disconnect(id);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void enterLobby() {
        gui.remove(startPagePanel);

       // gui.add(Lobby)
        gui.pack();
    }
}
