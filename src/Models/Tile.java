package Models;

public interface Tile {
    void addNeighbor(Tile target, int x, int y);
    Tile[][] getNeighborArray();
    Tile getNeighbor(int x, int y);
    boolean isOccupied();
    Gamepieces getPiece();
    void changePiece(Gamepieces newPiece);
    void reset();
}
