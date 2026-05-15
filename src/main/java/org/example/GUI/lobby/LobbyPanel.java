package org.example.GUI.lobby;

import org.example.Enums.ColorCode;
import org.example.FontLoader;
import org.example.GUI.GamePanel;
import org.example.GUI.ImagePanel;
import org.example.ISnakeServer;
import org.example.Snake;
import org.example.Statics.Images;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.example.Statics.Config.*;

public class LobbyPanel extends JPanel implements GamePanel {


    private Map<Integer,Snake> snakes;
    private int id;
    private ISnakeServer server;

    private Map<Integer,JLabel> statuses;
    private Map<Integer,ImagePanel> images;

    private ArrayList<String> messages;


    private JPanel playersContainer;
    private JPanel chatContainer;
    private JTextArea chatArea;
    private JTextField messageField;
    private JPanel playersPanel;
    private JPanel chatPanel;
    private JScrollPane playersScroll;
    private JScrollPane chatScroll;

    private final JPanel topPanel;
    private final JPanel centerPanel;
    private final JPanel bottomPanel;







    public LobbyPanel(ISnakeServer server, int id,Map<Integer,Snake> snakes,ArrayList<String> messages) throws RemoteException {

        this.server=server;
        this.id = id;
        this.snakes=snakes;
        this.messages=messages;

        statuses = new HashMap<>();
        images= new HashMap<>();

        setPreferredSize(new Dimension(LOBBY_WIDTH, LOBBY_HEIGHT));
        setLayout(new BorderLayout());
        setBackground(DARK_GREEN_COLOR);


        // TOP PANEL
        topPanel = new JPanel();
        initializeTopPanel();
        add(topPanel, BorderLayout.NORTH);


        // CENTER PANEL
        centerPanel = new JPanel(new GridLayout(1, 2, 30, 0));
        initializeCenterPanel();
        add(centerPanel, BorderLayout.CENTER);


        // BOTTOM BUTTONS
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        initializeBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);


        // TEST DATA
        addPlayer(new Snake(0,null,0,0,"Fatima",1,true));
        addPlayer(new Snake(1,null,0,1,"John",1,false));
        addPlayer(new Snake(2,null,0,2,"Maha",1,false));
        addPlayer(new Snake(3,null,0,3,"Alisar",1,false));
        addPlayer(new Snake(4,null,0,4,"Maryam",1,true));
        addPlayer(new Snake(5,null,0,5,"Reem",1,true));
        addPlayer(new Snake(0,null,0,6,"Mike",1,false));
        addPlayer(new Snake(1,null,0,7,"Alex",1,true));
        addPlayer(new Snake(2,null,0,8,"Bob",1,false));
        addPlayer(new Snake(3,null,0,9,"Alice",1,true));


displayMessage("hi");
displayMessage("hello");
displayMessage("yay");
    }


    private void initializeTopPanel() {
        topPanel.setBackground(DARK_GREEN_COLOR);
        topPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("GAME LOBBY");
        title.setForeground(LIGHT_GREEN_COLOR);
        title.setFont(FontLoader.loadPixelFont(30f));
        title.setAlignmentX(CENTER_ALIGNMENT);

        topPanel.add(title);
    }

    private void initializeCenterPanel() throws RemoteException{
        centerPanel.setBackground(DARKER_GREEN_COLOR);
        centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));


        // PLAYERS PANEL
        initializePlayersPanel();


        // CHAT PANEL
        initializeChatPanel();


        // ADD TO CENTER
        centerPanel.add(playersPanel);
        centerPanel.add(chatPanel);
    }

    private void initializeChatPanel() throws RemoteException{
        chatPanel = new JPanel(new BorderLayout(0, 15));

        chatPanel.setBackground(DARK_GREEN_COLOR);

        chatPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT_GREEN_COLOR, 2),
                new EmptyBorder(15, 15, 15, 15)
        ));

        JLabel chatTitle = new JLabel("LOBBY CHAT");
        chatTitle.setForeground(LIGHT_GREEN_COLOR);
        chatTitle.setFont(FontLoader.loadPixelFont(20f));


        // CHAT AREA

        chatContainer = new JPanel();
        chatContainer.setLayout(new BoxLayout(playersContainer, BoxLayout.Y_AXIS));
        chatContainer.setBackground(DARKER_GREEN_COLOR);
        chatContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));


        chatScroll = new JScrollPane(chatContainer);
        initializeScrollPane(chatScroll);

        playersPanel.add(playersScroll, BorderLayout.CENTER);

        if(messages!=null){
            for(String message:messages){
                displayMessage(message);
            }
        }
