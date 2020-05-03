import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import models.Dice;

public class DiceTest {
	Dice d1,d2;

	@Before
	public void setUp() {
		d1 = new Dice();
		d2 = new Dice(2);
	}

	@After
	public void tearDown() {
	}

	@Test
	public void testDice() {
		int temp;
		for(int i = 0 ; i < 50 ; i++) {
			temp = d1.rollDice();
			assertTrue(temp<7);
			System.out.println(temp);
		}
		
		
	}
	
	@Test
	public void testDice2() {
		assertTrue(d2.rollDice()>1);
		
	}

}
