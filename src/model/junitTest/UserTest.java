package model.junitTest;

import org.junit.Test;

import model.player.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserTest {
	
	@Test
	public void testUser() {
		String userName = "TestName";
		String pass = "Password";
		String email = "TestEmail";
		// Player testing		
		Player testPlayer = new Player(userName, pass, email);
		Player testPlayer2 = new Player(userName + 1, pass + 1, email + 1); 
		
		assertEquals(userName, testPlayer.getUsername());
		assertEquals(pass, testPlayer.getPassword());
		assertEquals(email, testPlayer.getUserEmail());
		assertEquals("player000001", testPlayer.getUsername());
		
		assertEquals(userName + 1, testPlayer2.getUsername());
		assertEquals(pass + 1, testPlayer2.getPassword());
		assertEquals(email + 1 , testPlayer2.getUserEmail());
		assertEquals("player000002", testPlayer2.getUsername());
		
		//Reset password tesing
		testPlayer.resetPassword("NewPassword", "NewPassword");
		assertNotEquals(pass, testPlayer.getPassword());
		assertEquals("NewPassword", testPlayer.getPassword());
		
		
		// Admin testing
		Admin testAdmin = new Admin(userName, pass, email);
		assertEquals(userName, testAdmin.getUsername());
		assertEquals(pass, testAdmin.getPassword());
		assertEquals(email, testAdmin.getUserEmail());
		assertEquals("admin000001", testAdmin.getUsername());
		
		
	}

}
