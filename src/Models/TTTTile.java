package Models;

import Enums.Gamepieces;
import Interfaces.Tile;

public class TTTTile implements Tile {
    private Boolean occupied;
    private Tile[][] neighbors;
    private Gamepieces piece;
    private final int x;
    private final int y;

    public TTTTile(int x, int y){
        occupied = false;
        piece = Gamepieces.E;
        neighbors = new TTTTile[3][3];
        neighbors[1][1] = this;
        this.x = x;
        this.y = y;
    }

    // adds a neighbor if available, if null nothing happens
    // symmetrical relationship
    // centered at 0,0 shifted by -1,-1
    // x and y come in at 0,0 to 2,2
    // only currently supports directly adjacent neighbors
    public void addNeighbor(Tile target, int x, int y){
        if (neighbors[y][x] == null){
            neighbors[y][x] = target;
            int x_ = ((x-1)*-1)+1;
            int y_ = ((y-1)*-1)+1;
            target.addNeighbor(this, x_, y_);
        }
    }

    public void changePiece(Gamepieces newPiece){
        piece = newPiece;
        occupied = true;
    }

    public void reset() {
        occupied = false;
        piece = Gamepieces.E;
        neighbors = new TTTTile[3][3];
        neighbors[1][1] = this;
    }

    public Gamepieces getPiece(){
        return piece;
    }

    public Tile[][] getNeighborArray() {
        return neighbors;
    }

    public Tile getNeighbor(int x, int y) {
        return neighbors[y][x];
    }

    public boolean isOccupied() {
        return occupied;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
