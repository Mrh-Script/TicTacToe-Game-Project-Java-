package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class TicTacToe {
    JFrame frame;
    JLabel titleLabel, turnLabel;
    JPanel topPanel, boardPanel, controlPanel;
    JButton[][] board;
    JButton restartButton;

    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turns = 0;

    public TicTacToe() {
        frame = new JFrame("Tic-Tac-Toe");
        frame.setSize(600, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Top Panel (Title + Turn Label)
        topPanel = new JPanel(new GridLayout(2, 1));
        titleLabel = new JLabel("Tic-Tac-Toe", JLabel.CENTER);
        titleLabel.setOpaque(true);
        titleLabel.setBackground(Color.darkGray);
        titleLabel.setForeground(Color.white);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 40));

        turnLabel = new JLabel(currentPlayer + "'s turn", JLabel.CENTER);
        turnLabel.setOpaque(true);
        turnLabel.setBackground(Color.gray);
        turnLabel.setForeground(Color.white);
        turnLabel.setFont(new Font("Arial", Font.PLAIN, 30));

        topPanel.add(titleLabel);
        topPanel.add(turnLabel);
        frame.add(topPanel, BorderLayout.NORTH);

        // Board Panel
        boardPanel = new JPanel(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        board = new JButton[3][3];

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c] = new JButton();
                board[r][c].setFont(new Font("Arial", Font.BOLD, 120));
                board[r][c].setBackground(Color.darkGray);
                board[r][c].setForeground(Color.white);
                board[r][c].setFocusable(false);
                board[r][c].addActionListener(new TileClickHandler());
                boardPanel.add(board[r][c]);
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER);

        // Control Panel (Restart Button)
        controlPanel = new JPanel(new FlowLayout());
        restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Arial", Font.BOLD, 20));
        restartButton.addActionListener(new RestartHandler());
        controlPanel.add(restartButton);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    class TileClickHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameOver) return;

            JButton clicked = (JButton) e.getSource();
            if (clicked.getText().equals("")) {
                clicked.setText(currentPlayer);
                turns++;
                checkWinner();
                if (!gameOver) {
                    currentPlayer = currentPlayer.equals(playerX) ? playerO : playerX;
                    turnLabel.setText(currentPlayer + "'s turn");
                }
            }
        }
    }

    class RestartHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            resetGame();
        }
    }

    void checkWinner() {
        //Check Row
        for (int r = 0; r < 3; r++) {
            if (!board[r][0].getText().equals("") &&
                    board[r][0].getText().equals(board[r][1].getText()) &&
                    board[r][1].getText().equals(board[r][2].getText())) {
                highlightWinner(board[r][0], board[r][1], board[r][2]);
                return;
            }
        }

        //Check Column
        for (int c = 0; c < 3; c++) {
            if (!board[0][c].getText().equals("") &&
                    board[0][c].getText().equals(board[1][c].getText()) &&
                    board[1][c].getText().equals(board[2][c].getText())) {
                highlightWinner(board[0][c], board[1][c], board[2][c]);
                return;
            }
        }

        //Check Diagonal
        if (!board[0][0].getText().equals("") &&
                board[0][0].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][2].getText())) {
            highlightWinner(board[0][0], board[1][1], board[2][2]);
            return;
        }

        //Check Anti-Diagonal
        if (!board[0][2].getText().equals("") &&
                board[0][2].getText().equals(board[1][1].getText()) &&
                board[1][1].getText().equals(board[2][0].getText())) {
            highlightWinner(board[0][2], board[1][1], board[2][0]);
            return;
        }

        //Check Tie or Not
        if (turns == 9) {
            for (int r = 0; r < 3; r++)
                for (int c = 0; c < 3; c++)
                    setTie(board[r][c]);
            gameOver = true;
            turnLabel.setText("Tie!");
        }
    }

    void highlightWinner(JButton t1, JButton t2, JButton t3) {
        t1.setForeground(Color.green);
        t2.setForeground(Color.green);
        t3.setForeground(Color.green);
        t1.setBackground(Color.gray);
        t2.setBackground(Color.gray);
        t3.setBackground(Color.gray);
        gameOver = true;
        turnLabel.setText(currentPlayer + " is the winner!");
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
    }

    void resetGame() {
        gameOver = false;
        turns = 0;
        currentPlayer = playerX;
        turnLabel.setText(currentPlayer + "'s turn");

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                board[r][c].setForeground(Color.white);
                board[r][c].setBackground(Color.darkGray);
            }
        }
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}