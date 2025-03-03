package main.Models;

import main.Interfaces.Tile;

public class TTTTileArray {
    private Tile[][] tiles;

    public TTTTileArray(int n) {
        tiles = new TTTTile[n][n];
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                TTTTile temp = new TTTTile(x, y);
                // Previous
                if (x > 0) {
                    temp.addNeighbor(tiles[y][x - 1], 0, 1);
                }
                // Top row
                if (y > 0 && x > 0) {
                    temp.addNeighbor(tiles[y - 1][x - 1], 0, 0);
                }
                if (y > 0) {
                    temp.addNeighbor(tiles[y - 1][x], 1, 0);
                }
                if (y > 0 && x < tiles.length - 1) {
                    temp.addNeighbor(tiles[y - 1][x + 1], 2, 0);
                }
                tiles[y][x] = temp;
            }
        }
    }

    public TTTTileArray(){
        new TTTTileArray(3);
    }

    public Tile[][] getTiles(){
        return tiles;
    }
}
