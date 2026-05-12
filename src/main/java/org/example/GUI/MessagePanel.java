package org.example.GUI;

import javax.swing.*;
import java.awt.*;

public class MessagePanel extends JPanel {
    JTextArea messageArea;

    public MessagePanel(int width, int height,String message) {
        setMaximumSize(new Dimension(width, height));

        messageArea = new JTextArea(message);
        messageArea.setEditable(false);
        messageArea.setFont(new Font("Arial", Font.PLAIN, 14));
        messageArea.setBackground(Color.WHITE);
        messageArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(messageArea);

        setVisible(true);
    }

    public JTextArea getMessageArea() {
        return messageArea;
    }

    public void displayMessage(String message){
        messageArea.setText(message);
    }
}
