package org.example.Client;

import org.example.Model.Snake;
import org.example.Model.Coordinate;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Map;

public interface ISnakeClient extends Remote {

    public void startGame(Map<Integer, Snake> snakes, Map<Integer, Coordinate> fruits) throws RemoteException;
    public void updateGame(Map<Integer, Snake> snakes, Map<Integer, Coordinate> fruits) throws RemoteException;


    public void displayMessage(String message) throws RemoteException;



    public void addToLobby(Map<Integer, Snake> players, ArrayList<Integer> ids,ArrayList<String> lobbyMessages) throws RemoteException, InterruptedException;
    public void updateASnakeInLobby(Snake s) throws RemoteException;
    public void addASnakeToLobby(Snake s) throws RemoteException;
    public void ASnakeDisconnected(Snake s) throws RemoteException;



    public void die()throws RemoteException;



}



