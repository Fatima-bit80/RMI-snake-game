package org.example;

import static org.example.Statics.Config.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StartGameGUI extends JFrame {

    private final JLabel titleLabel;
    private final JLabel ipAddressLabel;
    private final JLabel nameLabel;
    private final JTextArea messageArea;

    private final JTextField ipAddressField;
    private final JTextField snakeNameField;

    private final JButton connectButton;

    public StartGameGUI() {

        setTitle("Snake Online - Connect");
        setSize(TOTAL_WIDTH/3, GAME_HEIGHT*3/4);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel background = new JPanel();
        background.setLayout(new GridBagLayout());
        background.setBackground(Color.WHITE);

        // Create card
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.LIGHT_GRAY);
        card.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Title
        titleLabel = new JLabel("SNAKE ONLINE");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));

        // Subtitle
        JLabel subtitle = new JLabel("Connect to a server and play");
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 14));
        card.add(subtitle);
        card.add(Box.createVerticalStrut(30));

        // IP Address
        ipAddressLabel = new JLabel("IP Address");
        ipAddressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        ipAddressLabel.setFont(new Font("Arial", Font.BOLD, 16));
        card.add(ipAddressLabel);
        card.add(Box.createVerticalStrut(10));

        ipAddressField = new JTextField();
        ipAddressField.setText("127.0.0.1");
        ipAddressField.setHorizontalAlignment(JTextField.CENTER);
        ipAddressField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        ipAddressField.setFont(new Font("Arial", Font.PLAIN, 14));
        card.add(ipAddressField);
        card.add(Box.createVerticalStrut(30));

        // Snake Name
        nameLabel = new JLabel("Snake Name");
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        card.add(nameLabel);
        card.add(Box.createVerticalStrut(10));

        snakeNameField = new JTextField();
        snakeNameField.setHorizontalAlignment(JTextField.CENTER);
        snakeNameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        snakeNameField.setFont(new Font("Arial", Font.PLAIN, 14));
        card.add(snakeNameField);
        card.add(Box.createVerticalStrut(30));

        // Connect Button
        connectButton = new JButton("CONNECT");
        connectButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        connectButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        connectButton.setFont(new Font("Arial", Font.BOLD, 16));
        connectButton.addActionListener(e -> SnakeClientImp.connectToTheServer(ipAddressField.getText(),snakeNameField.getText()));
        card.add(connectButton);
        card.add(Box.createVerticalStrut(30));

        // Message Label
        messageArea = new JTextArea("Enter your name and the server's ip address to connect to the game");
        messageArea.setEditable(false);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 14));
        messageArea.setBackground(Color.WHITE);
        messageArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(messageArea);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        background.add(card, gbc);

        setLocationRelativeTo(null);
        add(background);
        setVisible(true);
        setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartGameGUI::new);
    }
}