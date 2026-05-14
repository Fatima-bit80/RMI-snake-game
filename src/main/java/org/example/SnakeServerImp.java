package org.example;

import org.example.Statics.Coordinate;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.example.Statics.Config.*;

public class SnakeServerImp extends UnicastRemoteObject implements ISnakeServer {

    //todo save last id given to file
    private static int id = 0;

    private static final List<Integer> availableColors = new ArrayList<>();

    static {
        availableColors.add(0);
        availableColors.add(1);
        availableColors.add(2);
        availableColors.add(3);
        availableColors.add(4);
        availableColors.add(5);
    }

    private static final ArrayList<ArrayList<Coordinate>> initialPositions = new ArrayList<>();

    static {
        for (int i = 0; i < 6; i++) {

            ArrayList<Coordinate> snakeCoordinate = new ArrayList<>();

            snakeCoordinate.add(new Coordinate((i + 2) * 4, 6));
            snakeCoordinate.add(new Coordinate((i + 2) * 4, 5));
            snakeCoordinate.add(new Coordinate((i + 2) * 4, 4));

            initialPositions.add(snakeCoordinate);
        }
    }//initial positions of snakes

    private int nbPlayers = 0;
    //players in the lobby or in game
    private final Map<Integer, ISnakeClient> players = new HashMap();

    //players unable to enter
    private final Map<Integer, ISnakeClient> waitingList = new HashMap();


    private final Map<Integer, Snake> snakes = new HashMap();


    //to see what players are ready
    private final Map<Integer, Boolean> gameStarted = new HashMap();

    //private Map<Integer,List<Integer>> initialPositions = new HashMap();


    protected SnakeServerImp() throws RemoteException {
    }


    @Override
    public synchronized int connect(ISnakeClient client, String name) throws RemoteException {

        System.out.println(availableColors);
        int playerNumber = -1;
        //todo save


        id++;
        Snake s = createSnake(id, name, playerNumber, START);
        snakes.put(id, s);

        if (nbPlayers <= 5) {
            addToLobby(client, s);
        } else {
            addToWaitingList(client,s);
        }

        return id;
    }

    private synchronized void addToWaitingList(ISnakeClient client, Snake s) throws RemoteException {
        waitingList.put(id, client);
        int snakeId=s.getId();
        s.setState(WAITING);
        String message = "there are already enough players, you can't connect\ntry again another time";
        client.displayMessage(message);


        System.out.println("a player with id "+snakeId+" to connect but the lobby is full transforming to waiting list");
        client.changeLabelText(message);
        client.addToWaitingList();
        client.updateWaitingList(snakes,new ArrayList<>(waitingList.keySet()));
        sendMessage("snake with id "+ snakeId+"was added to the waiting list",WAITING);

    }

    private Snake createSnake(int id, String name, int playerNumber, int state) {

        Snake s = new Snake(playerNumber, null, 2, id, name, state);

        if (playerNumber != -1)
            s.setCoordinates(initialPositions.get(playerNumber));

        return s;
    }

    @Override
    public synchronized void disconnect(int id) throws RemoteException {

        //todo if heart beat not sent -> disconnect
       Snake s = snakes.get(id);
        snakes.remove(id);


        if (players.containsKey(id)) {
            int playerNumber = s.getPlayerNumber();
            availableColors.add(playerNumber);


            ArrayList<Coordinate> snakeCoordinate = new ArrayList<>();

            snakeCoordinate.add(new Coordinate((playerNumber + 2) * 4, 6));
            snakeCoordinate.add(new Coordinate((playerNumber + 2) * 4, 5));
            snakeCoordinate.add(new Coordinate((playerNumber + 2) * 4, 4));

            initialPositions.add(snakeCoordinate);

            gameStarted.remove(id);
            players.remove(id);
            nbPlayers--;

        } else if(waitingList.containsKey(id)){
            waitingList.remove(id);
            notifyAllPlayersInWaitingList();
        }



        System.out.println("player with id " + id + " disconnected successfully");

        if (nbPlayers < 6 && !waitingList.isEmpty()) {
            int addedPlayer = (int) (waitingList.keySet().toArray())[0];

            ISnakeClient client = waitingList.get(addedPlayer);
            players.put(addedPlayer, client);
            waitingList.remove(addedPlayer);


            Snake ss = snakes.get(addedPlayer);
            int color = availableColors.get(0);
            ss.setPlayerNumber(color);
            ss.setCoordinates(initialPositions.get(addedPlayer));

            String message = "you are now playing " + "your id is " + id + "  snake : " + ss;

            client.displayMessage(message);
            client.changeLabelText(message);
            gameStarted.put(id, false);
            client.addToLobby(snakes,new ArrayList<>(players.keySet()));


            nbPlayers++;
        }
    }

    @Override
    public void setDirection(int id, int direction) throws RemoteException {

        snakes.get(id).setDirection(direction);

    }

    @Override
    public void heartbeat(int id) throws RemoteException {

    }

    @Override
    public void requestStartGame(int id) throws RemoteException {


        gameStarted.put(id, true);


        for (int playerId : gameStarted.keySet()) {
            //if atleast 1 player isn't ready -> don't start the game
            if (gameStarted.get(playerId) == false) {
                return;
            }
        }

        //if all players are ready
        startGame();

    }

    public void startGame() {


        System.out.println("Starting game");

        //todo notify all the players that the game started

    }



    public synchronized void addToLobby(ISnakeClient client, Snake s) throws RemoteException {
        int snakeId=s.getId();
        int playerNumber = availableColors.get(0);
        availableColors.remove((Object) playerNumber);
        s.setPlayerNumber(playerNumber);
        s.setCoordinates(initialPositions.get(playerNumber));

        nbPlayers++;

        String message = "connected successfully " + "your id is " + snakeId;
        System.out.println("player with id " + snakeId + " connected successfully");
        client.displayMessage(message);
        client.displayMessage(s.toString());
        client.changeLabelText(message);

        gameStarted.put(snakeId, false);

        players.put(snakeId, client);
        s.setState(LOBBY);

        client.addToLobby(snakes,new ArrayList<>(players.keySet()));
        //todo change panel to view lobby panel


        notifyAllPlayersInLobby();
        sendMessage("player with id "+snakeId+"was added to the lobby",LOBBY);

    }

    public void notifyAllPlayersInLobby() {
        for (int i : players.keySet()) {
            ISnakeClient c = players.get(i);
            try {
                c.updateLobby(snakes, new ArrayList<>(players.keySet()));
                c.displayMessage("the players ids: " + players.keySet());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void notifyAllPlayersInWaitingList() {
        for (int i : waitingList.keySet()) {
            ISnakeClient c = waitingList.get(i);
            try {
                c.updateWaitingList(snakes,new ArrayList<>(waitingList.keySet()));
                c.displayMessage("the waiting list ids: " + players.keySet());
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }    }

    public void sendMessage(String message,int state) throws RemoteException {
        if(state == LOBBY || state == GAME)
            {
                for (ISnakeClient c : players.values()) {
                    c.displayMessage(message);
                }
            }
            else if(state == WAITING) {
                for (ISnakeClient c : waitingList.values()) {
                    c.displayMessage(message);
                }

            }


    }


    public static void main(String[] args) {

        try {
            Registry r = LocateRegistry.getRegistry(PORT);

            ISnakeServer server = new SnakeServerImp();

            r.rebind("snakeServer", server);

            System.out.println("Snake server is running...");

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
