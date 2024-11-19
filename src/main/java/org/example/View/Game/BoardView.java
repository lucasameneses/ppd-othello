package org.example.View.Game;

import org.example.Controller.GameController;

import javax.swing.*;
import java.awt.*;
import org.example.Model.ProfileType;

public class BoardView extends JPanel {

    int size = 8;

    public BoardView(GameController gameController) {

        setLayout(new BorderLayout());
        JButton[][] pieces = gameController.pieces;
        int[][] matrix = gameController.board;
        JPanel buttonsPanel = new JPanel(new GridLayout(size, size));

        gameController.updateButtons(pieces);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                int currentI = i;
                int currentJ = j;

                pieces[i][j].addActionListener(e -> {
                    if (matrix[currentI][currentJ] == 0) {
                        String data = currentI + "," + currentJ;
                        System.out.println(data);
                        gameController.makeAndSendMove(data);

                    }

                });
                buttonsPanel.add(pieces[i][j]);
            }
        }
        add(buttonsPanel, BorderLayout.CENTER);
    }


}