package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ISnakeClient extends Remote {

    public void startGame(List<Snake> snakes) throws RemoteException;
    public void updateGame(List<Snake> snakes) throws RemoteException;


    public void displayMessage(String message) throws RemoteException;


    public void addToWaitingList() throws RemoteException;
    public void addToLobby(Map<Integer, Snake> players,ArrayList<Integer> ids) throws RemoteException;
    public void updateLobby(Map<Integer, Snake> players, ArrayList<Integer> ids) throws RemoteException;

    public void notifyChange(ArrayList<Snake> snakes) throws RemoteException;

    public void changeLabelText(String tetx) throws RemoteException;

    public void updateWaitingList(Map<Integer, Snake> snakes, ArrayList<Integer> integers) throws RemoteException;
    public void enableStartButton() throws RemoteException;
    public void disableStartButton() throws RemoteException;
}



