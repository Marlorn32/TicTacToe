package main.Models;

import main.Enums.Gamepieces;
import main.Enums.Gamestates;
import main.Interfaces.Agent;
import main.Interfaces.GameBoard;
import main.Interfaces.Tile;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

// should play optimally on a 3x3 board

public class TTTAgent implements Agent {
    @Override
    public Tile playMove(GameBoard board) {
        Agent random = new TTTAgentRandom();
        Tile ans = null;

        // First find win in 1
        ans = board.getUnchosenTiles().stream()
                .filter(tile -> isWin(board, tile))
                .findFirst()
                .or(() -> board.getUnchosenTiles().stream()
                        .filter(tile -> isLoss(board, tile))
                        .findFirst())
                .or(() -> board.getUnchosenTiles().stream()
                        .filter(this::isCenter)
                        .findFirst())
                .or(() -> board.getUnchosenTiles().stream()
                        .filter(this::isCorner)
                        .findFirst())
                .orElseGet(() -> {
                    return new TTTAgentRandom().playMove(board);
                });

        // Don't play on the sides, always center or corners

        board.updateBoardState(Gamepieces.O, ans.getX(), ans.getY());
        return ans;
    }

    private boolean isWin(GameBoard gameBoard, Tile tile) {
        TTTGameboard test = new TTTGameboard((TTTGameboard) gameBoard);
        test.updateBoardState(Gamepieces.O, tile.getX(), tile.getY());
        return (test.getGameState() == Gamestates.Defeat);
    }

    private boolean isLoss(GameBoard gameBoard, Tile tile) {
        TTTGameboard test = new TTTGameboard((TTTGameboard) gameBoard);
        test.updateBoardState(Gamepieces.X, tile.getX(), tile.getY());
        return (test.getGameState() == Gamestates.Victory);
    }

    private boolean isCenter(Tile tile){
        return (tile.getX() == 1 && tile.getY() == 1);
    }

    private boolean isCorner(Tile tile){
        return (tile.getX() % 2 == 0 && tile.getY() % 2 == 0);
    }
}
