package models;

import java.lang.reflect.Field;
import java.util.HashMap;

import exception.LoadGameException;
// TODO init DB API
import models.DB;

public class SystemController {
	static Player currentPlayer;
	
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
	
	//Put logic here first TODO implement this with GUI part	
	public static void signUp(String email, String userName, String password, String conPassword) {
		currentPlayer = null;
		DB db = new DB();
		if (db.isUserExist(email)) {
			System.err.println("Email exist!");
		}
		// TODO String format vaildation
		//Check email format, Check password	
		
		
		int playerCounts = db.count("select count(*) from users where type = 'player'");
		if (userName.isBlank()) {
			userName = "Player" + playerCounts;
		}
		Player newPlayer = new Player(userName, password, email);
		String sql = "insert into users (username, password, email, type) values(?, ?, ?, ?)";
		HashMap<String, Object> resultHashMap = (HashMap<String, Object>) db.create(sql, newPlayer);
		db.db_close();
		if ((boolean)resultHashMap.get("status")) {
			newPlayer.setID((long) resultHashMap.get("id"));
			currentPlayer = newPlayer;
		} else {
			System.err.println("Signup failed");
		}
	}
	
	public static void logIn(String email, String password) {
		DB db = new DB();
		currentPlayer = db.findPlayer(email, password);
		if (currentPlayer == null) {
			String validMessage = "Wrong password or email!";
		}
		db.db_close();
	}
	
	public static void logOut() {
		currentPlayer = null;
	}
	
	public static Game CreateGame() {
		// TODO GUI
		String selectRole = "";
		switch (selectRole) {
		case "human":
			Player humanPlayer = currentPlayer;
			break;
		case "snake":
			Player snakePlayer = currentPlayer;
		default:
			break;
		}
		DB db = new DB();
		//		
		String sql = "";
		return null;
	}
	
	public static Game loadGame() throws LoadGameException {
		DB db = new DB();
		// params: currentPlayer.getID(), gameID		
		String sql = "";
		Game loadGame = (Game) db.search("games", sql);
		if (loadGame == null) {
			throw new LoadGameException("Game not found!");
		}
		
		return loadGame;
	}
}
