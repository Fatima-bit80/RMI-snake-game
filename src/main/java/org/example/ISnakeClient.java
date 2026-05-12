package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ISnakeClient extends Remote {

    public void startGame(List<Snake> snakes) throws RemoteException;


    public void displayMessage(String message) throws RemoteException;


    public void addToWaitingList() throws RemoteException;
    public void addToLobby() throws RemoteException;

    public void notifyChange(ArrayList<Snake> snakes) throws RemoteException;

    public void changeLabelText(String tetx) throws RemoteException;

}
