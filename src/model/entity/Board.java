package model.entity;

import model.exception.GameSLException;
import model.player.DB;
import model.player.Game;
import model.player.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Board{
	private long id;
//	private static int grids = 10; // columns and lines
//	private int gridsize = 20; // size

	private ArrayList<Entity> collections = new ArrayList<>();

	public Board() {
		
	}
	
	public Board(long id) {
		this.id = id;
	}

	public Board(ArrayList<Entity> collections) {
		this.collections = collections;
	}

	public long getId(){
		return id;
	}

//	public static void setBoardSize(int grid) {
//		grids = grid;
//	}

	public void addCollection(Entity obj) {
		if(!collections.contains(obj))
			collections.add(obj);
	}
	
	public ArrayList<Entity> getCollections() {
		return collections;
	}
	
	public void viewBoard() {
		System.out.println();
		for(int i = 9 ; i >= 0 ; i--) {
			for(int j = 0 ; j < 10 ; j++) {
				boolean okay = false;
				for(Entity e:collections) {
					if(e instanceof PGEntity){
						if(((PGEntity) e).getPosition().compareTo(new Position(j,i))) {
							okay = true;
							System.out.print(e.getName());
						}
					}
					else{
						if(((SLEntity)e).getEntry().compareTo(new Position(j,i)) || ((SLEntity)e).getExit().compareTo(new Position(j,i))) {
							okay = true;
							System.out.print(e.getName());
						}
					}
				}
				if(!okay)
					System.out.print("-");
				System.out.print("\t");
			}
			System.out.println();
		}
		System.out.println();
	}

	public static ArrayList<Integer> getBoards() {
		final String TABLE_NAME = "boards";
		ArrayList<Integer> boards= new ArrayList<>();

		DB db = new DB();

		try (Connection con = db.getConn();
			 Statement stmt = con.createStatement();
		){
			String sql = "SELECT * FROM " + TABLE_NAME + " WHERE createdBy = 'Admin'";
//            String sql = "select * from users where email = '" + email + "' and password = '" + password + "'";
			try(ResultSet resultSet = stmt.executeQuery(sql)) {
				while (resultSet.next()) {
//					Game game = new Game(resultSet.getInt("id"), resultSet.getInt("playerID"), resultSet.getInt("boardID"));
					boards.add(resultSet.getInt("id"));
				}
			}catch (SQLException e) {
				System.out.println(e.getMessage());
			}
		} catch (Exception e){
			System.out.println(e.getMessage());
		}

		return boards;
	}
	
	public static Board findBoard(long boardID, User cUser) throws SQLException, GameSLException {
		final String TABLE_NAME = "boards";
		DB db = new DB();
		String createdBy = cUser.getType();
		String sql = "SELECT * FROM " + TABLE_NAME + " WHERE id = " + boardID + " and createdBy = '" + createdBy + "'";
		ResultSet rs = db.search(sql);
		String collection = "";
		while (rs.next()) {
			boardID = rs.getLong("id");
			collection = rs.getString("collections");
		}
		if (boardID == -1 || collection.isBlank()) {
			throw new GameSLException("Find Board Fail!");
		}
		
		Board board = Game.stringConvertToCollection(boardID, collection);
		return board;
	}
	
	// For Admin User
	public boolean saveBoard() throws SQLException {
		final String TABLE_NAME = "boards";
		DB db = new DB();
		String collection = Game.collectionConvetToStringJson(this.getCollections());
		String boardSql = "UPDATE boards SET collections = '" + collection + "' WHERE id = " + this.getId();
		long boardID = db.update(boardSql, this.getId());
		if (boardID == -1) {
			return false;
		} else {
			return true;
		}
	}
}
