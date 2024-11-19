package org.example.Controller;

import org.example.Model.ProfileType;
import org.example.View.ProfileView;
import org.example.View.MainView;

import java.awt.*;

public class ProfileController {

    MainController mainController;
    MainView mainScreen;
    Communication communication;

    public ProfileController(MainController mainController) {
        this.mainController = mainController;
        this.mainScreen = mainController.mainView;
        this.communication = mainController.communication;

        ProfileView profileView = new ProfileView(this);
        mainScreen.mainPanel.add(profileView, "initialView");
        ((CardLayout) mainScreen.mainPanel.getLayout()).show(mainScreen.mainPanel, "initialView");

    }

    public void selectProfile(ProfileType profileType, String ip) {
        mainController.profileType = profileType;
        if (profileType == ProfileType.SERVER) {
            System.out.println("SERVER");
            mainScreen.setTitle("Othello - Servidor");
            communication.startServer();


        } else if (profileType == ProfileType.CLIENT) {
            System.out.println("CLIENT");
            mainScreen.setTitle("Othello - Cliente");
            communication.startClient(ip);
        }
        mainController.gameController.startGame(profileType);

    }
}
