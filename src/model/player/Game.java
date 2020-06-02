package model.player;

import model.entity.*;
import model.exception.GameSLException;
import model.exception.InitializeException;
import exception.LoadGameException;

import java.io.StringReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.mysql.cj.xdevapi.DbDoc;


public class Game {
    private long id = -1;   //    private Player player;
//    private Board board;
    private long playerID;
    private long boardID;
    private int round = 0;
    private int levelRound = 0;
    private Board board;

    // whose turn : start from true
	private boolean human = true;
	// which stage : start from false
	private boolean level = false;

//
//    public Game(Player player, Board board) {
//        this.player = player;
//        this.board = board;
//    }
    
    public static void main(String[] args) throws SQLException, InitializeException {
    	Player player = new Player();
    	player.setID(4);
    	Game game = createGame(player);
    	game.getBoard().viewBoard();
	}
    
    public Game(long playerID, long boardID) {
        this.playerID = playerID;
        this.boardID = boardID;
    }

    public Game(long gameID, long playerID,long boardID, boolean human, boolean level, int round, int levelRound) {
        this.id = gameID;
        this.playerID = playerID;
        this.boardID = boardID;
        this.human = human;
        this.level = level;
        this.round = round;
        this.levelRound = levelRound;
    }

    public static ArrayList<Game> getGames(User currentPlayer) {
        final String GAME_TABLE_NAME = "games";
        ArrayList<Game> games = new ArrayList<>();

        DB db = new DB();

        try (Connection con = db.getConn();
             Statement stmt = con.createStatement();
        ){
            String sql = "SELECT * FROM " + GAME_TABLE_NAME + " WHERE playerID = " + currentPlayer.getID() + ";";
            try(ResultSet resultSet = stmt.executeQuery(sql)) {
                while (resultSet.next()) {
                    Game game = new Game(resultSet.getInt("id"), 
                    		resultSet.getInt("playerID"), 
                    		resultSet.getInt("boardID"),
                    		intToBoolean(resultSet.getInt("human")),
                    		intToBoolean(resultSet.getInt("level")),
                    		resultSet.getInt("round"),
                    		resultSet.getInt("levelRound")
                    		);

                    games.add(game);
                }
            }catch (SQLException e) {
//                System.out.println(e.getMessage());
            }
        } catch (Exception e){
//            System.out.println(e.getMessage());
        }
        
        return games;
    }
    
    public long getGameID() { return id; }

    public long getPlayerID() { return playerID; }

    public long getBoardID() { return boardID; }
    
    public void setBoardID(long boardID) { this.boardID = boardID; }
    
    public void setBoard(Board board) { this.board = board; }
    
    public Board getBoard() { return this.board; }

    public void setHuman(boolean human){this.human = human;}

    public boolean getHuman(){return human;}

	public void setLevel(boolean level){this.level = level;}

	public boolean getLevel(){return level;}
	
	public void setRound(int round) { this.round = round; }
	
	public int getRound() { return this.round; }
	
	public void setLevelRound(int levelRound) { this.levelRound = levelRound; }
	
	public int getLevelRound() { return this.levelRound; }

    public static Game createGame(Player currentPlayer) throws SQLException {
    	Game game = null;
    	long playerID = currentPlayer.getID();
    	long boardID = -1;
    	String collection = "";
        DB db = new DB();
        String sql = "SELECT * FROM boards WHERE createdBy='Admin' ORDER BY RAND() LIMIT 1";
        ResultSet rs = db.search(sql);
        while (rs.next()) {
			boardID = rs.getLong("id");
			collection = rs.getString("collections");
		}
        if (boardID == -1 || collection.isBlank()) {
			return game;
		}
        
        Board board = stringConvertToCollection(boardID, collection);
        game = new Game(playerID, boardID);
        game.setBoard(board);
        return game;

    }
    
