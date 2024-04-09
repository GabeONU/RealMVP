package main;
/*
 * Writen by Gabriel Parry, Julian Calvelage, Enzo Bordogna
 * This is the food information that extends item
 * Writen 4/9/2024
 */
public class Food extends Item{
	
	private int calories;
	
	void setCalories(int cal) {
		calories = cal;
	}
	
	int getCalories() {
		return calories;
	}
	
}
