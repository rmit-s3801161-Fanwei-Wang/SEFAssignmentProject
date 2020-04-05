package models;

public class Snake {
	private Position head;
	private Position tail;
	private static int relativeX;
	private static int relativeY;
	
	public Snake(Position head, Position tail) {
		this.head = head;
		this.tail = tail;
		relativeX = head.getX()-tail.getX();
		relativeY = head.getY()-tail.getY();
	}

	public Position getHead() {
		return head;
	}

	public boolean setHead(Position head) {
		if(head.getX()<0||head.getX()>9||head.getY()<0||head.getY()>9) {
			return false;
		}
		else if (head.getOccupy(this)) {
			return false;
		}
		this.head = head;
		this.tail.setXY(head.getX() - relativeX, head.getY() - relativeY);
		eat(head.getPieces());
		return true;
		
	}

	public Position getTail() {
		return tail;
	}

	public void eat(Piece[] pieces) {
		for(Piece piece:pieces) {
			if (piece.getPosition() == head) {
				piece.setPosition(tail);
				piece.setBuff();
			}
		}
	}

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
