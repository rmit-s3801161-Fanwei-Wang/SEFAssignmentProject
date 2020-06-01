package model.player;

import com.google.gson.Gson;
import model.entity.*;
import model.exception.GameSLException;
import model.exception.InitializeException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Game {
    private long id = -1;   //    private Player player;
//    private Board board;
    private long playerID;
    private long boardID;
    private Board board;

//
//    public Game(Player player, Board board) {
//        this.player = player;
//        this.board = board;
//    }
    
    public static void main(String[] args) {
    	
	}
    
    public Game(long playerID, long boardID) {
        this.playerID = playerID;
        this.boardID = boardID;
    }

    public Game(long gameID, long playerID,long boardID) {
        this.id = gameID;
        this.playerID = playerID;
        this.boardID = boardID;
    }

    public static ArrayList<Game> getGames(User currentPlayer) {
        final String GAME_TABLE_NAME = "games";
        ArrayList<Game> games = new ArrayList<>();

        DB db = new DB();

        try (Connection con = db.getConn();
             Statement stmt = con.createStatement();
        ){
            String sql = "SELECT * FROM " + GAME_TABLE_NAME + " WHERE playerID = " + currentPlayer.getID() + ";";
//            String sql = "select * from users where email = '" + email + "' and password = '" + password + "'";
            try(ResultSet resultSet = stmt.executeQuery(sql)) {
                while (resultSet.next()) {
                    Game game = new Game(resultSet.getInt("id"), resultSet.getInt("playerID"), resultSet.getInt("boardID"));

                    games.add(game);
                }
            }catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        return games;
    }
    
    public long getGameID() {
        return id;
    }

    public long getPlayerID() {
        return playerID;
    }

    public long getBoardID() {
        return boardID;
    }
    
    public void setBoardID(long boardID) {
    	this.boardID = boardID;
    }
    
    public void setBoard(Board board) {
    	this.board = board;
    }
    
    public Board getBoard() {
    	return this.board;
    }

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
		String boardSql = "INSERT INTO boards(collections, createdBy) VALUES('"+ collection +"', 'Player')";
		long boardID = db.insert(boardSql);
		if (boardID == -1) {
			throw new GameSLException("Save Board Failed");
		}
		String gameSql = "";
		if (this.getGameID() == -1) {
			gameSql = "INSERT INTO games(playerID, boardID) VALUES(" + this.getPlayerID() + ", "+ boardID +")";
		} else {
			gameSql = "UPDATE games SET boardID = " + boardID; 
		}
		long gameID = db.insert(gameSql);
		if (gameID == -1) {
			String dSql = "DELETE FROM boards WHERE id = " + boardID;
			if(db.delete(dSql)) {
				throw new GameSLException("Save Game Failed");
			} else {
				throw new GameSLException("DB Error");
			}
		}
		this.setBoardID(boardID);
		return true;
	}
    
    public Game loadGame(Player currentPlayer, long gameID) throws SQLException, GameSLException {
    	DB db = new DB();
    	Game game = null;
    	String sql = "SELECT * FROM games WHERE id = " + gameID + "and playerID = " + currentPlayer.getID();
    	ResultSet rs = db.search(sql);
    	while (rs.next()) {
			game = new Game(rs.getLong("id"), rs.getLong("playerID"), rs.getLong("boardID"));
		}
    	if (game == null) {
			throw new GameSLException("LoadGame Failed");
		}
    	Board board = null;
    	String bSql = "SELECT * FROM boards WHERE id = " + game.getBoardID() + "and createdBy = 'Player'";
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
    
    // init board to database
    public static String initBoardDataBase() throws InitializeException {
    	Board board = initBoard();
    	String result = collectionConvetToStringJson(board.getCollections());
    	return result;
    }
    
    // import google Gson jar
    // convert board to string    
    public static String collectionConvetToStringJson(HashMap<Position, Entity> collections) {
    	HashMap<Integer, String> hashmap = new HashMap<Integer, String>();
    	collections.forEach((k,v)->{
    		int position = k.positionToInt();
    		String entity = v.toDbString();
			hashmap.put(position, entity);
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
    		int position = Integer.valueOf(k);
    		Position p = initToPosition(position);
    		Entity entity = null;
    		HashMap<String, String> entityData = new HashMap<String, String>();
    		entityData = (HashMap<String, String>) gson.fromJson((String) v, entityData.getClass());
    		String type = entityData.get("Type");
    		String name = entityData.get("Name");
    		switch (type) {
			case "Ladder":
				int topX = Integer.valueOf(entityData.get("TopX"));
				int topY = Integer.valueOf(entityData.get("TopY"));
				int botX = Integer.valueOf(entityData.get("BotX"));
				int botY = Integer.valueOf(entityData.get("BotY"));
				Position top = new Position(topX, topY);
				Position bot = new Position(botX, botY);
				try {
					entity = new Ladder(bot, top, name);
				} catch (InitializeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "Piece":
				int pX = Integer.valueOf(entityData.get("PositionX"));
				int pY = Integer.valueOf(entityData.get("PositionY"));
				Position pp = new Position(pX, pY);
				entity = new Piece(pp, name);
				break;
				
			case "Snake":
				int tailX = Integer.valueOf(entityData.get("TailX"));
				int tailY = Integer.valueOf(entityData.get("TailY"));
				int headX = Integer.valueOf(entityData.get("HeadX"));
				int headY = Integer.valueOf(entityData.get("HeadY"));
				Position tail = new Position(tailX, tailY);
				Position head = new Position(headX, headY);
				try {
					entity = new Snake(head, tail, name);
				} catch (InitializeException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
    		board.getCollections().put(p, entity);
    	});
    	
    	return board;
    }
    
    // init board functions    
    public static Board initBoard() throws InitializeException{
    	Position initPiece = new Position(0, 0);
    	ArrayList<Entity> entitys = new ArrayList<Entity>();
    	for (int i = 1; i < 5; i++) {
    		Piece p = new Piece(initPiece, "P"+(i+1));
    		entitys.add(p);
		}
    	int[] ladderBottom = {-1, -1, -1, -1, -1};
    	int[] ladderTop = {-1, -1, -1, -1, -1};
    	int[] snakeHead = {-1, -1, -1, -1, -1};
    	int[] snakeTail = {-1, -1, -1, -1, -1};
    	
    	ladderBottom = initLadderBottom(ladderBottom);
    	ladderTop = initLadderTop(ladderBottom, ladderTop);
    	snakeTail = initSnakeTails(ladderBottom, ladderTop, snakeTail);
    	snakeHead = initSnakeHead(snakeHead, ladderBottom, ladderTop, snakeTail);
    	
    	for (int i = 0; i < ladderBottom.length; i++) {
    		Position bottom = initToPosition(ladderBottom[i]);
    		Position top = initToPosition(ladderTop[i]);
			Ladder ladder = new Ladder(bottom, top, "L"+(i+1));
			entitys.add(ladder);
		}
    	for (int i = 0; i < snakeHead.length; i++) {
			Position head = initToPosition(snakeHead[i]);
			Position tail = initToPosition(snakeTail[i]);
			Snake snake = new Snake(head, tail, "S"+(i+1));
			entitys.add(snake);
		}
    	Board iniBoard = new Board();
    	for (int i = 0; i < entitys.size(); i++) {
    		iniBoard.addCollection(entitys.get(i));
		}
    	iniBoard.viewBoard();
    	return iniBoard;
    }
    
    private static int randomInt(int range) {
    	Random random = new Random();
    	int i = -1;
    	while(i < 1) {
    		i = random.nextInt(range);
    	}
    	return i;
    }
    
    private static int[] initLadderBottom(int[] bottoms) {
    	boolean dup = false;
    	int ths = -1;
    	int n = 0;
    	while(bottoms[4] == -1) {
    		ths = randomInt(100);
    		for (int bottom : bottoms) {
				if (ths == bottom) {
					dup = true;
					break;
				}
			}
    		if (!dup && ths > 1) {
				bottoms[n] = ths;
				dup = false;
    			n++;
			}
    	}
    	return bottoms;
    }
    
    private static int[] initLadderTop(int[] bottoms, int[] tops) {
    	boolean dup = false;
    	int ths = -1;
    	int n = 0;
    	while(tops[4] == -1) {
    		ths = randomInt(100);
    		if (ths == 100) {
				continue;
			}
    		for (int top : tops) {
				if (ths == top) {
					dup = true;
					break;
				}
			}
    		if (dup) {
    			dup = false;
				continue;
			}
    		for (int bottom : bottoms) {
				if (ths == bottom) {
					dup = true;
					break;
				}
			}
    		if (!dup && ths > bottoms[n] && (ths - bottoms[n]) <= 30) {
				tops[n] = ths;
				dup = false;
				n++;
			}
    	}
    	return tops;
    }
    
    private static int[] initSnakeTails(int[] bottoms, int[] tops, int[] tails) {
    	boolean dup = false;
    	int ths = -1;
    	int n = 0;
    	while(tails[4] == -1) {
    		ths = randomInt(100);
    		for (int bottom : bottoms) {
				if (ths == bottom) {
					dup = true;
					break;
				}
			}
    		if (dup) {
    			dup = false;
				continue;
			}
    		for (int top : tops) {
				if (ths == top) {
					dup = true;
					break;
				}
			}
    		if (dup) {
    			dup = false;
				continue;
			}
    		for (int tail : tails) {
				if (ths == tail) {
					dup = true;
					break;
				}
			}
    		if (!dup) {
				tails[n] = ths;
				dup = false;
				n++;
			}
    	}
    	return tails;
    }
    
    private static int[] initSnakeHead(int[] heads, int[] bottoms, int[] tops, int[] tails) {
    	boolean dup = false;
    	int ths = -1;
    	int n = 0;
    	while(heads[4] == -1) {
    		ths = randomInt(100);
    		if (ths == 100) {
				continue;
			}
    		for (int bottom : bottoms) {
				if (ths == bottom) {
					dup = true;
					break;
				}
			}
    		if (dup) {
    			dup = false;
				continue;
			}
    		for (int top : tops) {
				if (ths == top) {
					dup = true;
					break;
				}
			}
    		if (dup) {
    			dup = false;
				continue;
			}
    		for (int tail : tails) {
				if (ths == tail) {
					dup = true;
					break;
				}
			}
    		if (dup) {
    			dup = false;
				continue;
			}
    		for (int head : heads) {
				if (ths == head) {
					dup = true;
				}
				if (ths == head && (ths < 100 && ths >= 81)) {
					dup = true;
				}
				if (dup) { break; }
			}
    		if (!dup && tails[n] < ths && (ths - tails[n]) <= 30) {
				heads[n] = ths;
				dup = false;
				n++;
			}
    	}
    	return heads;
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
