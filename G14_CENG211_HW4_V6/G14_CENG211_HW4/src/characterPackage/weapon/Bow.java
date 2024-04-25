package characterPackage.weapon;

import opponentPackage.Opponent;

public class Bow extends Weapon{

	public Bow() {
	}
		
	public Bow(int additionalAttack) {
		super(additionalAttack);	
	}

	//Shoot a single arrow to deal an opponent 0.8 × combined attack damage.
	//reduce stamina by 1
	@Override
	public double firstAttackType(Opponent selectedOpponent) {
		setStaminaNeeded(1);
		double combinedDamageCoefficent = 0.8;
		return combinedDamageCoefficent;
	}

	//Shoot two arrows at the same time to deal an opponent 2.5 × combined attack damage.
	//reduce stamina by 3
	@Override
	public double secondAttackType(Opponent selectedOpponent) {
		setStaminaNeeded(3);
		double combinedDamageCoefficent = 2.5;
		return combinedDamageCoefficent;
	}

	@Override
	public String toString() {
		return "Bow";
	}
	
}
