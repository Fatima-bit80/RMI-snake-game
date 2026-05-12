package org.example;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISnakeServer extends Remote {

    public int connect(ISnakeClient client,String name) throws RemoteException;
    //returns id
    //if there are 6 or more players -> client is put in the watchlist
    //else the player enter the lobby

    public void requestStartGame(int id) throws RemoteException;
    //i am already in the lobby and i want to start the game
    //game only starts when all players are ready

    public void disconnect(int id) throws RemoteException;
    //if in lobby i leave
    //if in waitinf list i am removed

    public void setDirection(int id, int direction) throws RemoteException;
}
