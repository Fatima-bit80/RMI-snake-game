package org.example.GUI.game;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.TreeMap;

public class LeaderBoardPanel extends JPanel {

    private JTable leaderboardTable;
    private DefaultTableModel tableModel;

    LeaderBoardPanel(ArrayList<String> scores) {

        setLayout(new BorderLayout());
        setBackground(new Color(30, 30, 30));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel title = new JLabel("Leaderboard", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        add(title, BorderLayout.NORTH);

        // Table columns
        String[] columns = {"Rank", "Name", "Score"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        leaderboardTable = new JTable(tableModel);

        leaderboardTable.setRowHeight(35);
        leaderboardTable.setFont(new Font("Arial", Font.PLAIN, 16));
        leaderboardTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

        leaderboardTable.setBackground(new Color(45, 45, 45));
        leaderboardTable.setForeground(Color.WHITE);

        leaderboardTable.getTableHeader().setBackground(new Color(70, 70, 70));
        leaderboardTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(leaderboardTable);
        add(scrollPane, BorderLayout.CENTER);

        updateLeaderBoard(scores);
    }

    public void updateLeaderBoard(ArrayList<String> scores) {

        // Clear old rows
        tableModel.setRowCount(0);

        TreeMap<Integer,ArrayList<String>> map = new TreeMap<>();


        for (int i = 0; i < scores.size(); i++) {

            /*
             Expected format:
             "Fatima:120"
             */

            String data = scores.get(i);

            String[] parts = data.split(":");

            String name = parts[0];
            int score =Integer.parseInt(parts[1]);

            if(!map.containsKey(score)){

                map.put(score,new ArrayList<>());
            }
         map.get(score).add(name);


        }


        for (int i = map.size()-1; i >=0;i--) {
            int score = (int) map.keySet().toArray()[i];
            for(String name:map.get(score))
            tableModel.addRow(new Object[]{map.size()-i,name,score});
        }

    }
}