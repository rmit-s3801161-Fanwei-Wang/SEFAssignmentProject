package models;

import java.awt.Graphics;

import exception.InitializeException;

public class Ladder extends Entity {

	public Ladder(Position bottom, Position top, String name) throws InitializeException {
		super(bottom, top, name);
		InitializeException ex = new InitializeException(
				("Bottom:" + bottom.positionToInt() + " ,Top:" + top.positionToInt() + " is not possible"));
		if (super.getExit().positionToInt() - super.getEntry().positionToInt() > 30
				|| super.getEntry().positionToInt() > super.getExit().positionToInt() || super.getExit().positionToInt() != 100) {
			throw ex;
		}
	}

	@Override
	public boolean adjustPosition(Piece piece) {
		if (super.adjustPosition(piece)) {
			System.out.println(
					piece.getName() + " climb " + super.getName() + " to " + super.getExit().positionToInt());
			return true;
		}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}

}
