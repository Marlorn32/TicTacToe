package Interfaces;

import Enums.Gamepieces;
import Enums.Gamestates;

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
