package characterPackage.weapon;

import java.util.Random;

import opponentPackage.Opponent;

public class Sword extends Weapon{
	
	public Sword() {
	}
	
	public Sword(int additionalAttack) {
		super(additionalAttack);	
	}

	//Slash a selected opponent and directly deal 
	//the combined attack stat of the character and the sword.
	//reduce stamina by 2
	@Override
	public double firstAttackType(Opponent selectedOpponent) {
		setStaminaNeeded(2);
		double combinedDamageCoefficent = 1;
		return combinedDamageCoefficent;		
	}

	//Stab an opponent with 25% chance of failure.
	//in case of success, the character deals 2 × combined attack damage.
	//reduce stamina by 2
	@Override
	public double secondAttackType(Opponent selectedOpponent) {
		setStaminaNeeded(2);
		double combinedDamageCoefficent;
		Random random = new Random();
		double chance = random.nextDouble();
		if (chance < 0.25) {
			combinedDamageCoefficent = 2;
			return combinedDamageCoefficent;
		}else {
			combinedDamageCoefficent = 0;
			return combinedDamageCoefficent;
		}
	}

	@Override
	public String toString() {
		return "Sword";
	}

}
