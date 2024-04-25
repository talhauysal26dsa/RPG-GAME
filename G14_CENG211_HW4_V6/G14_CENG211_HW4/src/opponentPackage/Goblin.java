package opponentPackage;

import java.util.ArrayList;

import characterPackage.Human;
import characterPackage.weapon.Weapon;

public class Goblin extends Opponent {

	public Goblin(int opponentId, int points, int attack, int speed) {
		super(opponentId, points, attack, speed);		
	}

	@Override
	public void special(Human<? extends Weapon> targetHuman, ArrayList<Opponent> opponentArrayList) {//rushingAttack
		int firstTargetPoint = targetHuman.getPoints();
		attack(targetHuman, 0.7);
		int finalTargetPoint = targetHuman.getPoints();
		//takes a tour immediately after that one
		int totalDamage = firstTargetPoint - finalTargetPoint;
		System.out.println("Opponent " + getOpponentId() + " uses RushingAttack on " + targetHuman.getName() + ". Deals " + totalDamage + " damage.");
		System.out.println(targetHuman.getName()+", Job: "+ targetHuman.getClass().getSimpleName() + ", Points: "
		+ targetHuman.getPoints()+", Stamina: "+ targetHuman.getStamina()+"\n");
	}
	
	public void attack(Human<? extends Weapon> targetHuman, double damageCoefficient) {
		targetHuman.setPoints(damagedPoints(targetHuman, damageCoefficient));		
	}

	@Override
	public String toString() {
		return "Goblin";
	}	
	
}
