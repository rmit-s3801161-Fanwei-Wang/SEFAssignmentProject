package models;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import exception.DBException;
import exception.ExistException;
import exception.ValidationException;
import javafx.event.ActionEvent;
// TODO init DB API


public class SystemController {
	public static Player currentPlayer;
	private ArrayList<Game> games = Game.getGames(currentPlayer);
	
	public static void main(String[] args) {
		Player object = new Player("test", "test", "test@email.com");
		Class<?> classN = object.getClass();
		if (object instanceof models.Player) {
			classN = User.class;
		}
        Field[] fields = classN.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
			System.out.println(fields[i]);
		}
	}
	
	public static Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	//Put logic here first TODO implement this with GUI part	
	public static void signUp(String email, String userName, String password, String conPassword) throws ValidationException, DBException, ExistException {
		currentPlayer = null;
		if (email.isBlank() || password.isBlank() || conPassword.isBlank()) {
			throw new ValidationException("Please enter right information!");
		}
		DB db = new DB();
		if (db.isUserExist(email)) {
			throw new ExistException("Email exist!");
		}
		
		String emailPattern = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
		String passPattern = "^[a-zA-Z]\\w{5,17}$";
		if (!email.matches(emailPattern)) {
			System.err.println("It is not an email!");
			throw new ValidationException("It is not an email!");
		}
		
		if (!password.matches(passPattern)) {
			System.err.println("It is not a valid passaword!");
			throw new ValidationException("It is not a valid passaword!");
		} else if (!password.equals(conPassword)) {
			System.err.println("Password is not same!");
			throw new ValidationException("Password is not same!");
		}	
		
		if (userName.isBlank()) {
			int playerCounts = db.count("select count(*) from users where type = 'player'");
			userName = "Player" + (++playerCounts);
		}


		Player newPlayer = new Player(userName, password, email);
		String sql = "insert into users (username, password, email, type) values(?, ?, ?, ?)";
		HashMap<String, Object> resultHashMap = (HashMap<String, Object>) db.create(sql, newPlayer);
		db.db_close();

		if ((boolean)resultHashMap.get("status")) {
			newPlayer.setID((long) resultHashMap.get("id"));
			currentPlayer = newPlayer;
		} else {
			throw new DBException((String) resultHashMap.get("message"));
		}
	}
	
	public static void logIn(String email, String password) throws DBException, ValidationException {
		DB db = new DB();
		currentPlayer = db.findPlayer(email, password);
		if (currentPlayer == null) {
			throw new ValidationException("Wrong password or email!");
		}
		db.db_close();
	}
	
	public static void logOut() {
		currentPlayer = null;
	}
	
	public static Game CreateGame() {
//		// TODO GUI
//		String selectRole = "";
//		switch (selectRole) {
//		case "human":
//			Player humanPlayer = currentPlayer;
//			break;
//		case "snake":
//			Player snakePlayer = currentPlayer;
//		default:
//			break;
//		}
//		DB db = new DB();
//		//
//		String sql = "";
//		return null;
		return Game.createGame(currentPlayer);
	}

	//get a list of games belong to current user
	public ArrayList<Game> getGames() {
		return games;
	}





	
//	public static Game loadGame() throws LoadGameException {
//		DB db = new DB();
//		// params: currentPlayer.getID(), gameID
//		String sql = "";
//		Game loadGame = (Game) db.search("games", sql);
//		if (loadGame == null) {
//			throw new LoadGameException("Game not found!");
//		}
//
//		return loadGame;
//	}
	

}
