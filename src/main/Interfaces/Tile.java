package main.Interfaces;

import main.Enums.Gamepieces;

public interface Tile {
    void addNeighbor(Tile target, int x, int y);
    Tile[][] getNeighborArray();
    Tile getNeighbor(int x, int y);
    boolean isOccupied();
    Gamepieces getPiece();
    // no side effects, for initialization of copies
    // side effect of changing occupied, for agents
    void playPiece(Gamepieces newPiece);
    void reset();
    int getX();
    int getY();
}
