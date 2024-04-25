package characterPackage;

import characterPackage.weapon.Weapon;
import opponentPackage.Opponent;

public class Hunter<W extends Weapon> extends Human<W> {

	public Hunter(String name, int points, int attack, int speed, W weapon) {
		super(name, points, attack, speed, weapon);		
	}

	//can attack for 0.5 × attack in the current turn 
	//and have two turns back to back for his next turn
	@Override
	public void specialAction(Opponent selectedOpponent) {
		int targetOpponentPoint = selectedOpponent.getPoints();
		selectedOpponent.setPoints(damagedPoints(selectedOpponent, 0.5));
		setSpecialUsed(true);
		setSkippedTurns(-1);
		int dealtDamage = targetOpponentPoint-selectedOpponent.getPoints();
		System.out.println(getName() + " attacks" + " Opponent "+selectedOpponent.getOpponentId()+" "+ "Deals "+ dealtDamage +" damage.");
		System.out.println("Opponent " +selectedOpponent.getOpponentId()+ ", Type: "+selectedOpponent.toString() +", Points: "+ selectedOpponent.getPoints());
		System.out.println(getName()+ ", Job: "+ getClass().getSimpleName()+ ", Points: "+ getPoints()+", Stamina: "+ getStamina());
		
	}

	@Override
	public String toString() {
		
		return "Hunter"; 
		
	}
	
	
}
