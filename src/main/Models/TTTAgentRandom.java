package main.Models;

import main.Enums.Gamepieces;
import main.Interfaces.Agent;
import main.Interfaces.GameBoard;
import main.Interfaces.Tile;

import java.util.List;
import java.util.Random;

public class TTTAgentRandom implements Agent {
    public Tile playMove(GameBoard board) {
        Random r = new Random();
        List<Tile> tiles = board.getUnchosenTiles();
        int i = r.nextInt((board.getSize()*board.getSize())-board.getTurn());
        Tile t = tiles.get(i);
        board.updateBoardState(Gamepieces.O, t.getX(),t.getY());
        return t;
    }
}
