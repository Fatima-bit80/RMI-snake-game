package org.example;

import org.example.GUI.lobby.LobbyPanel;
import org.example.GUI.MainFrame;
import org.example.GUI.waiitng.WaitingPanel;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.example.Statics.Config.PORT;

public class SnakeClientImp extends UnicastRemoteObject implements ISnakeClient {

    private final ScheduledExecutorService heartbeatScheduler = Executors.newSingleThreadScheduledExecutor();


    private static ISnakeServer server;
    private final static SnakeClientImp INSTANCE;

   public static SnakeClientImp getInstance(){
       return INSTANCE;
   }


    int state = 0;
    //0 login page
    //1 waiting
    //2 lobby
    //3 game

    int id = -1;//initially no id

    private static final MainFrame mainFrame;

    static {
        try {
            INSTANCE = new SnakeClientImp();
            mainFrame = new MainFrame(INSTANCE);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

 //   private static StartPagePanel startPagePanel;

    private boolean gameStarted = false;

    protected SnakeClientImp() throws RemoteException {

    }

    @Override
    public void startGame(List<Snake> snakes) throws RemoteException {
        if(state==3) {
            gameStarted = true;
        }else{
            System.out.println("couldn't start game");
        }
    }

    @Override
    public void updateGame(List<Snake> snakes) throws RemoteException {

    }

    @Override
    public void displayMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    @Override
    public void addToWaitingList() throws RemoteException {

        if(state==0) {
            state = 1;
            mainFrame.showPage(WaitingPanel.class.getSimpleName());
        }
    }

    @Override
    public void addToLobby(Map<Integer, Snake> players,ArrayList<Integer> ids) throws RemoteException {

        if(state ==0){
            state=2;
            mainFrame.showPage(LobbyPanel.class.getSimpleName());
            updateLobby(players,ids);
        }

    }

    @Override
    public void updateLobby(Map<Integer, Snake> players, ArrayList<Integer> ids) throws RemoteException {

    }

    @Override
    public void notifyChange(ArrayList<Snake> snakes) throws RemoteException {
        if (gameStarted && state==3) {
            mainFrame.getMainGamePanel().redrawSnakes(snakes);
        } else {//still in the lobby

        }

    }

    @Override
    public void changeLabelText(String tetx) throws RemoteException {

        if (state == 0) {
            //startPagePanel.getMessageArea().setText(tetx);
        }
    }

    @Override
    public void updateWaitingList(Map<Integer, Snake> snakes, ArrayList<Integer> integers) throws RemoteException {

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
            if(id>-1){

            }


            heartbeatScheduler.scheduleAtFixedRate(() -> {
                try {
                    server.heartbeat(id);
                } catch (Exception ignored) {}
            }, 1, 2, TimeUnit.SECONDS);

        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

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
       // gui.remove(startPagePanel);

       // gui.add(Lobby)
        //gui.pack();
    }


    public void setDirection(int direction){
        try {
            server.setDirection(id,direction);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {

        mainFrame.setVisible(true);
    }


}
