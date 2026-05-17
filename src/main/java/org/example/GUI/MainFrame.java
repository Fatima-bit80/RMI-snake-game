package org.example.GUI;

import org.example.GUI.lobby.LobbyPanel;
import org.example.GUI.start.StartPagePanel;
import org.example.ISnakeServer;
import org.example.SnakeClientImp;
import org.example.Statics.Images;
import org.example.GUI.game.MainGamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;
import java.util.HashMap;

public class MainFrame extends JFrame {

    private final HashMap<String,String> pageTitle;


    private Component currentComponent = null;

    private final CardLayout cardLayout;
    private final JPanel container;

    private final MainGamePanel mainGamePanel;
    private final StartPagePanel startPagePanel;
    private final LobbyPanel lobbyPanel;
    private final SnakeClientImp clientImp;



    public MainFrame(SnakeClientImp clientImp) throws RemoteException {
        this.clientImp = clientImp;

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        //panels
        mainGamePanel = new MainGamePanel(clientImp);
        startPagePanel = new StartPagePanel(clientImp);
        lobbyPanel = new LobbyPanel(clientImp);

        pageTitle = new HashMap<>();
        initializeMap();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Images.snakeImage[5][0]);


        container.add(mainGamePanel, mainGamePanel.getClass().getSimpleName());
        container.add(startPagePanel,startPagePanel.getClass().getSimpleName());
        container.add(lobbyPanel,lobbyPanel.getClass().getSimpleName());


        add(container);
        pack();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                System.out.println("Window is closing");
                clientImp.disconnect();

                dispose(); // closes window
                System.exit(0); // optional
            }
        });

        showPage(StartPagePanel.class.getSimpleName());
    }

    private void initializeMap() {
        pageTitle.put(mainGamePanel.getClass().getSimpleName(), "Snake Game");
        pageTitle.put(startPagePanel.getClass().getSimpleName(), "Start Page");
        pageTitle.put(lobbyPanel.getClass().getSimpleName(), "Lobby");

    }

    public void showPage(String pageName){


        if(pageTitle.containsKey(pageName)){
             setTitle(pageTitle.get(pageName));
        }

        for(Component c :container.getComponents()){
            if(c.getClass().getSimpleName().equals(pageName)){
                currentComponent = c;
                break;
            }
        }
        if(currentComponent != null){
            currentComponent.requestFocusInWindow();
        }
            cardLayout.show(container, pageName);


        Dimension size = currentComponent.getPreferredSize();

        container.setPreferredSize(size);

        pack();

        setLocationRelativeTo(null);

        currentComponent.requestFocusInWindow();

        System.out.println(currentComponent.getClass().getSimpleName());

    }



    public LobbyPanel getLobbyPanel() {
        return lobbyPanel;
    }

    public MainGamePanel getMainGamePanel() {
        return mainGamePanel;
    }


    public StartPagePanel getStartPagePanel() {
        return startPagePanel;
    }



    public GamePanel getCurrentPanel() {

        for (Component comp : container.getComponents()) {

            if (comp.isVisible()) {
                return (GamePanel) comp;
            }
        }

        return null;
    }


    public static void main(String[] args) throws RemoteException {
        MainFrame main = new MainFrame(SnakeClientImp.getInstance());
        try {
            Thread.sleep(3000);
            main.showPage(MainGamePanel.class.getSimpleName());
            Thread.sleep(3000);
            main.showPage(StartPagePanel.class.getSimpleName());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void resetUI() throws RemoteException {
        currentComponent=startPagePanel;
        lobbyPanel.reset();
        mainGamePanel.reset();
    }
}
