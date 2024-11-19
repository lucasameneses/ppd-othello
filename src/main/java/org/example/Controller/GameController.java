package org.example.Controller;

import org.example.Model.ProfileType;
import org.example.View.ResultView;
import org.example.View.Game.GameView;
import org.example.View.MainView;

import javax.swing.*;
import java.awt.*;

public class GameController {
    MainController mainController;
    MainView mainScreen;
    Communication communication;
    public JButton[][] pieces;

    public JTextArea chatTextArea;

    public int[][] board;

    public String result;

    public int player;

    public int opponent;

    GameView gameView;

    public JLabel[] scoreboard;


    public GameController(MainController mainController) {
        this.mainController = mainController;
        this.mainScreen = mainController.mainView;
        this.communication = mainController.communication;

        newBoard();

        chatTextArea = new JTextArea();
        pieces = setPieces();

        scoreboard = new JLabel[2];
        scoreboard[0] = new JLabel();
        scoreboard[1] = new JLabel();

        gameView = new GameView(this);
        mainScreen.mainPanel.add(gameView, "gameView");

    }

    public void sendMessage(String text) {
        chatTextArea.append("EU: " + text + "\n");
        communication.sendMessage("chat://" + text);
    }

    public void receiveMessage(String data) {
        SwingUtilities.invokeLater(() -> {
            chatTextArea.append("OPONENTE: " + data + "\n");

        });
    }

    private void showBoard(){
//        gameView.boardPanel.setVisible(true);
//        gameView.waitingPanel.setVisible(false);
        updateButtons(pieces);
    }

    private void showWaiting(){
//        gameView.boardPanel.setVisible(false);
//        gameView.waitingPanel.setVisible(true);
        disableButtons(pieces);
    }

    public void startGame(ProfileType profileType) {
        this.player = profileType.equals(ProfileType.CLIENT) ? 2 : 1;
        this.opponent = profileType.equals(ProfileType.CLIENT) ? 1 : 2;

        boolean gameStarted = profileType.equals(ProfileType.SERVER);
        if (!gameStarted) {
            showWaiting();
        } else {
            showBoard();
        }
        ((CardLayout) mainScreen.mainPanel.getLayout()).show(mainScreen.mainPanel, "gameView");
    }

    private void newBoard() {
        this.board = new int[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                    this.board[i][j] = 0;
            }
        }
        this.board[3][3] = 2;
        this.board[4][4] = 2;
        this.board[3][4] = 1;
        this.board[4][3] = 1;

    }

    private JButton[][] setPieces() {
        JButton[][] pieces = new JButton[8][8];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                pieces[i][j] = new JButton();
            }
        }
        return pieces;
    }

    public void updateButtons(JButton[][] buttons) {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (board[i][j] == 0) {
                    buttons[i][j].setBackground(Color.decode("#3e9633"));
                    buttons[i][j].setEnabled(true);
                } else if (board[i][j] == 1) {
                    buttons[i][j].setBackground(Color.black);
                    buttons[i][j].setEnabled(false);
                } else if (board[i][j] == 2) {
                    buttons[i][j].setBackground(Color.white);
                    buttons[i][j].setEnabled(false);
                }
            }
        }
        this.countPiece();
    }

    public void disableButtons(JButton[][] buttons) {
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                if (board[i][j] == 0) {
                    buttons[i][j].setBackground(Color.decode("#7d9979"));
                    buttons[i][j].setEnabled(false);
                } else if (board[i][j] == 1) {
                    buttons[i][j].setBackground(Color.black);
                    buttons[i][j].setEnabled(false);
                } else if (board[i][j] == 2) {
                    buttons[i][j].setBackground(Color.white);
                    buttons[i][j].setEnabled(false);
                }
            }
        }
        this.countPiece();

    }

    public void makeAndSendMove(String data) {
        if(checkAndMakeMove(data, this.opponent, this.player)) {
            communication.sendMessage("game://" + data);
            updateButtons(pieces);
            showWaiting();
        }
    }

    public void passAndSendMove() {
        communication.sendMessage("game://pass");
        showWaiting();
    }


    public void receiveMove(String data) {
        if(data.equals("pass")) {
          showBoard();
        }else{
            if (checkAndMakeMove(data, this.player, this.opponent)) {
                updateButtons(pieces);
                showBoard();
            }
        }
    }

    public boolean checkAndMakeMove(String data, int opponent, int player) {
        int selectedX = Integer.parseInt(data.split(",")[0]);
        int selectedY = Integer.parseInt(data.split(",")[1]);

        int[][] directions = {
            {0, 1},{1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1},{-1, 1},{-1, -1}
        };

        boolean valid = false;
        for(int[] direction : directions) {
            if(checkDirection(selectedX, selectedY, direction, opponent, player)) {
                valid = true;
            }
        }
        return valid;
    }

    private boolean checkDirection(int selectedX, int selectedY, int[] direction, int opponent, int player) {
        int directionX = selectedX + direction[0];
        int directionY = selectedY + direction[1];

        boolean foundOpponent = false;

        while(directionX >= 0 && directionX < 8 && directionY >= 0 && directionY < 8) {

            if (board[directionX][directionY] == opponent) {

                foundOpponent = true;

            }else if (board[directionX][directionY] == player && foundOpponent) {

                makeMove(selectedX, selectedY, direction, player, directionX, directionY);
              return true;
            } else {
                break;
            }
            directionX += direction[0];
            directionY += direction[1];

        }

        return false;
    }

    private void makeMove(int selectedX, int selectedY, int[] direction, int player, int x, int y) {

        while(selectedX != x || selectedY != y) {
            board[selectedX][selectedY] = player;
            selectedX += direction[0];
            selectedY += direction[1];
        }
    }

    public void sendResult(String data) {
        if (data == "loser") {
            this.result = data;
            communication.sendMessage("result://winner");
        } else if (data == "winner") {
            this.result = data;
            communication.sendMessage("result://loser");
        } else if (data == "draw") {
            this.result = data;
            communication.sendMessage("result://draw");
        }
        ResultView resultView = new ResultView(this);
        mainScreen.mainPanel.add(resultView, "resultView");
        ((CardLayout) mainScreen.mainPanel.getLayout()).show(mainScreen.mainPanel, "resultView");


    }


    public void receiveResult(String data) {
        this.result = data;

        ResultView resultView = new ResultView(this);
        mainScreen.mainPanel.add(resultView, "resultView");
        ((CardLayout) mainScreen.mainPanel.getLayout()).show(mainScreen.mainPanel, "resultView");

    }

    public void  countPiece() {
        int white = 0;
        int black = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 1) {
                    white++;
                } else if (board[i][j] == 2) {
                    black++;
                }
            }
        }

        if(white + black == 64) {
            if (player == 1 && black > white) {
                sendResult("winner");
            } else if (player == 2 && white > black) {
                sendResult("winner");
            } else if (black == white) {
                sendResult("draw");
            }
        }

        this.scoreboard[0].setText("Branco: " + white);
        this.scoreboard[1].setText("Preto: " + black);
    }
}
