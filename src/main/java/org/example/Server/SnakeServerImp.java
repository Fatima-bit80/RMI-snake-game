package org.example.Server;

import org.example.Client.ISnakeClient;
import org.example.Model.Snake;
import org.example.Statics.Coordinate;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.example.Statics.Config.*;

public class SnakeServerImp extends UnicastRemoteObject implements ISnakeServer {

    //todo save last id given to file
    private static boolean gameOn = false;
    private static int id = 0;


    private static final  List<Integer> availableColors = new CopyOnWriteArrayList<>();

    static {
        availableColors.add(0);
        availableColors.add(1);
        availableColors.add(2);
        availableColors.add(3);
        availableColors.add(4);
        availableColors.add(5);
    }

    private static final boolean[][] positions = new boolean[COLUMNS][ROWS];



    private int nbPlayers = 0;

    private static final Map<Integer, Coordinate> fruits = new ConcurrentHashMap<>();

    static {
        fruits.put(0, new Coordinate(5, 5));
        fruits.put(1, new Coordinate(10, 5));
    }

    //players in the lobby or in game
    private final Map<Integer, ISnakeClient> players = new ConcurrentHashMap();//int is id

    //players unable to enter

    private final Map<Integer, Snake> snakes = new ConcurrentHashMap();//int is id


    private final ArrayList<String> lobbyMessages = new ArrayList<>();

    //to see what players are ready
    private final Map<Integer, Boolean> gameStarted = new ConcurrentHashMap();//int is id


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
            client.displayMessage(message + "the one you want");

