package models;

import java.awt.Graphics;

import exception.InitializeException;

public class Ladder extends Entity{
	private Position bottom;
	private Position top;

	public Ladder(Position bottom, Position top) throws InitializeException {
		super(bottom,top);
		this.bottom = bottom;
		this.top = top;
		InitializeException ex = new InitializeException(("Bottom:" + bottom.positionToInt() + " ,Top:"+ top.positionToInt() + " is not possible"));
		if(this.top.positionToInt() - this.bottom.positionToInt() > 30 || this.bottom.positionToInt() > this.top.positionToInt()) {
			throw ex;
		}
	}

	public Position getBottom() {
		return bottom;
	}

	public Position getTop() {
		return top;
	}

//	public boolean climb(Piece piece) {
//		if (piece.getPosition().compareTo(bottom)) {
//			piece.getPosition().setXY(top);
//			System.out.println(piece.getName() + " climb ladder to " + top.positionToInt());
//			return true;
//		}
//		return false;
//	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}

}
