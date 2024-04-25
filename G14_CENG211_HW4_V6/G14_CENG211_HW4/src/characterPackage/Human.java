package characterPackage;

import characterPackage.weapon.Spear;
import characterPackage.weapon.Weapon;
import opponentPackage.*;
import java.lang.Math;

public abstract class Human<W extends Weapon> implements Character<W> {
		private String name;  
	    private int points;
	    private int stamina;
	    private int attack;
	    private int speed;
	    private W weapon;
	    private boolean guarded;
	    boolean specialUsed;
	    int skippedTurns;
	    boolean hasSpecial;

	    
	    public Human(String name, int points, int attack, int speed, W weapon)  {    
	    	this.name = name;
	        this.points = points;
	        this.stamina = 10;
	        this.attack = attack;
	        this.speed = speed;
	        this.weapon = weapon;
	        this.guarded = false;
	        this.specialUsed = false;
	        this.skippedTurns = 0;
	        this.hasSpecial = true;
	    }
	    
	    //The character deals “0. 8 × attack stat” to a selected opponent 
	    //This action reduces stamina stat by 1.
		@Override
		public void punch(Opponent selectedOpponent) {
			int targetOpponentPoint = selectedOpponent.getPoints();
			selectedOpponent.setPoints(damagedPoints(selectedOpponent, 0.8));
			setStamina(getStamina() - 1);
			int dealtDamage = targetOpponentPoint-selectedOpponent.getPoints();
			System.out.println(getName()+" "+"punches"+ " Opponent "+ selectedOpponent.getOpponentId()+". Deals "+ dealtDamage+ " damage.");
			System.out.println(getName()+ ", Job: "+ getClass().getSimpleName()+ ", Points: "+ getPoints()+", Stamina: "+ getStamina());
			System.out.println("Opponent "+selectedOpponent.getOpponentId()+", Type: "+selectedOpponent.getClass().getSimpleName()+", Points: "+ selectedOpponent.getPoints());
	
		}
		
		//The character selects an opponent and one the two attack types of his weapons.
		@Override
		public void attackWithWeapon(int attackType, Opponent selectedOpponent) {	
			double combinedDamageCoefficent;
			if (attackType == 1) {
				int targetOpponentPoint = selectedOpponent.getPoints();
				combinedDamageCoefficent = weapon.firstAttackType(selectedOpponent);
				double damage = getAttack() + weapon.getAdditionalAttack();
				selectedOpponent.setPoints(damagedPoints(selectedOpponent, damage, combinedDamageCoefficent));
				setStamina(getStamina() - weapon.getStaminaNeeded());
				int dealtDamage = targetOpponentPoint-selectedOpponent.getPoints();
				System.out.println(getName()+" uses " +getWeapon().toString()+ " on Opponent "+ selectedOpponent.getOpponentId()+". "+ "Deals "+ dealtDamage+ " damage.");
				System.out.println(getName()+ ", Job: "+ getClass().getSimpleName()+ ", Points: "+ getPoints()+", Stamina: "+ getStamina());
				System.out.println("Opponent "+selectedOpponent.getOpponentId()+", Type: "+selectedOpponent.getClass().getSimpleName()+", Points: "+ selectedOpponent.getPoints());
			}
			else if(attackType == 2) {
				int targetOpponentPoint = selectedOpponent.getPoints();
				combinedDamageCoefficent = weapon.secondAttackType(selectedOpponent);
				double damage = getAttack() + weapon.getAdditionalAttack();
				selectedOpponent.setPoints(damagedPoints(selectedOpponent, damage, combinedDamageCoefficent));
				setStamina(getStamina() - weapon.getStaminaNeeded());
				int dealtDamage = targetOpponentPoint-selectedOpponent.getPoints();
				System.out.println(getName()+" "+"uses "+getWeapon().toString()+ " on Opponent "+ selectedOpponent.getOpponentId()+". "+ "Deals "+ dealtDamage+ "damage.");
				System.out.println(getName()+ ", Job: "+ getClass().getSimpleName()+ ", Points: "+ getPoints()+", Stamina: "+ getStamina());
				System.out.println("Opponent "+selectedOpponent.getOpponentId()+", Type: "+selectedOpponent.getClass().getSimpleName()+", Points: "+ selectedOpponent.getPoints());
				if (getWeapon() instanceof Spear) {
					setSkippedTurns(1);
				}
				
			}									
		}
				
		//The character guards until his next turn and receives 75% reduced damage 
		//This action increases stamina by 3.
		@Override
		public void guard() {
			setGuarded(true);
			this.stamina += 3;
			System.out.println(getName()+ " starts guarding.");
			System.out.println(getName()+ ", Job: "+ getClass().getSimpleName()+ ", Points: "+ getPoints()+", Stamina: "+ getStamina());
		}
		
		//All characters run away and leave the battle.
		@Override
		public void run() {
			System.out.println("Your character(s) started running away. The battle ends!");
			System.out.println("Thanks for playing!");
			System.exit(0);
		}
		
		//The character performs a special action on a selected opponent according to his job . 
		//Each character can use their special action once per game . 
		//When they try to use it again a SpecialAlreadyUsedException should be thrown.	
		@Override
		public abstract void specialAction(Opponent selectedOpponent);

		protected int damagedPoints(Opponent selectedOpponent,  double damageCoefficient){
			return damagedPoints(selectedOpponent, getAttack(), damageCoefficient);
		}
		
		protected int damagedPoints(Opponent selectedOpponent, double damage,  double damageCoefficient){
			int recentPoints;
			double totalDamageReceived = damage * damageCoefficient;
			if(selectedOpponent.isGuarded()){
				recentPoints = (int) Math.round(selectedOpponent.getPoints() - (totalDamageReceived * 0.25));
			}
			else {		
				recentPoints = (int) Math.round(selectedOpponent.getPoints() - totalDamageReceived);
			}
			return recentPoints;
		}
		
 		public String getName() {
			return name;
		}
		public int getPoints() {
			return points;
		}
		public int getStamina() {
			return stamina;
		}
		public int getAttack() {
			return attack;
		}
		public int getSpeed() {
			return speed;
		}
		public W getWeapon() {
			return weapon;
		}
		public boolean hasSpecial() {
			return hasSpecial;
		}
		public void setHasSpecial(boolean hasSpecial) {
			this.hasSpecial = hasSpecial;
		}
		public void setName(String name) {
			this.name = name;
		}
		public void setPoints(int points) {
			if (points < 0) {
				this.points = 0;
			}
			else {
				this.points = points;
			}
		}
		public void setStamina(int stamina) {
			this.stamina = stamina;
		}
		public void setAttack(int attack) {
			this.attack = attack;
		}
		public void setSpeed(int speed) {
			this.speed = speed;
		}
		public void setWeapon(W weapon) {
			this.weapon = weapon;
		}

		
		public boolean isGuarded() {
			return guarded;
		}

		public void setGuarded(boolean guarded) {
			this.guarded = guarded;
		}

		public boolean isSpecialUsed() {
			return specialUsed;
		}

		public void setSpecialUsed(boolean specialUsed) {
			this.specialUsed = specialUsed;
		}

		public int getSkippedTurns() {
			return skippedTurns;
		}

		public void setSkippedTurns(int skippedTurns) {
			this.skippedTurns = skippedTurns;
		}	
		
		
	}


