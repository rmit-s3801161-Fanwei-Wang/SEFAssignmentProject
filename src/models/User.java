package models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public abstract class User implements Serializable {
    private String id = idGenerator();
    private String username;
    private String password;
    private String userName;
    private String userEmail;
    private static HashMap<String,User> users = new HashMap<>();
    private static HashMap<String,Game> games = new HashMap<>();

    public User() {
    }

    public User(String username, String password, String userName, String userEmail) {
        this.username = userName;
        this.password = password;
        this.userName = userName;
        this.userEmail = userEmail;
    }

    public static HashMap<String, Game> getGames() {
        return games;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean forgetPassword(String email){
        for(User user: users.values()){
            if(email.equals(user.getUserEmail()))
                //TODO send email function
                return true;
        }
        return false;
    }

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

    abstract String idGenerator();

}
