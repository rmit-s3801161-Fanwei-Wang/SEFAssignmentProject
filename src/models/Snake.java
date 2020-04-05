package models;

public class Snake {
	private Position head;
	private Position tail;

	
	public Snake(Position head, Position tail) {
		this.head = head;
		this.tail = tail;
	}

	public Position getHead() {
		return head;
	}

	public void setHead(Position head) {
		this.head = head;
	}

	public Position getTail() {
		return tail;
	}

	public void setTail(Position tail) {
		this.tail = tail;
	}

//	public boolean eat(Piece piece) {
//		if (piece.getPosition() == head) {
//			piece.setPosition(tail);
//			return true;
//		}
//		return false;
//	}

	public void move(String choice) {
		switch(choice.toUpperCase().charAt(0)) {
		case 'A':
			
		case 'B':
			
		case 'C':
			
		case 'D':
			
		default:
		}
	}
}
