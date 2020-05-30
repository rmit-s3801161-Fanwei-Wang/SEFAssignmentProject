package model.player;

import model.entity.Board;

import exception.LoadGameException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Game {
    private long gameID;
    //    private Player player;
//    private Board board;
    private String playerID;
    private String boardID;

//
//    public Game(Player player, Board board) {
//        this.player = player;
//        this.board = board;
//    }
    public Game(String playerID, String boardID) {
        this.playerID = playerID;
        this.boardID = boardID;
    }

    public Game(long gameID, String playerID, String boardID) {
        this.gameID = gameID;
        this.playerID = playerID;
        this.boardID = boardID;
    }

    //
    public static ArrayList<Game> getGames(User currentPlayer) {
        final String GAME_TABLE_NAME = "GAME";
        ArrayList<Game> games = new ArrayList<>();
        //TODO data in game table (game_id,player_id,board_id)

        DB db = new DB();

        try (Connection con = db.getConn();
             Statement stmt = con.createStatement();
        ) {
            String sql = "SELECT * FROM " + GAME_TABLE_NAME + " WHERE player_id = " + currentPlayer.getID();

            try(ResultSet resultSet = db.getStmt().executeQuery(sql)) {

                while (resultSet.next()) {
                    Game game = new Game(resultSet.getLong("game_id"), resultSet.getString("player_id"), resultSet.getString("board_id"));
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


//    public Player getPlayer() {
//        return player;
//    }
//
//    public void setPlayer(Player player) {
//        this.player = player;
//    }
//
//    public Board getBoard() {
//        return board;
//    }
//
//    public void setBoard(Board board) {
//        this.board = board;
//    }


    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getBoardID() {
        return boardID;
    }

    public void setBoardID(String boardID) {
        this.boardID = boardID;
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

}
