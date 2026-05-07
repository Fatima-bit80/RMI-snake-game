package org.example;

import javax.swing.*;
import java.util.List;

public class Lobby extends JPanel {

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
