package org.example.View.Game;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import org.example.Controller.GameController;

import javax.swing.*;

public class GameView extends JPanel {

    public JPanel boardPanel;
    public JPanel chatPanel;

    public GameView(GameController gameController) {
        setLayout(new BorderLayout());


        boardPanel = new BoardView(gameController);
        chatPanel = new ChatView(gameController);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(boardPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(chatPanel, BorderLayout.EAST);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = getWidth();
                int chatWidth = (int) (width * 0.4);
                chatPanel.setPreferredSize(new Dimension(chatWidth, getHeight()));
                revalidate();
            }
        });
    }
}

