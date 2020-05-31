package model.entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.exception.CannotMoveException;
import model.exception.OutOfBoardException;

public class Piece extends PGEntity {

	private int level = 1;
	private int buff = 0;
	private int climbedLadders = 0;
	private static final String pieceShape = "./src/model/icon/piece.png";
	private static final String knightShape = "./src/model/icon/knight.png";

	public Piece(Position position, String name) {
		super(position, name);
	}

	public boolean move(HashMap<Position, Entity> collections,int dice) throws CannotMoveException {
		if(level!=1)
			return false;
		if (super.getPosition() == null) {
			System.out.println(super.getName() + " move to " + dice);
			super.setPosition(new Position(dice - 1, 0));
			collections.put(super.getPosition(), this);
			 
		}
		else if (buff == 0) {
			System.out.print(super.getName() + " move from " + super.getPosition().positionToInt());
			super.getPosition().move(dice);
			System.out.println(" to " + super.getPosition().positionToInt());
		} else {
			throw new CannotMoveException(super.getName() + " cannot move when having buff");
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
		if(level!=2)
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
		buff = 1;
	}

	public void roundBuff(){
		buff ++;
		if(buff == 4){
			buff = 0;
		}
	}

	public int getBuff(){
		return buff;
	}

	public void addLevel() {
		level++;
	}

	public int getLevel() {
		return level;
	}
	
	@Override
	public void draw(ImageView imageView) {
		Image image = null;
		try {
			image = new Image(new FileInputStream(pieceShape));
			imageView.setImage(image);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void draw(ImageView imageView,boolean level){
		if(level){
			Image image = null;
			try {
				image = new Image(new FileInputStream(knightShape));
				imageView.setImage(image);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String toDbString() {
		return String.format("{\"Type\":\"Piece\",\"Name\":\"%s\",\"PositionX\":\"%d\",\"PositionY\":\"%d\"}",
				super.getName(),
				super.getPosition().getX(),super.getPosition().getY());
	}

	public int getClimbNumber() {
		return climbedLadders;
	}

}
