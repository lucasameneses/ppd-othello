package org.example.Controller;

public class Protocol {

    MainController mainController;

    public Protocol(MainController mainController) {
        this.mainController = mainController;
    }

    public void protocol(String message) {
        System.out.println("Protocolo: " + message);
        String action = message.split("://")[0];
        String data = message.split("://")[1];

        switch (action) {
            case "chat":
                mainController.gameController.receiveMessage(data);
                break;
            case "game":
                mainController.gameController.receiveMove(data);
                break;
            case "result":
                mainController.gameController.receiveResult(data);
                break;
            default:
                break;
        }

    }


}
