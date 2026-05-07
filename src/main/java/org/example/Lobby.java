package org.example;

import java.util.List;

public class Lobby {

    private int nbPlayers;

    private List<Snake> snakes;

    private int id;





    public void setId(int id) {
        this.id = id;
    }

    public void setNbPlayers(int nbPlayers) {
        this.nbPlayers = nbPlayers;
    }

    public void setSnakes(List<Snake> snakes) {
        this.snakes = snakes;
    }
}
