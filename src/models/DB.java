package models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {	
	private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; 
	// TODO put DB server url here
    private static final String DB_URL = "";
    
    private static final String USER = "root";
    private static final String PASS = "root";
    
    private Connection conn = null;
	private Statement  stmt = null;
	private PreparedStatement ptmt = null;
	
	public DB() {
		try {
    		Class.forName(JDBC_DRIVER);
    		// Connecting database...            
            this.conn = DriverManager.getConnection(DB_URL,USER,PASS);
		}
    	catch (SQLException se) {
    		se.printStackTrace();
    	}
    	catch (Exception e) {
			System.err.println("DB Error " + e);
		}
	}
	
	public Connection getConn() {
		return this.conn;
	}
	
	public void db_close() {
    	try {
            this.conn.close();
		} 
    	catch (SQLException se) {
			se.printStackTrace();
		}
    	catch (Exception e) {
    		System.err.println("Close DB " + e);
    	}
    	finally {
    		// Close source          
            try{
                if(conn!=null) conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }
		}
    }
	
	// TODO CRUD	
	public void create() {
		
	}
	
	public void delete() {
		
	}
	
	public void update() {
		
	}
	
	public Object search() {
		return null;
	}
	
	public int count(String tableName) {
		return 0;
	}
	
	
}
