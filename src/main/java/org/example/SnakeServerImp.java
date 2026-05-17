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

import static java.lang.Thread.sleep;
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
    private final Map<Integer, ISnakeClient> players = new HashMap();//int is id

    //players unable to enter

    private final Map<Integer, Snake> snakes = new HashMap();//int is id


    private final ArrayList<String> lobbyMessages = new ArrayList<>();

    //to see what players are ready
    private final Map<Integer, Boolean> gameStarted = new HashMap();//int is id


    protected SnakeServerImp() throws RemoteException {
    }


    @Override
    public synchronized int connect(ISnakeClient client, String name) throws RemoteException, InterruptedException {

        System.out.println(client);
        System.out.println(availableColors);
        int playerNumber = -1;
        //todo save to file


        id++;
        Snake s = createSnake(id, name, playerNumber, START);

        if (nbPlayers <= 5) {
            String message = "connected successfully " + "your id is " + id;
            System.out.println("player with id " + id + " connected successfully");
            client.displayMessage(message+"the one you want");

            addToLobby(client, s);
        } else {
            client.displayMessage("you cant join the lobby is full");
        }

        return id;
    }



    private Snake createSnake(int id, String name, int playerNumber, int state) {

        Snake s = new Snake(playerNumber, null, 2, id, name, state,false);

        if (playerNumber != -1)
            s.setCoordinates(initialPositions.get(playerNumber));

        snakes.put(id, s);
        return s;
    }

    @Override
    public synchronized void disconnect(int id) throws RemoteException, InterruptedException {

        //todo if heart beat not sent -> disconnect

        //todo when player disconnect notify all players
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

            notifyDisconnect(s);


        }



        System.out.println("player with id " + id + " disconnected successfully");


    }

    @Override
    public void setDirection(int id, int direction) throws RemoteException {

        snakes.get(id).setDirection(direction);

    }






    //lobby
    @Override
    public void displayLobbyChat(int id, String message) throws RemoteException {
String formattedMessage=id+":"+snakes.get(id).getName()+":"+message;
        lobbyMessages.add(formattedMessage);
        for(ISnakeClient snakeClient : players.values()){
            snakeClient.displayMessage(formattedMessage);
        }
    }

    public synchronized void addToLobby(ISnakeClient client, Snake s) throws RemoteException, InterruptedException {



        int snakeId=s.getId();

        int playerNumber = availableColors.get(0);
        availableColors.remove((Object) playerNumber);

        s.setPlayerNumber(playerNumber);
        s.setCoordinates(initialPositions.get(playerNumber));
        s.setState(LOBBY);

        nbPlayers++;




        gameStarted.put(snakeId, false);


        players.put(snakeId, client);


        client.addToLobby(snakes,new ArrayList<>(players.keySet()),lobbyMessages);


        notifyAllPlayersInLobby(s);








    }

    public void notifyAllPlayersInLobby(Snake s) {
        String lobbyMessage = "0:SERVER:"+s.getName()+" joined the lobby...";
        lobbyMessages.add(lobbyMessage);

        for (int i : players.keySet()) {
            System.out.println("id to notify "+i);
            ISnakeClient c = players.get(i);
            try {

                if(s.getId()!=i)
                c.addASnake(s);
                c.displayMessage(lobbyMessage);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

        }
    }

    @Override
    public void requestStartGame(int id) throws RemoteException {


        gameStarted.put(id, true);

        Snake s = snakes.get(id);
        s.setReady(true);

        for(int playerId : players.keySet()){
            ISnakeClient c = players.get(playerId);
            c.updateSnake(s);
        }


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







    @Override
    public void heartbeat(int id) throws RemoteException {

    }


    public void sendMessage(String message,int state) throws RemoteException {
        if(state == LOBBY || state == GAME)
            {
                for (ISnakeClient c : players.values()) {
                    c.displayMessage(message);
                }
            }



    }



    public void notifyDisconnect(Snake s) throws RemoteException {
        String message = "0:SERVER:"+s.getName()+" disconnected from the lobby...";
        lobbyMessages.add(message);

        for(int id:players.keySet()){
            ISnakeClient c = players.get(id);
            c.playerDisconnected(s);
            c.displayMessage(message);
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
