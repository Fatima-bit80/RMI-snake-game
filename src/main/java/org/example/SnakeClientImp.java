package org.example;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.*;

import static org.example.Statics.Config.PORT;

public class SnakeClientImp implements ISnakeClient{

    private boolean gameStarted = false;

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

        }else{//still in the lobby

        }

    }

    public static void connectToTheServer(String ip,String name){

    }


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("enter the ip of the server");
        String ip = input.nextLine();

        try {
            ISnakeServer server =(SnakeServerImp) Naming.lookup("rmi//"+ip+":"+PORT+"/snakeServer");


        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }


}
