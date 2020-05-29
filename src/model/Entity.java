package models;

import java.awt.Graphics;
//import javafx.beans.property.*;

abstract public class Entity {
	private Position entry;
	private Position exit;
	private String name;

	public Entity(Position entry, Position exit, String name) {
		this.entry = entry;
		this.exit = exit;
		this.name = name;
	}

	public Position getEntry() {
		return entry;
	}

	public void setEntry(Position entry) {
		this.entry = entry;
	}

	public Position getExit() {
		return exit;
	}

	public void setExit(Position exit) {
		this.exit = exit;
	}

	public String getName() {
		return name;
	}

	abstract public void draw(Graphics g);

	public boolean adjustPosition(Piece piece) {
		if (this.getExit() == null || this.getEntry() == null)
			return false;
		if (piece.getEntry() != null)
			if (piece.getEntry().compareTo(entry)) {
				piece.getEntry().setXY(exit);
				return true;
			}
		return false;
	}

}
