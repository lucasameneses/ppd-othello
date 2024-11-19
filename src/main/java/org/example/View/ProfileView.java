package org.example.View;

import org.example.Controller.ProfileController;
import org.example.Model.ProfileType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileView extends JPanel {

    public ProfileView(ProfileController profileController) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(200, 0, 0, 0));

        JLabel title = new JLabel("Othello");
        title.setFont(new Font("Arial", Font.BOLD, 34));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setVerticalAlignment(SwingConstants.CENTER);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(title, BorderLayout.NORTH);


        JPanel ipPanel = new JPanel();

        JLabel ipLabel = new JLabel("IP do Servidor:");
        ipPanel.add(ipLabel);

        JTextField ipField = new JTextField(15);
        ipField.setMaximumSize( ipField.getPreferredSize() );

        ipPanel.add(ipField);
        add(ipPanel);

        JPanel buttonPanel = new JPanel();

        JButton serverButton = new JButton("Servidor");
        serverButton.setPreferredSize(new Dimension(100, 30));
        serverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                profileController.selectProfile(ProfileType.SERVER, "");
            }
        });
        buttonPanel.add(serverButton);

        JButton clientButton = new JButton("Cliente");
        clientButton.setPreferredSize(new Dimension(100, 30));
        clientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ip = ipField.getText();
                profileController.selectProfile(ProfileType.CLIENT, ip);
            }
        });

        buttonPanel.add(clientButton);
        add(buttonPanel);

    }
}
