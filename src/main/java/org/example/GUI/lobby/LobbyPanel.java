package org.example.GUI.lobby;

import org.example.GUI.GamePanel;
import org.example.Snake;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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


        private final JPanel playersContainer;
        private final JTextArea chatArea;
        private final JTextField messageField;

        public LobbyPanel() {

            setLayout(new BorderLayout());
            setBackground(new Color(18, 18, 18));

            // =====================================================
            // TOP PANEL
            // =====================================================
            JPanel topPanel = new JPanel(new BorderLayout());
            topPanel.setBackground(new Color(25, 25, 25));
            topPanel.setBorder(new EmptyBorder(15, 20, 15, 20));

            JLabel title = new JLabel("SNAKE LOBBY");
            title.setForeground(new Color(0, 255, 120));
            title.setFont(new Font("Arial", Font.BOLD, 28));

            JLabel lobbyCode = new JLabel("Lobby Code: AB12");
            lobbyCode.setForeground(Color.WHITE);
            lobbyCode.setFont(new Font("Arial", Font.PLAIN, 16));

            topPanel.add(title, BorderLayout.WEST);
            topPanel.add(lobbyCode, BorderLayout.EAST);

            add(topPanel, BorderLayout.NORTH);

            // =====================================================
            // CENTER PANEL
            // =====================================================
            JPanel centerPanel = new JPanel(new GridLayout(1, 2, 20, 0));
            centerPanel.setBackground(new Color(18, 18, 18));
            centerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

            // =====================================================
            // PLAYERS PANEL
            // =====================================================
            JPanel playersPanel = new JPanel(new BorderLayout());
            playersPanel.setBackground(new Color(30, 30, 30));

            playersPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 255, 120), 2),
                    new EmptyBorder(15, 15, 15, 15)
            ));

            JLabel playersTitle = new JLabel("PLAYERS");
            playersTitle.setForeground(Color.WHITE);
            playersTitle.setFont(new Font("Arial", Font.BOLD, 22));

            playersContainer = new JPanel();
            playersContainer.setLayout(new BoxLayout(playersContainer, BoxLayout.Y_AXIS));
            playersContainer.setBackground(new Color(30, 30, 30));

            JScrollPane playersScroll = new JScrollPane(playersContainer);
            playersScroll.setBorder(null);
            playersScroll.getViewport().setBackground(new Color(30, 30, 30));

            playersPanel.add(playersTitle, BorderLayout.NORTH);
            playersPanel.add(playersScroll, BorderLayout.CENTER);

            // =====================================================
            // RIGHT SIDE PANEL
            // =====================================================
            JPanel rightPanel = new JPanel(new BorderLayout(0, 20));
            rightPanel.setBackground(new Color(18, 18, 18));

            // =====================================================
            // GAME INFO PANEL
            // =====================================================
            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

            infoPanel.setBackground(new Color(30, 30, 30));

            infoPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 255, 120), 2),
                    new EmptyBorder(20, 20, 20, 20)
            ));

            JLabel settingsTitle = createInfoLabel("GAME SETTINGS", 24, true);

            JLabel mode = createInfoLabel("Mode: Classic", 18, false);
            JLabel map = createInfoLabel("Map: Neon Arena", 18, false);
            JLabel speed = createInfoLabel("Speed: Medium", 18, false);
            JLabel maxPlayers = createInfoLabel("Max Players: 4", 18, false);

            infoPanel.add(settingsTitle);
            infoPanel.add(Box.createVerticalStrut(20));
            infoPanel.add(mode);
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(map);
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(speed);
            infoPanel.add(Box.createVerticalStrut(10));
            infoPanel.add(maxPlayers);

            // =====================================================
            // CHAT PANEL
            // =====================================================
            JPanel chatPanel = new JPanel(new BorderLayout(0, 15));

            chatPanel.setBackground(new Color(30, 30, 30));

            chatPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 255, 120), 2),
                    new EmptyBorder(15, 15, 15, 15)
            ));

            JLabel chatTitle = new JLabel("LOBBY CHAT");
            chatTitle.setForeground(Color.WHITE);
            chatTitle.setFont(new Font("Arial", Font.BOLD, 22));

            // CHAT AREA
            chatArea = new JTextArea();

            chatArea.setEditable(false);

            chatArea.setLineWrap(true);
            chatArea.setWrapStyleWord(true);

            chatArea.setBackground(new Color(20, 20, 20));
            chatArea.setForeground(Color.WHITE);

            chatArea.setFont(new Font("Consolas", Font.PLAIN, 15));

            chatArea.setBorder(new EmptyBorder(10, 10, 10, 10));

            JScrollPane chatScroll = new JScrollPane(chatArea);
            chatScroll.setBorder(null);

            // INPUT AREA
            JPanel inputPanel = new JPanel(new BorderLayout(10, 0));
            inputPanel.setOpaque(false);

            messageField = new JTextField();

            messageField.setBackground(new Color(45, 45, 45));
            messageField.setForeground(Color.WHITE);

            messageField.setCaretColor(Color.WHITE);

            messageField.setFont(new Font("Arial", Font.PLAIN, 15));

            messageField.setBorder(new EmptyBorder(10, 10, 10, 10));

            JButton sendButton = createButton("SEND", new Color(0, 120, 220));
            sendButton.setPreferredSize(new Dimension(100, 45));

            inputPanel.add(messageField, BorderLayout.CENTER);
            inputPanel.add(sendButton, BorderLayout.EAST);

            // SEND ACTION
            Runnable sendMessage = () -> {

                String msg = messageField.getText().trim();

                if (msg.isEmpty())
                    return;

                addChatMessage("Fatima", msg);

                messageField.setText("");
            };

            messageField.addActionListener(e -> sendMessage.run());
            sendButton.addActionListener(e -> sendMessage.run());

            chatPanel.add(chatTitle, BorderLayout.NORTH);
            chatPanel.add(chatScroll, BorderLayout.CENTER);
            chatPanel.add(inputPanel, BorderLayout.SOUTH);

            // =====================================================
            // RIGHT SIDE SPLIT
            // =====================================================
            JSplitPane splitPane = new JSplitPane(
                    JSplitPane.VERTICAL_SPLIT,
                    infoPanel,
                    chatPanel
            );

            splitPane.setResizeWeight(0.35);

            splitPane.setBorder(null);

            rightPanel.add(splitPane, BorderLayout.CENTER);

            // =====================================================
            // ADD TO CENTER
            // =====================================================
            centerPanel.add(playersPanel);
            centerPanel.add(rightPanel);

            add(centerPanel, BorderLayout.CENTER);

            // =====================================================
            // BOTTOM BUTTONS
            // =====================================================
            JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));

            bottomPanel.setBackground(new Color(25, 25, 25));

            JButton readyBtn = createButton("READY", new Color(0, 180, 0));
            JButton colorBtn = createButton("CHANGE COLOR", new Color(180, 140, 0));
            JButton leaveBtn = createButton("LEAVE", new Color(180, 40, 40));

            bottomPanel.add(readyBtn);
            bottomPanel.add(colorBtn);
            bottomPanel.add(leaveBtn);

            add(bottomPanel, BorderLayout.SOUTH);

            // =====================================================
            // TEST DATA
            // =====================================================
            addPlayer("Fatima", Color.GREEN, true, true);
            addPlayer("Ali", Color.CYAN, false, false);
            addPlayer("Sara", Color.PINK, true, false);

            addChatMessage("SYSTEM", "Welcome to the lobby!");
            addChatMessage("Ali", "Ready?");
            addChatMessage("Sara", "Let's start!");
        }

        // =====================================================
        // ADD PLAYER
        // =====================================================
        public void addPlayer(String name, Color snakeColor, boolean ready, boolean host) {

            JPanel card = new JPanel(new BorderLayout());

            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

            card.setBackground(new Color(45, 45, 45));

            card.setBorder(new EmptyBorder(10, 15, 10, 15));

            JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
            left.setOpaque(false);

            JPanel snakePreview = new JPanel();
            snakePreview.setPreferredSize(new Dimension(25, 25));
            snakePreview.setBackground(snakeColor);

            JLabel nameLabel = new JLabel((host ? "👑 " : "") + name);

            nameLabel.setForeground(Color.WHITE);

            nameLabel.setFont(new Font("Arial", Font.BOLD, 18));

            left.add(snakePreview);
            left.add(Box.createHorizontalStrut(10));
            left.add(nameLabel);

            JLabel status = new JLabel(ready ? "READY" : "WAITING");

            status.setForeground(ready ? new Color(0, 255, 120) : Color.ORANGE);

            status.setFont(new Font("Arial", Font.BOLD, 16));

            card.add(left, BorderLayout.WEST);
            card.add(status, BorderLayout.EAST);

            playersContainer.add(card);
            playersContainer.add(Box.createVerticalStrut(10));

            revalidate();
            repaint();
        }

        // =====================================================
        // ADD CHAT MESSAGE
        // =====================================================
        public void addChatMessage(String sender, String message) {

            chatArea.append(sender + ": " + message + "\n");

            chatArea.setCaretPosition(
                    chatArea.getDocument().getLength()
            );
        }

        // =====================================================
        // BUTTON STYLE
        // =====================================================
        private JButton createButton(String text, Color color) {

            JButton button = new JButton(text);

            button.setFocusPainted(false);

            button.setForeground(Color.WHITE);
            button.setBackground(color);

            button.setFont(new Font("Arial", Font.BOLD, 15));

            button.setPreferredSize(new Dimension(170, 45));

            button.setBorder(BorderFactory.createEmptyBorder());

            return button;
        }

        // =====================================================
        // LABEL STYLE
        // =====================================================
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

        // =====================================================
        // TEST MAIN
        // =====================================================
        public static void main(String[] args) {

            SwingUtilities.invokeLater(() -> {

                JFrame frame = new JFrame("Snake Lobby");

                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                frame.setSize(1200, 750);

                frame.setLocationRelativeTo(null);

                frame.setContentPane(new LobbyPanel());

                frame.setVisible(true);
            });
        }

}
