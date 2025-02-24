package Models;

public interface GameBoard {
    // gets and updates game state
    Gamestates getGameState();
    void updateGameState();
    // gets current board state
    Tile[][] getBoardState();
    void updateBoardState(Gamepieces piece, int x, int y);
    int getSize();
    void resetBoard();
}
