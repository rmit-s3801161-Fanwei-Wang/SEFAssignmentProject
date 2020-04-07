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
	private HashMap<Position,Entity> collections = new HashMap<Position,Entity>();

	public Board() {
	}

	public Board(Ladder[] ladders, Snake[] snakes) {
		this.ladders = ladders;
		this.snakes = snakes;
		for(int i=0;i<ladders.length;i++) {
			collections.put(ladders[i].getEntry(), ladders[i]);
			collections.put(ladders[i].getExit(), ladders[i]);
		}
		for(int i=0;i<snakes.length;i++) {
			collections.put(snakes[i].getEntry(), snakes[i]);
			collections.put(snakes[i].getExit(), snakes[i]);
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

	public void addCollection(Entity obj) {
		if(obj instanceof Piece) {
			if(((Piece)obj).getEntry()!=null)
				collections.put(((Piece)obj).getEntry(), obj);
		}
		else if(obj instanceof Guard) {
			if(((Guard)obj).getExit()!=null)
				collections.put(((Guard)obj).getExit(), obj);
		}
		else if(obj instanceof Snake) {
			collections.put(((Snake) obj).getEntry(),obj);
			collections.put(((Snake) obj).getExit(),obj);
		}
		else {
			collections.put(((Ladder) obj).getEntry(),obj);
			collections.put(((Ladder) obj).getExit(),obj);
		}
	}
	
	public HashMap<Position, Entity> getCollections() {
		return collections;
	}

}
