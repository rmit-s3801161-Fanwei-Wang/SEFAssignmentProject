package models;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
//	private static int grids = 10; // columns and lines
//	private int gridsize = 20; // size

	private Ladder[] ladders = new Ladder[5];
	private Snake[] snakes = new Snake[5];
	private Guard[] guards = new Guard[3];
	private ArrayList<Piece> pieces = new ArrayList<Piece>();
	private HashMap<Position,Object> collections = new HashMap<Position,Object>();

	public Board() {
	}

	public Board(Ladder[] ladders, Snake[] snakes) {
		this.ladders = ladders;
		this.snakes = snakes;
		for(int i=0;i<ladders.length;i++) {
			collections.put(ladders[i].getBottom(), ladders[i]);
			collections.put(ladders[i].getTop(), ladders[i]);
		}
		for(int i=0;i<snakes.length;i++) {
			collections.put(snakes[i].getHead(), snakes[i]);
			collections.put(snakes[i].getTail(), snakes[i]);
		}
	}

//	public static void setBoardSize(int grid) {
//		grids = grid;
//	}

	public Ladder[] getLadders() {
		return ladders;
	}

	public Snake[] getSnakes() {
		return snakes;
	}

	public Guard[] getGuards() {
		return guards;
	}

	public ArrayList<Piece> getPieces() {
		return pieces;
	}

	public void addCollection(Object obj) {
		if(obj instanceof Piece) {
			if(((Piece)obj).getPosition()!=null)
				collections.put(((Piece)obj).getPosition(), obj);
		}
		else if(obj instanceof Guard) {
			if(((Guard)obj).getPosition()!=null)
				collections.put(((Guard)obj).getPosition(), obj);
		}
		else if(obj instanceof Snake) {
			collections.put(((Snake) obj).getHead(),obj);
			collections.put(((Snake) obj).getTail(),obj);
		}
		else {
			collections.put(((Ladder) obj).getBottom(),obj);
			collections.put(((Ladder) obj).getTop(),obj);
		}
	}
	
	public HashMap<Position, Object> getCollections() {
		return collections;
	}

}
