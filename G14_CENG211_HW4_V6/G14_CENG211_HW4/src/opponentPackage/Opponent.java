package opponentPackage;

import java.util.ArrayList;

import characterPackage.Human;
import characterPackage.weapon.Weapon;


public abstract class Opponent {
	private int opponentId;
	private int points;
	private int attack; 
	private int speed;
	private boolean guarded;
	int skippedTurns;
	
	public Opponent(int opponentId, int points, int attack, int speed) {
		this.opponentId = opponentId;
		this.points = points;
		this.attack = attack;
		this.speed = speed;
		this.guarded = false;
		this.skippedTurns = 0;
	}
	
	public abstract void special(Human<? extends Weapon> targetHuman, ArrayList<Opponent> opponentArrayList);
	
	public void attack(Human<? extends Weapon> targetHuman) {
			int firstTargetPoint = targetHuman.getPoints();
			targetHuman.setPoints(damagedPoints(targetHuman, 1));
			int finalTargetPoint = targetHuman.getPoints();
			int totalDamage = firstTargetPoint - finalTargetPoint;
			System.out.println("Opponent " + getOpponentId() + " attacks " + targetHuman.getName() + ". Deals " + totalDamage + " damage.\n");
			System.out.println(targetHuman.getName()+", Job: "+ targetHuman.getClass().getSimpleName() + ", Points: "
			+ targetHuman.getPoints()+", Stamina: "+ targetHuman.getStamina()+"\n");
	}
			
	
	
	public void attack(Human<? extends Weapon> targetHuman, double damageCoefficient) {
		targetHuman.setPoints(damagedPoints(targetHuman, damageCoefficient));
	}
	
	public void guard() {
		System.out.println("Opponent "+ opponentId + " uses Guard.");
		guarded = true;		
	}
	
	protected int damagedPoints(Human<? extends Weapon> targetHuman,  double damageCoefficient){
		return damagedPoints(targetHuman, getAttack(), damageCoefficient);	
	}
	
	protected int damagedPoints(Human<? extends Weapon> targetHuman, double damage,  double damageCoefficient){
		int recentPoints;
		double totalDamageReceived = damage * damageCoefficient;
		if(targetHuman.isGuarded()){
			recentPoints = (int) Math.round(targetHuman.getPoints() - (totalDamageReceived * 0.5));
		}
		else {		
			recentPoints = (int) Math.round(targetHuman.getPoints() - totalDamageReceived);
		}
		return recentPoints;
	}

	public int getOpponentId() {
		return opponentId;
	}
	public void setOpponentId(int opponentId) {
		this.opponentId = opponentId;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		if (points < 0) {
			this.points = 0;
		}
		else {
			this.points = points;
		}
		
	}
	public int getAttack() {
		return attack;
	}
	public void setAttack(int attack) {
		this.attack = attack;
	}
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isGuarded() {
		return guarded;
	}

	public void setGuarded(boolean guarded) {
		this.guarded = guarded;
	}
		
	public int getSkippedTurns() {
		return skippedTurns;
	}

	public void setSkippedTurns(int skippedTurns) {
		this.skippedTurns = skippedTurns;
	}	
	
}