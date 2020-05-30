package junitTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import exception.DBException;
import exception.ExistException;
import exception.ValidationException;
import model.player.Player;
import model.player.SystemController;

public class SystemControllerTest {
	//positive
	@Test
	public void testSignUpSuccess() throws ValidationException, DBException, ExistException {
		String email = "test@email.com";
		String username = "";
		String password = "asdf1234";
		String conPassword = "asdf1234";
		SystemController.signUp(email, username, password, conPassword);
		
		Player cPlayer = SystemController.getCurrentPlayer();
		assertEquals("Player1", cPlayer.getUsername());
		assertEquals(email, cPlayer.getUserEmail());
		assertEquals(password, cPlayer.getPassword());
		assertEquals(1, cPlayer.getID());
		
	}
	
	//positive
	@Test
	public void testLogInSuccess() throws DBException, ValidationException {
		String email = "test@email.com";
		String password = "asdf1234";
		
		SystemController.logIn(email, password);
		
		Player cPlayer = SystemController.getCurrentPlayer();
		assertEquals("Player1", cPlayer.getUsername());
		assertEquals(email, cPlayer.getUserEmail());
	}
	
	//negative
	@Test (expected = ValidationException.class)
	public void testLogInFailed() throws ValidationException, DBException{
		String email = "test2@email.com";
		String password = "asdf1234";
		
		SystemController.logIn(email, password);
	}
	
	
	@Test (expected = ExistException.class)
	public void testSignUpFailed() throws ValidationException, DBException, ExistException{
		String email = "test@email.com";
		String username = "";
		String password = "asdf1234";
		String conPassword = "asdf1234";
		
		SystemController.signUp(email, username, password, conPassword);
		
	}
	
	@Test (expected = ValidationException.class)
	public void testSignUpFailedWrongEmail() throws ValidationException, DBException, ExistException {
		String email = "test_email";
		String username = "";
		String password = "asdf1234";
		String conPassword = "asdf1234";
		
		SystemController.signUp(email, username, password, conPassword);
	}
	
	@Test (expected = ValidationException.class)
	public void testSignUpFailedWrongPassword() throws ValidationException, DBException, ExistException {
		String email = "test2@email.com";
		String username = "";
		String password = "1234";
		String conPassword = "1234";
		
		SystemController.signUp(email, username, password, conPassword);
	}
	
	@Test (expected = ValidationException.class)
	public void testSignUpFailedNoInput() throws ValidationException, DBException, ExistException {
		String email = "";
		String username = "";
		String password = "";
		String conPassword = "";
		
		SystemController.signUp(email, username, password, conPassword);
	}
	
	
//	//DB negative test	
//	@Test (expected = DBException.class)
//	public void testSignInFailedDB() throws ValidationException, DBException, ExistException {
//		String email = "test2@email.com";
//		String username = "";
//		String password = "aaa1234";
//		String conPassword = "aaa1234";
//		
//		SystemController.signUp(email, username, password, conPassword);
//	}
//	
//	@Test (expected = DBException.class)
//	public void testLogInFailedDB() throws ValidationException, DBException{
//		String email = "test2@email.com";
//		String password = "asdf1234";
//		
//		SystemController.logIn(email, password);
//	}
}
