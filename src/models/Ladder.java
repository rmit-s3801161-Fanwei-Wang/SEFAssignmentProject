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

//	public boolean climb(Piece piece) {
//		if(piece.getPosition() == bottom){
//			piece.setPosition(top);
//			return true;
//		}
//		return false;
//	}
	

}
