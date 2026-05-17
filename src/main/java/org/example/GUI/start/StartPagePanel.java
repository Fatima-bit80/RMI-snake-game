package org.example.GUI.start;

import org.example.GUI.GamePanel;
import org.example.GUI.MessagePanel;
import org.example.SnakeClientImp;
import org.example.Statics.Images;

import static org.example.Statics.Config.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StartPagePanel extends JPanel implements GamePanel {



    private final Image backgroundImage;

    private final MessagePanel messagePanel;

    private final JLabel titleLabel;
    private final JLabel ipAddressLabel;
    private final JLabel nameLabel;
  //  private final JTextArea messageArea;

    private final JTextField ipAddressField;
    private final JTextField snakeNameField;

    private final JButton connectButton;

    private final SnakeClientImp client;

    private final Image titleImage;

    private int fieldWidth = 200;

    public StartPagePanel(SnakeClientImp snakeClient) {
        backgroundImage= Images.startBack;
        titleImage = Images.snakesGame.getScaledInstance(425,53,Image.SCALE_SMOOTH);

        client = snakeClient;
        setPreferredSize(new Dimension(START_PAGE_WIDTH,START_PAGE_HEIGHT));

        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));


           setBorder(new EmptyBorder(70, 70, 70, 70));

        // Title
        titleLabel = new JLabel(new ImageIcon(titleImage));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(titleLabel);
        add(Box.createVerticalStrut(20));

        // Subtitle
        JLabel subtitle = new JLabel("Connect to a server and play");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setForeground(LIGHT_GREEN_COLOR);
        subtitle.setFont(pixelFont.deriveFont(12f));
        add(subtitle);
        add(Box.createVerticalStrut(25));

        // IP Address
        ipAddressLabel = new JLabel("IP Address");
        ipAddressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ipAddressLabel.setFont(pixelFont.deriveFont(14f));
        ipAddressLabel.setForeground(LIGHT_GREEN_COLOR);
        add(ipAddressLabel);
        add(Box.createVerticalStrut(10));


        ipAddressField = new JTextField();
        ipAddressField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT_GREEN_COLOR, 2),
                new EmptyBorder(8, 12, 8, 12)
        ));
        ipAddressField.setCaretColor(LIGHT_GREEN_COLOR);
        ipAddressField.setBackground(DARK_GREEN_COLOR);
        ipAddressField.setForeground(LIGHT_GREEN_COLOR);
        ipAddressField.setText("127.0.0.1");
        ipAddressField.setHorizontalAlignment(JTextField.CENTER);
        ipAddressField.setMaximumSize(new Dimension(fieldWidth, 60));
        ipAddressField.setFont(pixelFont.deriveFont(16f));
        add(ipAddressField);
        add(Box.createVerticalStrut(25));

        // Snake Name
        nameLabel = new JLabel("Snake Name");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(pixelFont.deriveFont(14f));
        nameLabel.setForeground(LIGHT_GREEN_COLOR);
        add(nameLabel);
        add(Box.createVerticalStrut(10));

        snakeNameField = new JTextField();
        snakeNameField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT_GREEN_COLOR, 2),
                new EmptyBorder(8, 12, 8, 12)
        ));
        snakeNameField.setBackground(DARK_GREEN_COLOR);
        snakeNameField.setCaretColor(LIGHT_GREEN_COLOR);
        snakeNameField.setForeground(LIGHT_GREEN_COLOR);
        snakeNameField.setHorizontalAlignment(JTextField.CENTER);
        snakeNameField.setMaximumSize(new Dimension(fieldWidth, 60));
        snakeNameField.setFont(pixelFont.deriveFont(16f));
        add(snakeNameField);
        add(Box.createVerticalStrut(45));

        // Connect Button
        connectButton = new JButton("CONNECT");
        connectButton.setBackground(DARK_GREEN_COLOR);
        connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        connectButton.setFocusPainted(false);
        connectButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(LIGHT_GREEN_COLOR, 2),
                new EmptyBorder(12, 25, 12, 25) // top, left, bottom, right padding
        ));
        connectButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );
        connectButton.setMaximumSize(new Dimension(200, 60));
        connectButton.setFont(pixelFont.deriveFont(16f));
        connectButton.setForeground(LIGHT_GREEN_COLOR);
        connectButton.addActionListener(e -> connect());
        add(connectButton);
        add(Box.createVerticalStrut(30));

        // Message Label
        messagePanel = new MessagePanel(TOTAL_WIDTH/4,40,"Enter your name and the server's ip address to connect to the game");
        JTextArea messageArea = messagePanel.getMessageArea();
        messageArea.setEditable(false);
        messageArea.setBackground(DARK_GREEN_COLOR);
        messageArea.setForeground(LIGHT_GREEN_COLOR);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(pixelFont.deriveFont(14f));
        messageArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(messageArea);


        ipAddressField.addActionListener(e -> connect());
        snakeNameField.addActionListener(e -> connect());




        setVisible(true);





    }
    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        // BACKGROUND IMAGE
        g2d.drawImage(
                backgroundImage,
                0,
                0,
                getWidth(),
                getHeight(),
                this
        );

        // TRANSPARENT BLACK OVERLAY
        g2d.setColor(new Color(0, 0, 0, 150));

        g2d.fillRect(
                0,
                0,
                getWidth(),
                getHeight()
        );
    }



    @Override
    public void displayMessage(String message) {
        messagePanel.displayMessage(message);
    }

    public void connect(){
        client.connectToTheServer(ipAddressField.getText(),snakeNameField.getText());
    }



}