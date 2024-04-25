package characterPackage;

import characterPackage.weapon.Weapon;
import opponentPackage.*;

public interface Character<W extends Weapon> {
	    public void punch(Opponent selectedOpponent);
	    public void attackWithWeapon(int AttackType, Opponent selectedOpponent);
	    public void guard();
	    public void run();
	    public void specialAction(Opponent selectedOpponent);  //karakter special actionu oyun boyunca bir kez kullanabilecek  
	    							  //bir kez daha kullanmaya çalışırsa SpecialAlreadyUsedException
}
