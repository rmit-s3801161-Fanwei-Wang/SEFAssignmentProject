import models.Board;
import models.Game;
import models.Player;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

public class GameTest {
    Player snakePlayer = new Player("s1","123","s1@gmail.com");
    Player humanPlayer = new Player("s2","123","s2@gmail.com");
    Game game1;
    Game game2;
    Game game3;

    @BeforeClass
    public static void setUpClass() throws Exception{

    }

    @Before
    public void setUp() throws Exception {

        game1 = new Game();
        game2 = new Game(snakePlayer,humanPlayer,new Board());
        game3 = new Game(snakePlayer,humanPlayer,new Board(),5);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testGetPlayers() {

        assertEquals(snakePlayer,game2.getPlayers()[0]);
        assertEquals(humanPlayer,game2.getPlayers()[1]);

    }

    @Test
    public void setPlayers1() {
        assertNotEquals(snakePlayer,game1.getSnakePlayer());
        assertNotEquals(humanPlayer,game1.getHumanPlayer());

        game1.setSnakePlayer(snakePlayer);
        game1.setHumanPlayer(humanPlayer);
        assertEquals(snakePlayer,game1.getSnakePlayer());
        assertEquals(humanPlayer,game1.getHumanPlayer());


    }

    @Test (expected = IllegalArgumentException.class)
    public void setPlayer2(){
        game1.setSnakePlayer(snakePlayer);
        assertEquals(snakePlayer,game1.getSnakePlayer());
        game1.setHumanPlayer(snakePlayer);

    }

    @Test (expected = IllegalArgumentException.class)
    public void setPlayer3(){
        game1.setHumanPlayer(humanPlayer);
        assertEquals(humanPlayer,game1.getHumanPlayer());
        game1.setSnakePlayer(humanPlayer);

    }



    @Test
    public void TestRound() {
        assertEquals(0,game1.getRound());
        assertEquals(5,game3.getRound());

        game3.addRound();
        assertEquals(6,game3.getRound());
    }




}