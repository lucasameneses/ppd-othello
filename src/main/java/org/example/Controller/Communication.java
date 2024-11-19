package org.example.Controller;

import java.io.*;
import java.net.*;

public class Communication {

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private BufferedReader input;
    private PrintWriter output;
    public Protocol protocol;

    public Communication(MainController mainController) {
        this.protocol = mainController.protocol;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(9876);
            System.out.println("Servidor iniciado. Aguardando conexão...");

            clientSocket = serverSocket.accept();
            System.out.println("Conexão estabelecida com o cliente.");

            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);

            new Thread(() -> {
                try {
                    while (true) {
                        String message = input.readLine();
                        if (message == null) {
                            break;
                        }
                        this.protocol.protocol(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startClient(String host) {
        try {
            clientSocket = new Socket(host, 9876);
            System.out.println("Conectado ao servidor.");

            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintWriter(clientSocket.getOutputStream(), true);

            new Thread(() -> {
                try {
                    while (true) {
                        String message = input.readLine();
                        if (message == null) {
                            break;
                        }
                        this.protocol.protocol(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void sendMessage(String message) {
        output.println(message);
    }

}
