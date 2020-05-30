package model.player;

import model.entity.Board;

import exception.LoadGameException;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Game {
<<<<<<< HEAD
    private static int uniqueID = 0;
    private String gameID = idGenerator();
    private Player snakePlayer;
    private Player humanPlayer;
    private Player[] players = new Player[2];// players[0] is snake control ID, players[1] is Human control ID
    private Board board;
    private int round;

    public Game(Player snakePlayer, Player humanPlayer, Board board, int round) {
        this.snakePlayer = snakePlayer;
        this.humanPlayer = humanPlayer;
        players = new Player[]{snakePlayer, humanPlayer};
        this.board = board;
        this.round = round;
    }

    public Game(Player snakePlayer, Player humanPlayer, Board board) {
        this(snakePlayer, humanPlayer, board, 0);
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


    public int addRound() {
        return round++;

    public int getPlayerID() {
        return playerID;

    }

    public int getBoardID() {
        return boardID;
    }

    @Override
    public String toString() {
        String snake = "N/A";
        String human = "N/A";
        if (snakePlayer != null)
            snake = snakePlayer.getUserID();
        if (humanPlayer != null)
            human = humanPlayer.getUserID();
        return String.format("Game[ ID: %s   Snake PlayerID: %s   Human PlayerID: %s ]", gameID, snake, human);

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
