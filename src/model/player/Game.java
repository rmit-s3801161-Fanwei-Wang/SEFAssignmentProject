package model.player;

import model.entity.*;
import model.exception.InitializeException;
import exception.LoadGameException;

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


public class Game {
    private int gameID;
    //    private Player player;
//    private Board board;
    private int playerID;
    private int boardID;
    private Board board;

//
//    public Game(Player player, Board board) {
//        this.player = player;
//        this.board = board;
//    }
    
    public static void main(String[] args) {
		try {
			System.out.println("Testing Start!");
			String result = initBoardDataBase();
			Board board = stringConvertToCollection(result);
			board.viewBoard();
			System.out.println("Testing End!");
		} catch (InitializeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
    
    public Game(int playerID, int boardID) {
        this.playerID = playerID;
        this.boardID = boardID;
    }

    public Game(int gameID, int playerID,int boardID) {
        this.gameID = gameID;
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

//    public Board getBoard(String boardID) {
//        final String BOARD_TABLE_NAME = "BOARD";
//
//        DB db = new DB();
//
//        String sql = "SELECT * FROM " + BOARD_TABLE_NAME + "WHERE board_id = " + boardID;
//
//        try (Connection con = db.getConn();
//             Statement stmt = con.createStatement();
//        ) {
//            Board board = new Board()
//
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }

    public int getGameID() {
        return gameID;
    }

    public int getPlayerID() {
        return playerID;
    }

    public int getBoardID() {
        return boardID;
    }


    public static Game createGame(Player currentPlayer) {
//         TODO GUI
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
    // init board to database
    private static String initBoardDataBase() throws InitializeException {
    	//    	DB db = new DB();
    	Board board = initBoard();
    	String result = collectionConvetToStringJson(board.getCollections());
    	System.out.println(result);
    	return result;
    }
    
    // import google Gson jar
    // convert board to string    
    private static String collectionConvetToStringJson(HashMap<Position, Entity> collections) {
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
    private static Board stringConvertToCollection(String json) {
    	Gson gson = new Gson();
    	Board board = new Board();
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
    private static Board initBoard() throws InitializeException{
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
    	
    	System.out.println("Starting Random");
    	ladderBottom = initLadderBottom(ladderBottom);
    	ladderTop = initLadderTop(ladderBottom, ladderTop);
    	snakeTail = initSnakeTails(ladderBottom, ladderTop, snakeTail);
    	snakeHead = initSnakeHead(snakeHead, ladderBottom, ladderTop, snakeTail);
    	System.out.println("Ending Random");
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
