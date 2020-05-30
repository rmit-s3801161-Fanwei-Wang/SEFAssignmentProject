package model.player;

import model.entity.*;
import exception.LoadGameException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class Game {
    private int gameID;
    //    private Player player;
//    private Board board;
    private int playerID;
    private int boardID;

//
//    public Game(Player player, Board board) {
//        this.player = player;
//        this.board = board;
//    }
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
    
    private static HashMap<String, Position> rollMetricx(){
    	HashMap<String, Position> initPostions = new HashMap<String, Position>();
    	Position initPiece = new Position(0, 0);
    	ArrayList<Entity> entitys = new ArrayList<Entity>();
    	for (int i = 1; i < 5; i++) {
    		Piece p = new Piece(initPiece, "piece"+i);
    		entitys.add(p);
		}
    	
    	int[] ladderBottom = {-1, -1, -1, -1, -1};
    	int[] ladderTop = {-1, -1, -1, -1, -1};
    	int[] snakeHead = {-1, -1, -1, -1, -1};
    	int[] snakeTail = {-1, -1, -1, -1, -1};
    	
    	return initPostions;
    }
    
    private static int randomInt(int range) {
    	int i = (int) Math.random() * range;
    	return i;
    }
    
    private static int[] initLadderBottom(int[] bottoms) {
    	int prev = -1;
    	int ths = -1;
    	while(bottoms[4] == -1) {
    		
    	}
    }
    
    private static Position intToPosition(int z) {
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