    public boolean saveGame() throws SQLException, GameSLException {
		DB db = new DB();
		String collection = collectionConvetToStringJson(this.board.getCollections());
		String boardSql = "";
		String gameSql = "";
		long gameID = -1;
		long boardID = -1;
		if (this.getGameID() == -1) {
			boardSql = "INSERT INTO boards(collections, createdBy) VALUES('"+ collection +"', 'Player')";
			boardID = db.insert(boardSql);
			if (boardID == -1) {
				throw new GameSLException("Save Board Failed");
			}
			gameSql = "INSERT INTO games(playerID, boardID, human, level, round, levelRound) VALUES(" + 
					this.getPlayerID() + 
					", "+ boardID +
					", "+ booleanToInt(this.getHuman()) +
					", "+ booleanToInt(this.getLevel()) +
					", "+ this.getRound() +
					", "+ this.getLevelRound() +")";
			gameID = db.insert(gameSql);
		} else {
			boardSql = "UPDATE boards SET collections = '" + collection + "' WHERE id = " + this.getBoardID();
			boardID = db.update(boardSql, this.getBoardID());
			if (boardID == -1) { return false; }
			gameSql = "UPDATE games SET human = "+ booleanToInt(this.getHuman()) +
					", level = "+ booleanToInt(this.getLevel()) +", round = "+ this.getRound() +
					", levelRound = " + this.getLevelRound() +" WHERE id = "+ this.getGameID();
			gameID = db.update(gameSql, this.getGameID());
			if (gameID == -1) { return false; }
		}
		
		return true;
	}
    
    public Game loadGame(Player currentPlayer, long gameID) throws SQLException, GameSLException {
    	DB db = new DB();
    	Game game = null;
    	String sql = "SELECT * FROM games WHERE id = " + gameID + " and playerID = " + currentPlayer.getID();
    	ResultSet rs = db.search(sql);
    	while (rs.next()) {
			game = new Game(rs.getLong("id"), 
					rs.getLong("playerID"), 
					rs.getLong("boardID"),
					intToBoolean(rs.getInt("human")),
					intToBoolean(rs.getInt("level")),
					rs.getInt("round"),
					rs.getInt("levelRound")
					);
		}
    	if (game == null) {
			throw new GameSLException("LoadGame Failed");
		}
    	Board board = null;
    	String bSql = "SELECT * FROM boards WHERE id = " + game.getBoardID() + " and createdBy = 'Player'";
    	ResultSet bRs = db.search(bSql);
    	while (bRs.next()) {
			board = stringConvertToCollection(bRs.getLong("id"), bRs.getString("collections"));
		}
    	if (board == null) {
			throw new GameSLException("LoadBoard Failed");
		}
    	game.setBoard(board);
    	return game;
    }
    
    public static Integer booleanToInt(boolean tag) {
		if (tag) {
			return 1;
		} else {
			return 0;
		}
	}
    
    public static Boolean intToBoolean(int tag) {
    	if (tag == 1) { 
    		return true; 
    	} else {
    		return false;
    	}
    }
    
    // init board to database
    public static String initBoardDataBase() throws InitializeException {
    	Board board = initBoard();
    	String result = collectionConvetToStringJson(board.getCollections());
    	return result;
    }
    
    // import google Gson jar
    // convert board to string    
    public static String collectionConvetToStringJson(ArrayList<Entity> collections) {
    	HashMap<Entity, JsonObject> hashmap = new HashMap<Entity, JsonObject>();
    	collections.forEach((v)->{
    		JsonObject jsOb = v.toDbString();
    		hashmap.put(v, jsOb);
    	});
    	Gson gson = new Gson();
    	String hashString = gson.toJson(hashmap);
    	return hashString;
    }
    
