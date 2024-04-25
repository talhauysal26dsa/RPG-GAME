package characterPackage;

import characterPackage.weapon.Weapon;
import opponentPackage.Opponent;

public class Villager<W extends Weapon> extends Human<W> {

	public Villager(String name, int points, int attack, int speed, W weapon) {
		super(name, points, attack, speed, weapon);	
		this.hasSpecial = false;
	}

	//!!!!!!!!!!!!!
	@Override
	public void specialAction(Opponent selectedOpponent) {
		//has not special action
	}
	
	@Override
	public String toString() {
		 return "Villager";		
	}
	
}







