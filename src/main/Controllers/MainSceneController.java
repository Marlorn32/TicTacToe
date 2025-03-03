package main.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import main.Enums.Gamepieces;
import main.Enums.Gamestates;
import main.Interfaces.Agent;
import main.Interfaces.GameBoard;
import main.Interfaces.Tile;
import main.Models.TTTAgent;
import main.Models.TTTAgentRandom;
import main.Models.TTTGameboard;

import java.util.Arrays;
import java.util.stream.Stream;

public class MainSceneController {
    @FXML
    private GridPane grid;

    private Button[][] buttons = new Button[3][3];

    @FXML
    private void initialize() {
        GameBoard gameBoard = new TTTGameboard(3);
        Agent agent = new TTTAgent();

        Button Victory = new Button("Win");
        Victory.setVisible(false);
        Victory.setMinSize(100, 100);
        grid.add(Victory, 0, 4);

        Button Defeat = new Button("Lose");
        Defeat.setVisible(false);
        Defeat.setMinSize(100, 100);
        grid.add(Defeat, 2, 4);


        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Button button = new Button();
                button.setMinSize(100, 100);

                int finalRow = row;
                int finalCol = col;
                button.setOnAction(event ->
                        {
                            if (gameBoard.getGameState() == Gamestates.Ongoing) {
                                if (gameBoard.updateBoardState(Gamepieces.X, finalRow, finalCol)) {
                                    button.setText("X"); // Change button text when clicked
                                    System.out.println(gameBoard.getGameState());
                                    if (gameBoard.getGameState() == Gamestates.Victory) {
                                        Victory.setVisible(true);
                                    }
                                    if (gameBoard.getTurn() < 9) {
                                        if (gameBoard.getGameState() == Gamestates.Ongoing) {
                                            Tile t = agent.playMove(gameBoard);
                                            buttons[t.getX()][t.getY()].setText("O");
                                            System.out.println(gameBoard.getGameState());
                                            if (gameBoard.getGameState() == Gamestates.Defeat) {
                                                Defeat.setVisible(true);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // handleButtonClick(button, gameBoard, agent)
                );
                grid.add(button, col, row);
                buttons[row][col] = button; // Store reference in array
            }
        }

        Button reset = new Button("Reset");
        reset.setMinSize(100, 100);
        reset.setOnAction(e -> {
                    gameBoard.resetBoard();

                    Stream<Button> buttonStream = Arrays.stream(buttons)
                            .flatMap(Arrays::stream);

                    buttonStream.forEach(button -> {
                        button.setText(" ");
                    });

                    Defeat.setVisible(false);
                    Victory.setVisible(false);
                }
        );

        grid.add(reset, 1, 4);
    }

    @FXML
    private void handleButtonClick(Button button, GameBoard gameBoard, Agent agent) {
        // Game logic


    }
}
