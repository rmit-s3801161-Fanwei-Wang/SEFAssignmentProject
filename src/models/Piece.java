package models;

import java.awt.Graphics;
import java.util.HashMap;

import exception.OutOfBoardException;

public class Piece extends Entity {

	private int level = 1;
	private boolean debuff = false;
	private int climbedLadders = 0;

	public Piece(Position position, String name) {
		super(position, null, name);
	}

	public boolean move(HashMap<Position, Entity> collections,int dice) {
		if(level!=1)
			return false;
		if (super.getEntry() == null) {
			System.out.println(super.getName() + " move to " + dice);
			super.setEntry(new Position(dice - 1, 0));
			collections.put(super.getEntry(), this);
			 
		}
		else if (!debuff) {
			System.out.print(super.getName() + " move from " + super.getEntry().positionToInt());
			super.getEntry().move(dice);
			System.out.println(" to " + super.getEntry().positionToInt());
		} else {
			System.out.println(super.getName() + " cannot move when having buff");
			return false;
		}
		
		for(Entity e:collections.values()) {
			if(e instanceof Piece)
				continue;
			if(e.getEntry().compareTo(super.getEntry())) {
				if(e instanceof Ladder) {
					climbedLadders++;
				}
				e.adjustPosition(this);
			}
		}
		return true;
	}

	public boolean move(HashMap<Position, Entity> collections, String choice) throws OutOfBoardException {

		if(level!=3)
			return false;
		Position Destination = new Position(super.getEntry().getX(), super.getEntry().getY());
		Destination.move(choice);
		super.getEntry().setXY(Destination);
		for(Entity e:collections.values()) {
			if(e instanceof Snake) {
				if(e.getEntry().compareTo(super.getEntry())) 
					System.out.println(super.getName()+" is eaten by "+e.getName() +",Snake Win!");
				else if(e.getExit().compareTo(super.getEntry())) {
					System.out.println(e.getName()+" died");
					collections.remove(e.getEntry(), e);
				}
			}
			else if(e instanceof Ladder) {
				if(e.getEntry().compareTo(super.getEntry()))
					e.adjustPosition(this);
			}
		}
		return true;
	}
	
	public void setBuff() {
		debuff = true;
	}
	
	public void addLevel() {
		level++;
	}

	public int getLevel() {
		return level;
	}
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub

	}
	
	public int getClimbNumber() {
		return climbedLadders;
	}

}
