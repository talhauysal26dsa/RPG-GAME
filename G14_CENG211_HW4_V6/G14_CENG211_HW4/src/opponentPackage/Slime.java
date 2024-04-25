package opponentPackage;

import java.util.ArrayList;

import characterPackage.Human;
import characterPackage.weapon.Weapon;

public class Slime extends Opponent{

	public Slime(int opponentId, int points, int attack, int speed) {
		super(opponentId, points, attack, speed);		
	}

	@Override
	public void special(Human<? extends Weapon> targetHuman, ArrayList<Opponent> opponentArrayList) {//absorb
		int firstTargetPoint = targetHuman.getPoints();
		attack(targetHuman);
		int finalTargetPoint = targetHuman.getPoints();
		int totalDamage = firstTargetPoint - finalTargetPoint;
		int newPoints = getPoints() + getAttack();
		if(newPoints > 150) {
			newPoints = 150;
		}else {}
		setPoints(newPoints);
		System.out.println("Opponent " + getOpponentId() + " uses Absorb on " + targetHuman.getName() + ". Deals " + totalDamage + " damage.");
		System.out.println(targetHuman.getName()+", Job: "+ targetHuman.getClass().getSimpleName() + ", Points: "
		+ targetHuman.getPoints()+", Stamina: "+ targetHuman.getStamina());
		System.out.println("Opponent " + getOpponentId() + ", Type: Slime, Points: " + newPoints+"\n");
	}

	@Override
	public String toString() {
		return "Slime";
	}

}
