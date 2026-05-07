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

import static org.example.Statics.Config.PORT;

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

    private static final ArrayList<Coordinate[]> initialPositions = new ArrayList<>();

    static {
        for (int i = 0; i < 6; i++) {

            initialPositions.add(new Coordinate[]{
                    new Coordinate((i + 2) * 4, 6),
                    new Coordinate((i + 2) * 4, 5),
                    new Coordinate((i + 2) * 4, 4)
            });
        }
    }//initial position of snakes

    private int nbPlayers = 0;
    //players in the lobby
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

        int color = -1;
        //todo save

        id++;
        if (nbPlayers < 6) {
            color = availableColors.get(0);
            availableColors.remove(color);


            nbPlayers++;

            client.displayMessage("connected successfully " + "your id is " + id);

            gameStarted.put(id, false);
            players.put(id, client);


            client.addToLobby();
        } else {
            waitingList.put(id, client);
            client.displayMessage("there are already enough players, you can't connect\ntry again another time");

            client.addToWaitingList();
        }


        return id;
    }

    @Override
    public synchronized void disconnect(int id) throws RemoteException {

        if (players.containsKey(id)) {
            players.remove(id);
            nbPlayers--;

        } else waitingList.remove(id);


        if (nbPlayers < 6) {
            int addedPlayer = (int) (waitingList.keySet().toArray())[0];

            ISnakeClient client = waitingList.get(addedPlayer);

            players.put(addedPlayer, client);
            waitingList.remove(addedPlayer);
            client.displayMessage("you are now playing " + "your id is " + id);
            gameStarted.put(id, false);
            client.addToLobby();
            nbPlayers++;
        }
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

    public static void main(String[] args) {

        try {
            Registry r = LocateRegistry.getRegistry(PORT);
            ISnakeServer server = new SnakeServerImp();
            r.rebind("snakeServer", server);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
