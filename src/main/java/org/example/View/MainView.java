package org.example.View;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {
    public JPanel mainPanel;

    public MainView() {

        setTitle("Othello");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());


        add(mainPanel);
        setVisible(true);
    }
}
