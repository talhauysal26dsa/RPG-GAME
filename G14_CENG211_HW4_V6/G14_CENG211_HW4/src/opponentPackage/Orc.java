package opponentPackage;

import java.util.ArrayList;

import characterPackage.Human;
import characterPackage.weapon.Weapon;

public class Orc extends Opponent{

	public Orc(int opponentId, int points, int attack, int speed) {
		super(opponentId, points, attack, speed);	
	}

	@Override
	public void special(Human<? extends Weapon> targetHuman, ArrayList<Opponent> opponentArrayList) {//heavyHit
		int firstTargetPoint = targetHuman.getPoints();
		attack(targetHuman, 2);
		setSkippedTurns(1);		
		int finalTargetPoint = targetHuman.getPoints();
		int totalDamage = firstTargetPoint - finalTargetPoint;
		System.out.println("Opponent " + getOpponentId() + " uses HeavyHit on " + targetHuman.getName() + ". Deals " + totalDamage + " damage.");
		System.out.println(targetHuman.getName()+", Job: "+ targetHuman.getClass().getSimpleName() + ", Points: "
		+ targetHuman.getPoints()+", Stamina: "+ targetHuman.getStamina()+"\n");
	}
	
	@Override
	public String toString() {
		return "Orc";
	}
}