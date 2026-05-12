package org.example.GUI.lobby;

import org.example.GUI.GamePanel;
import org.example.Snake;

import javax.swing.*;
import java.util.List;

public class LobbyPanel extends JPanel implements GamePanel {

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

    @Override
    public void displayMessage(String message) {

    }
}
