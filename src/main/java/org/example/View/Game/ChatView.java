package org.example.View.Game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.example.Controller.GameController;

import javax.swing.*;
import java.awt.*;

public class ChatView extends JPanel {

    public JLabel white;

    public JLabel black;

    public ChatView(GameController gameController) {

        setLayout(new BorderLayout());

        Dimension minSize = new Dimension(300, 200);
        setMinimumSize(minSize);

        JPanel controllerPanel = new JPanel();
        controllerPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        JButton passButton = new JButton("passar \n a vez");
        passButton.setForeground(Color.WHITE);
        passButton.setBackground(Color.blue);
        passButton.setPreferredSize(new Dimension(100, 30));
        passButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("passar a vez");
                gameController.passAndSendMove();
            }
        });
        buttonPanel.add(passButton, BorderLayout.WEST);

        JButton giveUpButton = new JButton("desistir");
        giveUpButton.setForeground(Color.WHITE);
        giveUpButton.setBackground(Color.RED);
        giveUpButton.setPreferredSize(new Dimension(100, 30));
        giveUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("desistir");
                gameController.sendResult("loser");
            }
        });
        buttonPanel.add(giveUpButton, BorderLayout.EAST);

        controllerPanel.add(buttonPanel, BorderLayout.NORTH);

        JPanel scoreboardPanel = new JPanel();
        scoreboardPanel.add(gameController.scoreboard[0], BorderLayout.WEST);
        scoreboardPanel.add(gameController.scoreboard[1], BorderLayout.EAST);
        controllerPanel.add(scoreboardPanel, BorderLayout.SOUTH);


        add(controllerPanel, BorderLayout.NORTH);

        JTextArea chatTextArea = gameController.chatTextArea;
        chatTextArea.setEditable(false);
        chatTextArea.setLineWrap(true);
        chatTextArea.setAlignmentX(Component.RIGHT_ALIGNMENT);
        JScrollPane scrollChat = new JScrollPane(chatTextArea);
        add(scrollChat, BorderLayout.CENTER);


        JTextField campoTextoChat = new JTextField();
        campoTextoChat.addActionListener(e -> {
            gameController.sendMessage(campoTextoChat.getText());
            campoTextoChat.setText("");
        });
        add(campoTextoChat, BorderLayout.SOUTH);

        chatTextArea.append("Bem-vindos ao jogo!" + "\n");

    }

}
