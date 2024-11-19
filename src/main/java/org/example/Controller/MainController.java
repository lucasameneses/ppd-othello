package org.example.Controller;

import org.example.Model.ProfileType;
import org.example.View.MainView;

public class MainController {

    public MainView mainView;
    public Protocol protocol;

    public Communication communication;

    public ProfileController profileController;
    public GameController gameController;

    public ProfileType profileType;

    public MainController() {
        this.mainView = new MainView();

        this.protocol = new Protocol(this);
        this.communication = new Communication(this);

        this.profileController = new ProfileController(this);
        this.gameController = new GameController(this);


    }


}