//        chatArea = new JTextArea();
//        chatArea.setEditable(false);
//        chatArea.setLineWrap(true);
//        chatArea.setWrapStyleWord(true);
//        chatArea.setBackground(DARKER_GREEN_COLOR);
//        chatArea.setForeground(LIGHT_GREEN_COLOR);
//        chatArea.setFont(FontLoader.loadPixelFont(12f));
//        chatArea.setBorder(new EmptyBorder(10, 10, 10, 10));
//
//
//        JScrollPane chatScroll = new JScrollPane(chatArea);
//        chatScroll.setBorder(null);


        // INPUT AREA
        JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
        inputPanel.setOpaque(false);

        messageField = new JTextField();

        messageField.setBackground(DARKER_GREEN_COLOR);
        messageField.setForeground(LIGHT_GREEN_COLOR);
        messageField.setCaretColor(LIGHT_GREEN_COLOR);
        messageField.setFont(FontLoader.loadPixelFont(15f));
        messageField.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton sendButton = createButton("SEND", DARKER_GREEN_COLOR);
        sendButton.setFont(FontLoader.loadPixelFont(15f));
        sendButton.setPreferredSize(new Dimension(100, 45));
        sendButton.setForeground(LIGHT_GREEN_COLOR);

        inputPanel.add(messageField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);



        messageField.addActionListener(e -> {
            try {
                sendMessageToTheServer();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });
        sendButton.addActionListener(e -> {
            try {
                sendMessageToTheServer();
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        chatPanel.add(chatTitle, BorderLayout.NORTH);
        chatPanel.add(chatScroll, BorderLayout.CENTER);
        chatPanel.add(inputPanel, BorderLayout.SOUTH);


    }

    private void initializePlayersPanel() {
        playersPanel = new JPanel(new BorderLayout());
        playersPanel.setBackground(DARK_GREEN_COLOR);

        playersPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT_GREEN_COLOR, 2),
                new EmptyBorder(15, 15, 15, 15)
        ));


        JLabel playersTitle = new JLabel("PLAYERS");
        playersTitle.setForeground(LIGHT_GREEN_COLOR);
        playersTitle.setFont(FontLoader.loadPixelFont(20f));
        playersTitle.setBorder(new EmptyBorder(10, 10, 10, 10));

        playersPanel.add(playersTitle, BorderLayout.NORTH);



        playersContainer = new JPanel();
        playersContainer.setLayout(new BoxLayout(playersContainer, BoxLayout.Y_AXIS));
        playersContainer.setBackground(DARKER_GREEN_COLOR);
        playersContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));


        playersScroll = new JScrollPane(playersContainer);
        initializeScrollPane(playersScroll);

        playersPanel.add(playersScroll, BorderLayout.CENTER);


        if(snakes != null){
            for(Snake s: snakes.values())
                addPlayer(s);;
        }

    }

    private void initializeScrollPane(JScrollPane scrollPane) {
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBorder(null);
        scrollPane.setBackground(DARKER_GREEN_COLOR);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setBackground(LIGHT_GREEN_COLOR);
        scrollPane.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        );
        scrollPane.getViewport().setScrollMode(JViewport.BLIT_SCROLL_MODE);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);


        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {

            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = LIGHT_GREEN_COLOR;
                this.trackColor = DARK_GREEN_COLOR;
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                return createZeroButton();
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                return createZeroButton();
            }

            private JButton createZeroButton() {
                JButton button = new JButton();
                button.setPreferredSize(new Dimension(0, 0));
                return button;
            }
        });

    }

    private void initializeBottomPanel() {

        bottomPanel.setBackground(new Color(25, 25, 25));

        JButton readyBtn = createButton("READY", new Color(0, 180, 0));
        JButton colorBtn = createButton("CHANGE COLOR", new Color(180, 140, 0));
        JButton leaveBtn = createButton("LEAVE", new Color(180, 40, 40));

        bottomPanel.add(readyBtn);
        bottomPanel.add(colorBtn);
        bottomPanel.add(leaveBtn);
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setSnakes(Map<Integer,Snake> snakes) {
        this.snakes = snakes;
    }





    // ADD PLAYER
    public void addPlayer(Snake s) {
        snakes.put(s.getId(), s);

        JPanel card = new JPanel(new BorderLayout());

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

        card.setBackground(LIGHT_GREEN_COLOR);

        card.setBorder(new EmptyBorder(5, 5, 5, 5));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.setOpaque(false);

        ImagePanel snakePreview = new ImagePanel(Images.snakeImage[s.getPlayerNumber()][0],30,30);


        JLabel nameLabel = new JLabel(s.getName());

        nameLabel.setForeground(DARKER_GREEN_COLOR);

        nameLabel.setFont(FontLoader.loadPixelFont(10f));

        left.add(snakePreview);
        left.add(Box.createHorizontalStrut(10));
        left.add(nameLabel);

        JLabel status = new JLabel(s.isReady() ? "READY" : "WAITING");

        status.setForeground(s.isReady() ? READY_GREEN_HEX : NOT_READY_ORANGE_HEX);

        status.setFont(FontLoader.loadPixelFont(10f));

        card.add(left, BorderLayout.WEST);
        card.add(status, BorderLayout.EAST);

        playersContainer.add(card);
        playersContainer.add(Box.createVerticalStrut(10));

        statuses.put(s.id,status);
        images.put(s.getId(),snakePreview);


        revalidate();
        repaint();
    }

    public void updatePlayersPanel(Snake s) {
        Snake old = snakes.get(s.getId());

        if(old.getPlayerNumber() != s.getPlayerNumber()) {
            updateImage(s.getId(),s.getPlayerNumber());
        }

        if(old.isReady()!=s.isReady()) {
            updateStatus(s.getId(),s.isReady());
        }

        snakes.put(s.getId(), s);

    }



    // BUTTON STYLE
    private JButton createButton(String text, Color color) {

        JButton button = new JButton(text);

        button.setFocusPainted(false);

        button.setForeground(Color.WHITE);
        button.setBackground(color);

        button.setPreferredSize(new Dimension(170, 45));

        button.setBorder(BorderFactory.createEmptyBorder());

        return button;
    }

    // LABEL STYLE
    private JLabel createInfoLabel(String text, int size, boolean bold) {

        JLabel label = new JLabel(text);

        label.setForeground(Color.WHITE);

        label.setFont(new Font(
                "Arial",
                bold ? Font.BOLD : Font.PLAIN,
                size
        ));

        return label;
    }

    @Override
    public void displayMessage(String message) {

        String[] parts = message.split(":");

        int playerId =Integer.parseInt(parts[0]);

        String playerName =parts[1];

        String msg = "";
        for(int i=2;i<parts.length;i++) {
            msg += parts[i];
        }

        int playerNumber = snakes.get(playerId).getPlayerNumber();

     String colorHex = ColorCode.fromCode(playerNumber).getColorHex();
     Color color = ColorCode.getColor(colorHex);



        JPanel messagePanel = new JPanel(new BorderLayout());
        messagePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
        messagePanel.setBackground(DARK_GREEN_COLOR);
        messagePanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel name = new JLabel(playerName+":");
        name.setForeground(color);
        name.setBackground(DARKER_GREEN_COLOR);
        name.setFont(FontLoader.loadPixelFont(10f));



        JLabel messageLabel = new JLabel(msg);
        messageLabel.setForeground(LIGHT_GREEN_COLOR);
        messageLabel.setFont(FontLoader.loadPixelFont(10f));

        messagePanel.add(name, BorderLayout.WEST);
        messagePanel.add(messageLabel, BorderLayout.EAST);



        chatContainer.add(messagePanel);
        chatContainer.add(Box.createVerticalStrut(10));


        revalidate();
        repaint();





    }

    public void updateStatus(int id,boolean status){
      JLabel statusPanel =   statuses.get(id);
        statusPanel.setText(status ? "READY" : "WAITING");
        statusPanel.setForeground(status? READY_GREEN_HEX : NOT_READY_ORANGE_HEX);
    }

    public void updateImage(int id,int playerNumber){
        ImagePanel image = images.get(id);
        image.setImage(Images.snakeImage[playerNumber][0]);
    }


    public void sendMessageToTheServer() throws RemoteException {

        String msg = messageField.getText().trim();

        if (msg.isEmpty())
            return;

        try {
            server.displayLobbyChat(id,msg);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        messageField.setText("");

    }


    // TEST MAIN
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            JFrame frame = new JFrame("Snake Lobby");

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


            try {
                frame.setContentPane(new LobbyPanel(null, -1,null,null));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }

            frame.setVisible(true);
            frame.pack();
            frame.setLocationRelativeTo(null);

        });
    }

}
