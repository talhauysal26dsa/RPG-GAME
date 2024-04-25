package opponentPackage;

import java.util.ArrayList;
import java.util.Random;

import characterPackage.Human;
import characterPackage.weapon.Weapon;

public class Wolf extends Opponent{
	
	boolean specialSuccesful = false;
	public Wolf(int opponentId, int points, int attack, int speed) {
		super(opponentId, points, attack, speed);
	}
	
	@Override
	public void special(Human<? extends Weapon> targetHuman, ArrayList<Opponent> opponentArrayList) {//callFriend
		System.out.println("Opponent " + getOpponentId() + " uses CallFriend");
		Random random = new Random();
		double chance = random.nextDouble();
		if (chance < 0.2) {
			Wolf wolf = new Wolf(opponentArrayList.size(), getPoints(), getAttack(), getSpeed());
			opponentArrayList.add(wolf);
			setSpecialSuccesful(true);
			System.out.println("callFriend is successful a new identical Wolf created.");
		}
		else {
			System.out.println("callFriend is unsuccessful, turn passed.");
		}
		
	}
	
	@Override
	public String toString() {
		return "Wolf";
	}

	public boolean isSpecialSuccesful() {
		return specialSuccesful;
	}

	public void setSpecialSuccesful(boolean specialSuccesful) {
		this.specialSuccesful = specialSuccesful;
	}
	
	
}
