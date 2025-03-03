package main.Interfaces;

import main.Enums.Gamepieces;
import main.Enums.Gamestates;

import java.util.List;

public interface GameBoard {
    Gamestates getGameState();
    void updateGameState();
    Tile[][] getBoardState();
    boolean updateBoardState(Gamepieces piece, int x, int y);
    int getSize();
    void resetBoard();
    int getTurn();
    List<Tile> getUnchosenTiles();
}
