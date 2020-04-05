package models;

import java.util.Random;

public class Dice {
	private int numbersOfDice = 1;
	public Dice() {
		
	}
	public Dice(int numbersOfDice) {
		this.numbersOfDice = numbersOfDice;
	}
	
	public int rollDice() {
		int value = 0;
		Random r = new Random();
		for(int i=0;i<numbersOfDice;i++)
			value += r.nextInt(6)+1;
		return value ;
	}
}
