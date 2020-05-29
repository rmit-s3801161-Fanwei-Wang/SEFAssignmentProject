package models;

import java.util.HashMap;

public class Board{
//	private static int grids = 10; // columns and lines
//	private int gridsize = 20; // size

	private HashMap<Position,Entity> collections = new HashMap<Position,Entity>();

	public Board() {
		
	}

	public Board(HashMap<Position,Entity> collections) {
		this.collections = collections;
	}

//	public static void setBoardSize(int grid) {
//		grids = grid;
//	}

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
	
	public void viewBoard() {
		System.out.println();
		for(int i = 9 ; i >= 0 ; i--) {
			for(int j = 0 ; j < 10 ; j++) {
				boolean okay = true;
				for(Position p:collections.keySet()) {
					if(p.compareTo(new Position(j,i))) {
						System.out.print(collections.get(p).getName());
						okay = false;
					}	
				}
				if(okay)
					System.out.print("-");
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println();
	}

}
