package org.example.View;

import org.example.Controller.GameController;

import javax.swing.*;
import java.awt.*;

import java.util.Objects;

public class ResultView extends JPanel {

    public ResultView(GameController gameController) {

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(200, 0, 0, 0));

        JLabel title = new JLabel();
        if(Objects.equals(gameController.result, "winner")){
            title.setText("VITÓRIA");
            title.setForeground(Color.GREEN);
        }else if (Objects.equals(gameController.result, "loser")){
            title.setText("DERROTA");
            title.setForeground(Color.RED);
        } else if (Objects.equals(gameController.result, "draw")){
            title.setText("EMPATE");
            title.setForeground(Color.BLUE);
        }

        title.setFont(new Font("Arial", Font.BOLD, 34));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        add(title, BorderLayout.NORTH);




    }

}
