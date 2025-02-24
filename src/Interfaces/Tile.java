package Interfaces;

import Enums.Gamepieces;

public interface Tile {
    void addNeighbor(Tile target, int x, int y);
    Tile[][] getNeighborArray();
    Tile getNeighbor(int x, int y);
    boolean isOccupied();
    Gamepieces getPiece();
    void changePiece(Gamepieces newPiece);
    void reset();
    int getX();
    int getY();
}
