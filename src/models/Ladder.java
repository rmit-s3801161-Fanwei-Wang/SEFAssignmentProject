package models;

public class Ladder {
	private Position bottom;
	private Position top;

	public Ladder(Position bottom, Position top) {
		this.bottom = bottom;
		this.top = top;
	}

	public Position getBottom() {
		return bottom;
	}

	public Position getTop() {
		return top;
	}

	public boolean climb(Piece piece) {
		if (piece.getPosition().compareTo(bottom)) {
			piece.getPosition().setXY(top);
			System.out.println(piece.getName() + " climb ladder to " + top.positionToInt());
			return true;
		}
		return false;
	}

}