    // convert string to board    
    public static Board stringConvertToCollection(long boardID, String json) {
    	Gson gson = new Gson();
    	Board board = new Board(boardID);
    	HashMap<String, Object> prepareMap = new HashMap<String, Object>();
    	prepareMap = (HashMap<String, Object>) gson.fromJson(json, prepareMap.getClass());
    	prepareMap.forEach((k,v)->{ 
    		Entity entity = null;
    		String type = ((Map) v).get("Type").toString();
    		String name = ((Map) v).get("Name").toString();
    		switch (type) {
			case "Ladder":
				int topX = Integer.valueOf(((Map) v).get("TopX").toString());
				int topY = Integer.valueOf(((Map) v).get("TopY").toString());
				int botX = Integer.valueOf(((Map) v).get("BotX").toString());
				int botY = Integer.valueOf(((Map) v).get("BotY").toString());
				Position top = new Position(topX, topY);
				Position bot = new Position(botX, botY);
				try {
					entity = new Ladder(bot, top, name, board.getCollections());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "Piece":
				int pX = Integer.valueOf(((Map) v).get("PositionX").toString());
				int pY = Integer.valueOf(((Map) v).get("PositionY").toString());
				Position pp = new Position(pX, pY);
				entity = new Piece(pp, name);
				break;
				
			case "Guard":
				int gX = Integer.valueOf(((Map) v).get("PositionX").toString());
				int gY = Integer.valueOf(((Map) v).get("PositionY").toString());
				Position gp = new Position(gX, gY);
				entity = new Guard(gp, name);
				break;
				
			case "Snake":
				int tailX = Integer.valueOf(((Map) v).get("TailX").toString());
				int tailY = Integer.valueOf(((Map) v).get("TailY").toString());
				int headX = Integer.valueOf(((Map) v).get("HeadX").toString());
				int headY = Integer.valueOf(((Map) v).get("HeadY").toString());
				Position tail = new Position(tailX, tailY);
				Position head = new Position(headX, headY);
				try {
					entity = new Snake(head, tail, name, board.getCollections());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
    		board.addCollection(entity);
    	});

    	return board;
    }
    
    // init board functions    
    public static Board initBoard() {
    	Board iniBoard = new Board();
    	for (int i = 0; i < 5; i++) {
    		Piece p = new Piece(new Position(0,0), "P"+(i+1));
    		iniBoard.addCollection(p);
		}
    	
    	int times = 1;
    	while (times < 6) {
    		int top = randomInt(89) + 10;
    		int bottom = -1;
    		if (top > 30) {
    			int dif = randomInt(29);
    			bottom = top - dif;
			} else {
				bottom = randomInt(top);
			}
        	Position topXY = initToPosition(top);
        	Position bottomXY = initToPosition(bottom);
        	Ladder ladder = null;
        	try {
    			ladder = new Ladder(bottomXY, topXY, "L"+times, iniBoard.getCollections());
    			times++;
            	iniBoard.addCollection(ladder);
    		} catch (Exception e) {
//    			System.out.println(e.toString());
    		}
        	
		}
    	
    	times = 1;
    	while (times < 6) {
    		int head = randomInt(89) + 10;
    		int tail = -1;
    		if (head > 30) {
    			int dif = randomInt(29);
    			tail = head - dif;
			} else {
				tail = randomInt(head);
			}
        	Position topXY = initToPosition(head);
        	Position bottomXY = initToPosition(tail);
        	Snake snake = null;
        	try {
    			snake = new Snake(topXY, bottomXY, "S"+times, iniBoard.getCollections());
    			times++;
            	iniBoard.addCollection(snake);
    		} catch (Exception e) {
//    			System.out.println(e.toString());
    		}
		}
    	return iniBoard;
    }
    
    private static int randomInt(int range) {
    	Random random = new Random();
    	int i = -1;
    	while(i < 1) {
    		i = random.nextInt(range)+1;
    	}
    	return i;
    }
    
    private static Position initToPosition(int z) {
    	Position p = null;
		if ((z - 1) / 10 % 2 == 0) {
			if (z % 10 == 0)
				p = new Position(9, z / 10 - 1);
			else
				p = new Position(z % 10 - 1, z / 10);
		} else {
			if (z % 10 == 0)
				p = new Position(0, z / 10 - 1);
			else
				p = new Position((100 - z) % 10, z / 10);
		}
		return p;
	}
}
