package characterPackage.weapon;

import opponentPackage.Opponent;

public abstract class Weapon {
	private int additionalAttack;
	private int staminaNeeded;
			
	public Weapon() {
		staminaNeeded = 0;
	}

	public Weapon(int additionalAttack) {
		this.additionalAttack = additionalAttack;		
	}

	public int getAdditionalAttack() {
		return additionalAttack;
	}
	
	public void setAdditionalAttack(int additionalAttack) {
		this.additionalAttack = additionalAttack;
	}
	
	public int getStaminaNeeded() {
		return staminaNeeded;
	}

	public void setStaminaNeeded(int staminaNeeded) {
		this.staminaNeeded = staminaNeeded;
	}

	public abstract double firstAttackType(Opponent selectedOpponent);
	public abstract double secondAttackType(Opponent selectedOpponent);
	
}
