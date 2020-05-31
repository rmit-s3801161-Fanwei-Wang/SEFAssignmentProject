package model.player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class User implements Serializable {
	private long id;
//    private String userID = idGenerator();
    private String username;
    private String password;
//    duplicate element
//    private String userName;
    private String email;
    private String type = "Player";
//    implement this by database
//    private static HashMap<String,User> users = new HashMap<>();
//    private static HashMap<String,Game> games = new HashMap<>();

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
//        this.userName = userName;
        this.email = email;
    }

//    public static HashMap<String, Game> getGames() {
//        return games;
//    }

//    public String getUserID() {
//        return userID;
//    }

//    public void setUserID(String id) {
//        userID = id;
//    }
    public long getID() {
		return id;
	}
    
    public void setID(long id) {
		this.id = id;
	}
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setType(String type) {
		this.type = type;
	}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }

    public String getUserEmail() {
        return email;
    }

    public void setUserEmail(String email) {
        this.email = email;
    }

//    public boolean forgetPassword(String email){
//        for(User user: users.values()){
//            if(email.equals(user.getUserEmail()))
//                //TODO send email function
//                return true;
//        }
//        return false;
//    }

    //
    public boolean resetPassword(String newPassword, String confirmPassword){
     
        /*
        TODO
        1. The length of password should in [6,20]
        2. Password includes upper case letter
        3. Password includes lower case letter
        */
        if(newPassword.equals(confirmPassword)){
            setPassword(newPassword);
            return true;
        }
        return false;
    }

//    abstract String idGenerator();



}
