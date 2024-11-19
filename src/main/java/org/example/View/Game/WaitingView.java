package org.example.View.Game;

import javax.swing.*;
import java.awt.*;

public class WaitingView extends JPanel {

    public WaitingView() {
        setLayout(new BorderLayout());
        setOpaque(false);

        JLabel label = new JLabel("Esperando a jogada do oponente...", JLabel.CENTER);
        add(label, BorderLayout.CENTER);
    }

}