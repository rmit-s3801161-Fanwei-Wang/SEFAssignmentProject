package model.entity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonObject;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.exception.CannotMoveException;
import model.exception.OutOfBoardException;

public class Piece extends PGEntity {

	private int level = 1;
	private int buff = 0;
	private ArrayList<Ladder> ladders = new ArrayList<>();
	public static final String pieceShape = "./src/model/icon/piece.png";
	private static final String knightShape = "./src/model/icon/knight.png";

	public Piece(Position position, String name) {
		super(position, name);
	}

	public boolean move(ArrayList<Entity> collections,int dice) throws CannotMoveException {
		if(level!=1)
			return false;
//		if (super.getPosition() == null) {
//			super.setPosition(new Position(dice - 1, 0));
//			collections.add(this);
//		}
		if (buff == 0) {
			super.getPosition().move(dice);
		} else {
			throw new CannotMoveException(super.getName() + " cannot move when having buff");
		}
		
		for(Entity e: collections) {
			if(e instanceof PGEntity)
				continue;
			if(e instanceof SLEntity && this.getPosition().compareTo(((SLEntity) e).getEntry())) {
				if(e instanceof Ladder) {
					if(!ladders.contains(e)) {
						ladders.add((Ladder)e);
						((Ladder)e).adjustPosition(this);
					}
				}else
					((Snake)e).adjustPosition(this);
			}
		}
		return true;
	}

	public boolean move(ArrayList<Entity> collections, String choice) throws OutOfBoardException {
		if(level!=2)
			return false;
		Position Destination = new Position(super.getPosition().getX(), super.getPosition().getY());
		Destination.move(choice);
		this.getPosition().setXY(Destination);
		Snake removeSnake = null;
		for(Entity e:collections) {
			if(e instanceof Snake) {
				if(((Snake)e).getEntry().compareTo(super.getPosition())) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION,super.getName() + " is eaten by " + e.getName()+", Snake Win!");
					alert.showAndWait();
					System.exit(0);
				}
				else if(((Snake)e).getExit().compareTo(super.getPosition())) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION,e.getName()+" died");
					alert.showAndWait();
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
			collections.remove(removeSnake);
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
	public JsonObject toDbString() {
		JsonObject json = new JsonObject();
		json.addProperty("Type", "Piece");
		json.addProperty("Name", super.getName());
		json.addProperty("PositionX", String.valueOf(super.getPosition().getX()));
		json.addProperty("PositionY", String.valueOf(super.getPosition().getY()));
		return json;
//		return String.format("{\"Type\":\"Piece\",\"Name\":\"%s\",\"PositionX\":\"%d\",\"PositionY\":\"%d\"}",
//				super.getName(),
//				super.getPosition().getX(),super.getPosition().getY());
	}

	public int getClimbNumber() {
		return ladders.size();
	}

}
