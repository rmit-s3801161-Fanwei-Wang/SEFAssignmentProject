package models;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

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
	public Object create(String sql, Object object) {
		// result = {"status": [true, false], "id": 1, "message": ""}    		    	
    	HashMap<String, Object> result = new HashMap<>();
    	result.put("status", false);
    	result.put("id", null);
    	result.put("message", "Inserting failed");
 
    	try {
    		// Create preparedStatement
            this.ptmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            Class<?> classN = object.getClass();

            Field[] fields = classN.getDeclaredFields();
            for (int i = 1; i < fields.length; i++) {
				Field field = fields[i];
				field.setAccessible(true);
				Object value = field.get(object);
				this.ptmt.setObject((i), value);
			}
            
            int count = this.ptmt.executeUpdate();

            if (count > 0) {
            	ResultSet rs = this.ptmt.getGeneratedKeys();
            	while (rs.next()) {
    				result.put("status", true);
    				result.put("id", rs.getLong(1));
    				result.put("message", "Inserting sucessful");
    				return result;
				}
			}
            
		}
    	catch (SQLException se) {
    		//se.printStackTrace();
			result.put("status", false);
			result.put("message", "SQL excetion occuring: " + se);
		}
    	catch (Exception e) {
    		result.put("status", false);
			result.put("message", "Excetion occuring: " + e);
		}
    	finally {
    		try {
				if (this.ptmt != null) this.ptmt.close();
			} 
			catch (Exception e2) {
				System.out.println("Closing statement failed: " + e2);
			}
		}
    	return result;
	}
	
	public void delete() {
		
	}
	
	public void update() {
		
	}
	
	public Object search(String className, String sql) {
		try {
    		// Create statement            
            this.stmt = this.conn.createStatement();
            ResultSet rs = this.stmt.executeQuery(sql);
            ResultSetMetaData meta = rs.getMetaData();
    		ArrayList<Object> data = new ArrayList<>();
    		// result = {"status": [true, false], "data": [Object], "message": ""}    		
			HashMap<String, Object> result = new HashMap<>();
//    		TODO Create tables
//            switch (className) {
//    		case "":
//    			className = "";
//    			break;
//    		case "":
//    			className = "";
//    			break;
//    		case "":
//    			className = "";
//    			break;
//    		case "":
//    			className = "";
//    			break;
//    		case "":
//    			className = "";
//    			break;
//    		case "":
//    			className = "";
//    			break;
//    		case "":
//    			className = "";
//    			break;
//    		default:
//    			result.put("status", false);
//    			result.put("data", new ArrayList<>());
//    			result.put("message", "Class name not found!");
//    			return result;
//    		}
           
            while (rs.next()) {
                int cols = meta.getColumnCount();
                Class<?> classN = Class.forName(className);
    			Object obj = classN.getDeclaredConstructor().newInstance();
                for (int i = 1; i <=cols; i++) {
    				Field field = null;
    				field = classN.getDeclaredField(meta.getColumnName(i));
    				field.setAccessible(true);
    				field.set(obj, rs.getObject(i));
    			}
                
				data.add(obj);
    		}
            if (!data.isEmpty()) {
            	result.put("status", true);
                result.put("data", data);
                result.put("message", "Query success!");
			}else {
				result.put("status", true);
                result.put("data", data);
                result.put("message", "No results!");
			}
            return result;
		} catch (Exception e) {
			HashMap<String, Object> result = new HashMap<>();
			result.put("status", false);
			result.put("data", new ArrayList<>());
			result.put("message", e);
			return result;
		} finally {
			try {
				if (this.stmt != null) this.stmt.close();
			} 
			catch (Exception e2) {
				System.out.println("Closing statement failed: " + e2);
			}
		}	
	}
	
	public int count(String tableName) {
		return 0;
	}
	
	
}
