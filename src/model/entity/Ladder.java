package model.entity;

import java.awt.Graphics;

import model.exception.InitializeException;

public class Ladder extends SLEntity {

	public Ladder(Position bottom, Position top, String name) throws InitializeException {
		super(bottom, top, name);
		InitializeException ex = new InitializeException(
				("Bottom:" + bottom.positionToInt() + " ,Top:" + top.positionToInt() + " is not possible"));
		if (super.getExit().positionToInt() - super.getEntry().positionToInt() > 30
				|| super.getEntry().positionToInt() > super.getExit().positionToInt()
				|| super.getExit().positionToInt() == 100
				|| super.getEntry().getY() == super.getExit().getY()) {
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

	@Override
	public String toDbString() {
		return String.format("{\"Type\":\"Ladder\",\"Name\":\"%s\",\"TopX\":%d,\"TopY\":%d,\"BotX\":%d,\"BotY\":%d}", super.getName(),
				super.getExit().getX(),super.getExit().getY(),super.getEntry().getX(),super.getEntry().getY());
	}

}
