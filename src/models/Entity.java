package models;

import java.awt.Graphics;
//import javafx.beans.property.*;

abstract public class Entity {
	private Position entry;
	private Position exit;
	public Entity(Position entry, Position exit) {
		this.entry = entry;
		this.exit = exit;
	}
	
	abstract public void draw(Graphics g);
	
	public void adajustPosition(Piece piece) {
		if (piece.getPosition() != null)
			if (piece.getPosition().compareTo(entry)) {
				piece.getPosition().setXY(exit);
//				System.out.println(piece.getName() + " is eaten by a snake to " + exit.positionToInt());
				piece.setBuff();
			}
	}

	
}
