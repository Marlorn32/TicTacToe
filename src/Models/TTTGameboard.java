package Models;

public class TTTGameboard implements GameBoard {

    private Gamestates gamestate;
    private TTTTile[][] tiles;
    private int size;

    public TTTGameboard(int n) {
        size = n;
        gamestate = Gamestates.Ongoing;

        // tiles
        tiles = new TTTTile[n][n];
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles.length; x++) {
                TTTTile temp = new TTTTile();
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

    @Override
    public Gamestates getGameState() {
        return gamestate;
    }

    @Override
    public void updateGameState() {
        int outcome = win();
        if (outcome == -1) {
            gamestate = Gamestates.Ongoing;
        }
        if (outcome == -2) {
            gamestate = Gamestates.Error;
        }
        if (outcome == 0) {
            gamestate = Gamestates.Defeat;
        }
        if (outcome == 1) {
            gamestate = Gamestates.Victory;
        }
    }

    @Override
    public TTTTile[][] getBoardState() {
        return tiles;
    }

    // places the piece on a square
    @Override
    public void updateBoardState(Gamepieces piece, int x, int y) {
        if (x > size-1 || x < 0){
            throw new RuntimeException("X with value: "+ y +", is out of bounds for size: " + size);
        }
        if (y > size-1 || y < 0){
            throw new RuntimeException("Y with value: "+ y +", is out of bounds for size: " + size);
        }
        tiles[y][x].changePiece(piece);
        updateGameState();
    }

    @Override
    public int getSize() {
        return size;
    }

    // returns 0 if o wins, returns 1 if x wins, returns -1 if no one wins, returns -2 if both win
    private int win() {
        boolean x_wins = false;
        boolean o_wins = false;

        // upper corner
        TTTTile u = tiles[0][0];
        if (u.getOccupied()) {
            if (isSameTokensDiagonal(u,2,2) ||
            isSameTokensDiagonal(u, 2,1) ||
            isSameTokensDiagonal(u, 1,2)) {
                if (u.getPiece() == Gamepieces.X) {
                    x_wins = true;
                } else if (u.getPiece() == Gamepieces.O) {
                    o_wins = true;
                }
            }
        }

        // bottom corner / diagonal
        TTTTile b = tiles[2][0];
        if (b.getOccupied()) {
            if (isSameTokensDiagonal(b,2,0)) {
                if (b.getPiece() == Gamepieces.X) {
                    x_wins = true;
                } else if (b.getPiece() == Gamepieces.O) {
                    o_wins = true;
                }
            }
        }

        for (int x = 0; x < size; x++) {
            TTTTile t = tiles[0][x];
            if (t.getOccupied()) {
                if (isSameTokensDiagonal(t, 1,2)) {
                    if (t.getPiece() == Gamepieces.X) {
                        x_wins = true;
                    } else if (t.getPiece() == Gamepieces.O) {
                        o_wins = true;
                    }
                }
            }
        }

        for (int y = 0; y < size; y++) {
            TTTTile t = tiles[y][0];
            if (t.getOccupied()) {
                if (isSameTokensDiagonal(t, 2, 1)) {
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
    private boolean isSameTokensDiagonal(TTTTile t, int x, int y) {
        boolean result = true;
        int counter = 1;
        while (t.getNeighbor(x, y) != null) {
            TTTTile n = t.getNeighbor(x, y);
            if (n.getPiece() != t.getPiece()) {
                result = false;
                break;
            }
            t = n;
            counter++;
        }
        ////  gets the inverse value
        int x_ = ((x-1)*-1)+1;
        int y_ = ((y-1)*-1)+1;
        while (t.getNeighbor(x_, y_) != null) {
            TTTTile n = t.getNeighbor(x_, y_);
            if (n.getPiece() != t.getPiece()) {
                result = false;
                break;
            }
            t = n;
            counter++;
        }

        if (counter != size){result = false;}
        return result;
    }
}