            addToLobby(client, s);
        } else {
            client.displayMessage("you cant join the lobby is full");
        }

        return id;
    }


    private Snake createSnake(int id, String name, int playerNumber, int state) {

        Snake s = new Snake(playerNumber, null, 2, id, name, state, false);

        if (playerNumber != -1)
            s.setCoordinates(createInitialPosition(playerNumber));

        snakes.put(id, s);
        return s;
    }

    @Override
    public synchronized void disconnect(int id) throws RemoteException, InterruptedException {

        //todo if heart beat not sent -> disconnect

        Snake s = snakes.get(id);
        snakes.remove(id);


        if (players.containsKey(id)) {

            int playerNumber = s.getPlayerNumber();
            availableColors.add(playerNumber);




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
        String formattedMessage = id + ":" + snakes.get(id).getName() + ":" + message;
        lobbyMessages.add(formattedMessage);
        for (ISnakeClient snakeClient : players.values()) {
            snakeClient.displayMessage(formattedMessage);
        }
    }

    public synchronized void addToLobby(ISnakeClient client, Snake s) throws RemoteException, InterruptedException {


        int snakeId = s.getId();

        int playerNumber = availableColors.get(0);
        availableColors.remove((Object) playerNumber);

        s.setPlayerNumber(playerNumber);
        s.setCoordinates(createInitialPosition(playerNumber));
        s.setState(LOBBY);

        nbPlayers++;


        gameStarted.put(snakeId, false);


        players.put(snakeId, client);


        client.addToLobby(snakes, new ArrayList<>(players.keySet()), lobbyMessages);


        notifyAllPlayersInLobby(s);


    }

    public void notifyAllPlayersInLobby(Snake s) {
        String lobbyMessage = "0:SERVER:" + s.getName() + " joined the lobby...";
        lobbyMessages.add(lobbyMessage);

        for (int i :new ArrayList<>(players.keySet())) {
            System.out.println("id to notify " + i);
            ISnakeClient c = players.get(i);
            try {

                if (s.getId() != i)
                    c.addASnakeToLobby(s);
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

        for (int playerId : new ArrayList<>(players.keySet())) {
            ISnakeClient c = players.get(playerId);
            c.updateASnakeInLobby(s);
        }


        if (new ArrayList<>(players.keySet()).size() < 2) {
            return;
        }

        for (int playerId : new ArrayList<>(gameStarted.keySet())) {
            //if atleast 1 player isn't ready -> don't start the game
            if (gameStarted.get(playerId) == false) {
                return;
            }
        }


        //if all players are ready and number of players is atleast 2
        new Thread(() -> {
            try {
                if(gameOn==false)
                startGame();
                else {
                    addToOnGame(id);
                }
            } catch (RemoteException | InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }


    public void addToOnGame(int id) throws RemoteException, InterruptedException {
        Snake s = snakes.get(id);
        s.setState(GAME);
        ISnakeClient client = players.get(id);
        client.startGame(snakes,fruits);
    }

    public void resetPositions(){
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                positions[i][j] = false;
            }
        }
    }

    public void setPlayersCoordinatesTrue(){

        for (int id : new ArrayList<>(players.keySet())) {
            Snake s = snakes.get(id);
            s.setState(GAME);
            for (Coordinate c : s.coordinates) {
                positions[c.getX()][c.getY()] = true;
            }
        }
    }

    public void notifyAllPlayersGameStarted(){

        for (int id : new ArrayList<>(players.keySet())) {
            new Thread(() -> {
                ISnakeClient c = players.get(id);
                try {
                    c.startGame(snakes, fruits);
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }).start();

        }
    }

    public void growOrMoveSnake(Snake snake) throws RemoteException {
        boolean grew = false;
        if (snake.isReady() && snake.getState() == GAME) {



            int totalF = (int) (new ArrayList<>(fruits.keySet()).toArray()[0]) + (int) (new ArrayList<>(fruits.keySet()).toArray()[1]);
            int otherFruit = 3 - totalF;
            for (int fruitNb :new ArrayList<>(fruits.keySet())) {

                if (fruits.get(fruitNb).equals(snake.coordinates.get(0))) {
                    fruits.remove(fruitNb);
                    Coordinate c = findCoordinate();
                    fruits.put(otherFruit, c);
                    snake.grow();

                    grew=true;

                }
            }

            if (grew == false) {
                snake.moveForward();
            }


Coordinate c = snake.coordinates.get(0);

            if (c.getX() <= 0 || c.getX() >= COLUMNS - 1 || c.getY() <= 0 || c.getY() >= ROWS - 1 || positions[c.getX()][c.getY()] == true ) {
                    System.out.println("border was defeated by " + snake.getName());

                    int id = snake.getId();

                    new Thread(() -> {

                        try {


                            ISnakeClient client = players.get(id);
                           disconnect(id);
                            client.die();

                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }).start();
                }


        }
    }

    public void notifyNewSnakePositions(){
        for (int i : new ArrayList<>(players.keySet())) {
            if (snakes.get(i)!=null&&snakes.get(i).isReady() && snakes.get(i).state == GAME) {

                new Thread(() -> {
                    ISnakeClient c = players.get(i);
                    try {
                        if(c!=null)
                        c.updateGame(snakes, fruits);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                }).start();

            }
        }
    }

    public void startGame() throws RemoteException, InterruptedException {

        gameOn = true;
        resetPositions();
        setPlayersCoordinatesTrue();


        System.out.println("Starting game");

       notifyAllPlayersGameStarted();


        //todo displayNumbers coutdown

        //todo if a player joined while the others are in game, make sure that you create it in a place where no snake is


        while (true) {


                Thread.sleep(400);

            if (players.isEmpty()) break;


            for (Snake snake :new ArrayList<>(snakes.values())) {
                growOrMoveSnake(snake);
            }

            resetPositions();
            setPlayersCoordinatesTrue();

            notifyNewSnakePositions();

        }

        gameOn=false;
    }

    public synchronized Coordinate findCoordinate() {
        Random rand = new Random();

        int x = 1+ rand.nextInt(COLUMNS - 2);
        int y = 1+ rand.nextInt( ROWS - 2);
        if (positions[x][y] == false) {
            return new Coordinate(x, y);
        } else {
            return findCoordinate();
        }
    }

    @Override
    public void heartbeat(int id) throws RemoteException {

    }


    public void sendMessage(String message, int state) throws RemoteException {
        if (state == LOBBY || state == GAME) {
            for (ISnakeClient c : players.values()) {
                c.displayMessage(message);
            }
        }


    }


    public void notifyDisconnect(Snake s) throws RemoteException {
        String message = "0:SERVER:" + s.getName() + " disconnected from the lobby...";
        lobbyMessages.add(message);

        for (int id : players.keySet()) {
            ISnakeClient c = players.get(id);
            c.ASnakeDisconnected(s);
            c.displayMessage(message);
        }

    }


    private ArrayList<Coordinate> createInitialPosition(int playerNumber) {
        ArrayList<Coordinate> snakeCoordinate = new ArrayList<>();

        snakeCoordinate.add(new Coordinate((playerNumber + 2) * 4, 6));
        snakeCoordinate.add(new Coordinate((playerNumber + 2) * 4, 5));
        snakeCoordinate.add(new Coordinate((playerNumber + 2) * 4, 4));

        return snakeCoordinate;
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
