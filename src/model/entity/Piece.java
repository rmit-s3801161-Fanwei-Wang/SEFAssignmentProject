package model.entity;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import model.exception.OutOfBoardException;

public class Piece extends PGEntity {

	private int level = 1;
	private boolean debuff = false;
	private int climbedLadders = 0;
	private static final String pieceShape = "./icon/piece.png";

	public Piece(Position position, String name) {
		super(position, name);
	}

	public boolean move(HashMap<Position, Entity> collections,int dice) {
		if(level!=1)
			return false;
		if (super.getPosition() == null) {
			System.out.println(super.getName() + " move to " + dice);
			super.setPosition(new Position(dice - 1, 0));
			collections.put(super.getPosition(), this);
			 
		}
		else if (!debuff) {
			System.out.print(super.getName() + " move from " + super.getPosition().positionToInt());
			super.getPosition().move(dice);
			System.out.println(" to " + super.getPosition().positionToInt());
		} else {
			System.out.println(super.getName() + " cannot move when having buff");
			return false;
		}
		
		for(Entity e:collections.values()) {
			if(e instanceof PGEntity)
				continue;
			if(e instanceof SLEntity) {
				if(e instanceof Ladder) {
					climbedLadders++;
				}
				((SLEntity)e).adjustPosition(this);
			}
		}
		return true;
	}

	public boolean move(HashMap<Position, Entity> collections, String choice) throws OutOfBoardException {

		if(level!=3)
			return false;
		Position Destination = new Position(super.getPosition().getX(), super.getPosition().getY());
		Destination.move(choice);
		super.getPosition().setXY(Destination);
		Snake removeSnake = null;
		for(Entity e:collections.values()) {
			if(e instanceof Snake) {
				if(((Snake)e).getEntry().compareTo(super.getPosition()))
					System.out.println(super.getName()+" is eaten by "+e.getName() +",Snake Win!");
				else if(((Snake)e).getExit().compareTo(super.getPosition())) {
					System.out.println(e.getName()+" died");
					removeSnake = (Snake)e;
					break;
				}
			}
			else if(e instanceof Ladder) {
				if(((Ladder)e).getEntry().compareTo(super.getPosition()))
					((Ladder)e).adjustPosition(this);
			}
		}
		if(removeSnake!=null) {
			collections.remove(removeSnake.getEntry());
			collections.remove(removeSnake.getExit());
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
//		BufferedImage
	}

	@Override
	public String toDbString() {
		return String.format("{\"Type\":\"Piece\",\"Name\":\"%s\",\"PositionX\":%d,\"PositionY\":%d}",
				super.getName(),
				super.getPosition().getX(),super.getPosition().getY());
	}

	public int getClimbNumber() {
		return climbedLadders;
	}

}
