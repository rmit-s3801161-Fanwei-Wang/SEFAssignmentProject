package models;

public class Position {
	private int x;
	private int y;

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public void setXY(int x, int y) {
		setX(x);
		setY(y);
	}

	public void setXY(Position p) {
		setX(p.getX());
		setY(p.getY());
	}

	public void move(int dice) {
		int temp = this.positionToInt() + dice;
		if(temp>100) 
			temp = 200-temp;
		intToPosition(temp);
	}

	public void move(String choice) throws Exception {
		// 先不急，人走马步用，蛇斜着走
		switch (choice) {
		case "TL":
			break;
		case "TR":
			break;

		case "BR":
			break;

		case "BL":
			break;

		case "T2L1":
			break;

		case "T1L2":
			break;

		case "T2R1":
			break;

		case "T1R2":
			break;

		case "B2R1":
			break;

		case "B1R2":
			break;

		case "B2L1":
			break;

		case "B1L2":
			break;

		default:
		}
	}

	public int positionToInt() {
		return this.getY() % 2 == 0 ? (this.getY() * 10 + this.getX() + 1) : (this.getY() * 10 + 10 - this.getX());
	}

	private void intToPosition(int z) {
		
		if ((z-1) / 10 % 2 == 0) {
			if(z%10==0)
				this.setXY(9,z/10-1);
			else
				this.setXY(z % 10 - 1, z / 10);
		}
		else {
			if(z%10==0)
				this.setXY(0, z/10 -1);
			else
				this.setXY((100 - z) % 10, z / 10 );
		}
	}

	public boolean compareTo(Position p) {
		if (p.getX() == this.x && p.getY() == this.y)
			return true;
		return false;
	}
}
