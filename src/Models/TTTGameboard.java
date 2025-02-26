package Models;

import Enums.Gamepieces;
import Enums.Gamestates;
import Interfaces.GameBoard;
import Interfaces.Tile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class TTTGameboard implements GameBoard {

    private Gamestates gamestate;
    private final Tile[][] tiles;
    private final int size;
    private int turn;
    private List<Tile> unchosenTiles;

    public TTTGameboard(int n) {
        size = n;
        gamestate = Gamestates.Ongoing;
        unchosenTiles = new ArrayList<>(size*size);
        turn = 0;

        // tiles
        tiles = new TTTTile[n][n];
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                TTTTile temp = new TTTTile(x,y);
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
                unchosenTiles.add(temp);
            }
        }
    }

    public Gamestates getGameState() {
        return gamestate;
    }

    public void updateGameState() {
        int outcome = win();
        switch (outcome) {
            case -1:
                gamestate = Gamestates.Ongoing;
                if (turn >= 8) {
                    gamestate = Gamestates.Error;
                }
                break;
            case -2:
                gamestate = Gamestates.Error;
                break;
            case 0:
                gamestate = Gamestates.Defeat;
                break;
            case 1:
                gamestate = Gamestates.Victory;
                break;
            default:
                // Optionally handle an unexpected outcome (though this should not happen if outcome is -1, -2, 0, or 1)
                gamestate = Gamestates.Error;
                break;
        }
    }

    public Tile[][] getBoardState() {
        return tiles;
    }

    // places the piece on a square
    public boolean updateBoardState(Gamepieces piece, int x, int y) {
        if (x > size-1 || x < 0){
            throw new RuntimeException("X with value: "+ y +", is out of bounds for size: " + size);
        }
        if (y > size-1 || y < 0){
            throw new RuntimeException("Y with value: "+ y +", is out of bounds for size: " + size);
        }
        if (tiles[y][x].isOccupied()){return false;}
        tiles[y][x].changePiece(piece);
        updateGameState();
        turn++;
        unchosenTiles.removeIf(z -> z.getX() == x && z.getY() == y);
        return true;
    }

    public int getSize() {
        return size;
    }

    public void resetBoard() {
        Stream<Tile> tileStream = Arrays.stream(tiles).flatMap(Arrays::stream);
        tileStream.forEach(Tile::reset);
        gamestate = Gamestates.Ongoing;
        turn = 0;
    }

    public int getTurn() {
        return turn;
    }

    public List<Tile> getUnchosenTiles() {
        return unchosenTiles;
    }

    // returns 0 if o wins, returns 1 if x wins, returns -1 if no one wins, returns -2 if both win
    private int win() {
        boolean x_wins = false;
        boolean o_wins = false;

        // upper corner
        Tile u = tiles[0][0];
        if (u.isOccupied()) {
            if (isSameTokensLine(u,2,2) ||
            isSameTokensLine(u, 2,1) ||
            isSameTokensLine(u, 1,2)) {
                if (u.getPiece() == Gamepieces.X) {
                    x_wins = true;
                } else if (u.getPiece() == Gamepieces.O) {
                    o_wins = true;
                }
            }
        }

        // bottom corner / diagonal
        Tile b = tiles[2][0];
        if (b.isOccupied()) {
            if (isSameTokensLine(b,2,0)) {
                if (b.getPiece() == Gamepieces.X) {
                    x_wins = true;
                } else if (b.getPiece() == Gamepieces.O) {
                    o_wins = true;
                }
            }
        }

        for (int x = 0; x < size; x++) {
            Tile t = tiles[0][x];
            if (t.isOccupied()) {
                if (isSameTokensLine(t, 1,2)) {
                    if (t.getPiece() == Gamepieces.X) {
                        x_wins = true;
                    } else if (t.getPiece() == Gamepieces.O) {
                        o_wins = true;
                    }
                }
            }
        }

        for (int y = 0; y < size; y++) {
            Tile t = tiles[y][0];
            if (t.isOccupied()) {
                if (isSameTokensLine(t, 2, 1)) {
                    if (t.getPiece() == Gamepieces.X) {
                        x_wins = true;
                    } else if (t.getPiece() == Gamepieces.O) {
                        o_wins = true;
                    }
                }
            }
        }

        if (x_wins && o_wins) {
            return -2;
        }
        if (o_wins) {
            return 0;
        }
        if (x_wins) {
            return 1;
        }
        return -1;
    }

    // takes a tile and a direction and confirms if all of the tiles have the same token on that diagonal
    private boolean isSameTokensLine(Tile t, int x, int y) {
        if (!t.isOccupied()) return false;

        Gamepieces piece = t.getPiece();
        Tile start = t;  // Store original tile

        int counter = 1;

        // First direction
        while (t.getNeighbor(x, y) != null) {
            Tile n = t.getNeighbor(x, y);
            if (n.getPiece() != piece) return false;  // Break immediately if mismatch
            t = n;
            counter++;
        }

        // Reset tile to original
        t = start;

        // Opposite direction
        int x_ = ((x - 1) * -1) + 1;
        int y_ = ((y - 1) * -1) + 1;

        while (t.getNeighbor(x_, y_) != null) {
            Tile n = t.getNeighbor(x_, y_);
            if (n.getPiece() != piece) return false;
            t = n;
            counter++;
        }

        return counter == size;  // Must exactly match board size
    }
}
