package Models;

public interface GameBoard {
    // gets and updates game state
    public Gamestates getGameState();
    public void updateGameState();
    // gets current board state
    public Object getBoardState();
    public void updateBoardState(Gamepieces piece, int x, int y);
    public int getSize();
}
