package characterPackage;

import characterPackage.weapon.Weapon;
import opponentPackage.Opponent;

public class Knight<W extends Weapon> extends Human<W>{
	Opponent specialOpponent;
		
	public Knight(String name, int points, int attack, int speed, W weapon) {
		super(name, points, attack, speed, weapon);		
	}
	
	//can skip the current turn and deals 3 × attack on his next turn.
	@Override
	public void specialAction(Opponent selectedOpponent) {
		int targetOpponentPoint = selectedOpponent.getPoints();
		setSkippedTurns(-2);
		setSpecialUsed(true);
		this.specialOpponent = selectedOpponent;
		int dealtDamage = targetOpponentPoint-selectedOpponent.getPoints();
		System.out.println(getName() + " uses special. It will deal 3 × attack on his next turn. Now skipping current turn...");
		//System.out.println(getName() + " attacks" + " Opponent "+selectedOpponent.getOpponentId()+" "+ "Deals "+ dealtDamage +" damage.");
		//System.out.println("Opponent " +selectedOpponent.getOpponentId()+ ", Type: "+selectedOpponent.toString() +", Points: "+ selectedOpponent.getPoints());
		System.out.println(getName()+ ", Job: "+ getClass().getSimpleName()+ ", Points: "+ getPoints()+", Stamina: "+ getStamina()+"\n");
		
		
		
	}
	
	public void knightSpecialAttack() {
		int targetOpponentPoint = specialOpponent.getPoints();
		specialOpponent.setPoints(damagedPoints(specialOpponent, 3));
		int dealtDamage = targetOpponentPoint-specialOpponent.getPoints();
		System.out.println(getName() + " attacks" + " Opponent "+specialOpponent.getOpponentId()+" "+ "Deals "+ dealtDamage +" damage.");
		System.out.println("Opponent " +specialOpponent.getOpponentId()+ ", Type: "+specialOpponent.toString() +", Points: "+ specialOpponent.getPoints());
		setSkippedTurns(0);
		
	}
		
	@Override
	public String toString() {
		 return "Knight";
		
	}

}
