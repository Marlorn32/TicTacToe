package Testing;

import Enums.Gamepieces;
import Enums.Gamestates;
import Interfaces.GameBoard;
import Interfaces.Tile;
import Models.*;

import java.util.function.Supplier;

public class TTTTesting implements Testing {
    private final GameBoard gameboard;

    public TTTTesting() {
        gameboard = new TTTGameboard(3);
    }

    @Override
    public int runTests() {
        tileUnitTests();
        //adjacencyTests();
        //boardTests();
        return 1;
    }

    private void adjacencyTests(){
        System.out.println("X variable Y = 1:");
        System.out.println("0, 1:");
        printAdjacency(0, 1);
        System.out.println("1, 1:");
        printAdjacency(1, 1);
        System.out.println("2, 1:");
        printAdjacency(2, 1);

        System.out.println("Y variable X = 1:");
        printAdjacency(1, 0);
        printAdjacency(1, 1);
        printAdjacency(1, 2);

        System.out.println("Y variable X = 0:");
        printAdjacency(0, 0);
        printAdjacency(0, 1);
        printAdjacency(0, 2);
    }

    private void tileUnitTests(){
        unitTest(gameboard::getBoardState, null);
        unitTest(gameboard::getGameState, Gamestates.Ongoing);

        Tile t = gameboard.getBoardState()[0][0];
        gameboard.updateBoardState(Gamepieces.X, 0,1);
        System.out.println(t.getNeighbor(2,1).getPiece());
    }

    private void boardTests(){
        System.out.println("Initial Boardstate:");
        showBoard();
        System.out.println(gameboard.getGameState());

        System.out.println("Changing the board");
        gameboard.updateBoardState(Gamepieces.X, 1, 1);
        showBoard();
        System.out.println(gameboard.getGameState());

        System.out.println("Out of bounds test: ");
        try {
            gameboard.updateBoardState(Gamepieces.O, -2, 0);
        }catch (Exception e){
            System.out.println("Exception caught");
        }

        gameboard.updateBoardState(Gamepieces.X, 2,0);
        System.out.println(gameboard.getGameState());
        showBoard();

        gameboard.updateBoardState(Gamepieces.X, 0,2);
        System.out.println(gameboard.getGameState());
        showBoard();

        gameboard.updateBoardState(Gamepieces.O, 2,2);
        System.out.println(gameboard.getGameState());
        showBoard();

        gameboard.updateBoardState(Gamepieces.O, 1,1);
        System.out.println(gameboard.getGameState());
        showBoard();

        gameboard.updateBoardState(Gamepieces.O, 0,0);
        System.out.println(gameboard.getGameState());
        showBoard();
    }

    @Override
    public void showBoard() {
        Tile[][] board = gameboard.getBoardState();
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < gameboard.getSize(); i++) {
            for (int j = 0; j < gameboard.getSize(); j++) {
                output.append(board[i][j].getPiece().toString());
                if (j != gameboard.getSize() - 1) {
                    output.append(", ");
                }
            }
            output.append("\n");
        }
        System.out.println(output);
    }

    private void printAdjacency(int x, int y){
        Tile[][] t = gameboard.getBoardState();
        Tile tile = t[y][x];
        Gamepieces g = tile.getPiece();
        tile.changePiece(Gamepieces.O);
        Tile[][] target = tile.getNeighborArray();
        StringBuilder output = new StringBuilder();

        for (Tile[] tttTiles : target) {
            for (int x_ = 0; x_ < target.length; x_++) {
                if (tttTiles[x_] != null) {
                    output.append(tttTiles[x_].getPiece().toString());
                } else {
                    output.append("-");
                }
                if (x_ != target.length - 1) {
                    output.append(", ");
                }
            }
            output.append("\n");
        }
        System.out.println(output);
        tile.changePiece(g);
    }

    public boolean unitTest(Supplier<Object> testing, Object expected) {
        Object result = testing.get();
        System.out.println("Results of test: " + result);

        // handle null exceptions
        if (result == null) {
            return expected == null;
        }

        return !result.equals(expected);
    }

}
