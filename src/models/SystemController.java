package models;

import exception.LoadGameException;
// TODO init DB API
import models.DB;

public class SystemController {
	static Player currentPlayer;
	
	//Put logic here first TODO implement this with GUI part	
	public static void signUp(String email, String userName, String password, String conPassword) {
		// TODO String format vaildation
		//Check email format, Check password	
		
		// TODO player id generator need to be changed
		DB db = new DB();	
		int playerCounts = db.count("players");
		//	idGenerator(count);	
		Player newPlayer = new Player(userName, password, email);
		String sql = "";
		db.create();
		currentPlayer = newPlayer;
		db.db_close();
	}
	
	public static void logIn(String email, String password) {
		DB db = new DB();
		String sql = "";
		currentPlayer = (Player) db.search();
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
		Game loadGame = (Game) db.search();
		if (loadGame == null) {
			throw new LoadGameException("Game not found!");
		}
		
		return loadGame;
	}
}
