package characterPackage.weapon;

import opponentPackage.Opponent;

public class Spear extends Weapon{
	
	public Spear() {
	}
	
	public Spear(int additionalAttack) {
		super(additionalAttack);		
	}

	//Stab an opponent for 1.1 × combined attack damage.
	//reduce stamina by 2
	@Override
	public double firstAttackType(Opponent selectedOpponent) {
		setStaminaNeeded(2);
		double combinedDamageCoefficent = 1.1;
		return combinedDamageCoefficent;
	}

	//thrown to deal 2 × combined attack damage
	//the character skips his next turn as a result.
	//reduce stamina by 2
	@Override
	public double secondAttackType(Opponent selectedOpponent) {
		setStaminaNeeded(2);
		double combinedDamageCoefficent = 2;
		return combinedDamageCoefficent;
	}
	
	@Override
	public String toString() {
		return "Spear";
	}

}
