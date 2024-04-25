package gamePackage;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.function.Supplier;

import opponentPackage.Goblin;
import opponentPackage.Slime;
import opponentPackage.Wolf;
import opponentPackage.Opponent;
import opponentPackage.OpponentNames;
import opponentPackage.Orc;
import characterPackage.CharacterJobs;
import characterPackage.Human;
import characterPackage.Hunter;
import characterPackage.Knight;
import characterPackage.Squire;
import characterPackage.Villager;
import characterPackage.weapon.Bow;
import characterPackage.weapon.Spear;
import characterPackage.weapon.Sword;
import characterPackage.weapon.Weapon;
import characterPackage.weapon.WeaponNames;
import exceptionPackage.*;

public class TBGame {
	ArrayList<Opponent> opponentArrayList;
	ArrayList<Human<? extends Weapon>> humanArrayList;
	private Map<Object, Turn> turnsMap;
	private Map<Turn, Object> reverseTurnsMap; 
	public PriorityQueue<Turn> turnOrder;
	ArrayList<Turn> turnArrayList;
	Scanner scanner;
	 
	 public static void main(String[] args) {
		 TBGame tbgame = new TBGame();
		 TBGame.Menu menu =  tbgame.new Menu();
		 TBGame.Initializer initializer =  tbgame.new Initializer();
		 TBGame.QueueAndMapInitializer queueAndMapInitializer = tbgame.new QueueAndMapInitializer();
		 ArrayList<Opponent> opponentList = initializer.createOpponentList();
		 menu.display(initializer, opponentList);	
		 queueAndMapInitializer.createTurnsMap();
		 queueAndMapInitializer.createTurnOrder();
		 tbgame.createTurnArrayList();
		 menu.displayturnOrder(tbgame);
		 tbgame.gameLogic();
		 tbgame.scanner.close();
	 }
	 
	 public void gameLogic() {
		 while(!(humanArrayList.isEmpty())&&!(opponentArrayList.isEmpty())) {
			 Turn nextTurn = turnArrayList.get(0); //null
			 turnArrayList.remove(nextTurn);
			 if(reverseTurnsMap.get(nextTurn) instanceof Human){
				 System.out.println("\nIt is the turn of: " + nextTurn.getOwnerId());
				 Human<? extends Weapon> human = (Human<? extends Weapon>) reverseTurnsMap.get(nextTurn);
				 humanActionDecider(human, turnArrayList);
				
			 }
			 else {
				 Opponent opponent = (Opponent) reverseTurnsMap.get(nextTurn);
				 opponentActionDecider(opponent, turnArrayList);
			 }
			 Iterator<Human<? extends Weapon>> humanIterator = humanArrayList.iterator();
			 while (humanIterator.hasNext()) {
			     Human<? extends Weapon> human = humanIterator.next();
			     if (human.getPoints() <= 0) {
			         humanIterator.remove();
			         turnArrayList.remove(turnsMap.get(human));
			     }
			 }

			 // For opponentArrayList
			 Iterator<Opponent> opponentIterator = opponentArrayList.iterator();
			 while (opponentIterator.hasNext()) {
			     Opponent opponent = opponentIterator.next();
			     if (opponent.getPoints() <= 0) {
			         opponentIterator.remove();
			         turnArrayList.remove(turnsMap.get(opponent));
			     }
			 }
			 System.out.print("\n\n*** Turn Order: ");
			 for(Turn turn :turnArrayList) {
				 System.out.print(turn.getOwnerId());
				 System.out.print("  > ");
				 
			 }
			 System.out.println("");
			 System.out.println("\n************ Turn Finish ************\n\n\n");
			 
			 	 
		 }
		 System.out.println("*********************************************** End of the game ***********************************************");
	 }
	 
	 
	 private void humanActionDecider(Human<? extends Weapon> human, ArrayList<Turn> turnArrayList) {
		 human.setGuarded(false);
		 if(human.getSkippedTurns() > 0){
			 skip(human);
			 turnArrayList.add(turnsMap.get(human));
		 }
		 else if(human.getSkippedTurns() == 0){
			 actionHuman(human);
			 turnArrayList.add(turnsMap.get(human));
		 }
		 else if(human.getSkippedTurns() < 0) {
			 if(human.getSkippedTurns() < -1) {
				 Knight<? extends Weapon> knight = (Knight<? extends Weapon>) human;
				 knight.knightSpecialAttack();
				 turnArrayList.add(turnsMap.get(human));
			 }
			 else {
				 human.setSkippedTurns(human.getSkippedTurns() + 1);
				 actionHuman(human);
				 humanActionDecider(human, turnArrayList);
				 turnArrayList.add(turnsMap.get(human));
			 }
		 }
	 }
	 
	 private void opponentActionDecider(Opponent opponent, ArrayList<Turn> turnArrayList) {
		 opponent.setGuarded(false);
		 if(opponent.getSkippedTurns() > 0){
			 skip(opponent);
			 turnArrayList.add(turnsMap.get(opponent));
		 }
		 else if(opponent.getSkippedTurns() == 0){
			 actionOpponent(opponent);
			 turnArrayList.add(turnsMap.get(opponent));
		 }
	 }
	 
	 private int selectAttackType() {
		 int choice = 0;
		 boolean validInput = false;
		 displayAttackTypeOptions();
		 System.out.println("Please enter an Attack Type 1 or 2:");
		 while (!validInput) {
			 try {
				 choice = Integer.parseInt(scanner.nextLine());
				 if (choice == 1 || choice == 2) {
					 validInput = true;
				 } else {
					 System.out.println("Please enter an Attack Type 1 or 2:");
				 }
			 } catch (NumberFormatException e) {
				 System.out.println("Invalid input. Please enter a valid number.");
			 }
		 }
		 return choice;
	 }
 
	 public void switchOptions(int choice, Human<? extends Weapon> human) {
         switch (choice) {
           case 1:
               human.punch(selectedOpponent());
               break;
            case 2:
                human.attackWithWeapon(selectAttackType(), selectedOpponent());
                break;
            case 3:
                human.guard();
                break;
            case 4:
                human.specialAction(selectedOpponent());
                break;
            case 5:
                human.run();;
                break;
          //default:
                //throw new IllegalArgumentException("Unexpected value: " + choice);
         }

     }
 
     private void actionHuman(Human<? extends Weapon> human){
         int choice = 0;
         boolean validInput = false;
         while (!validInput) {
             displayActionOptions();
             String input = scanner.nextLine();
             try {
                 choice = Integer.parseInt(input);
                 if (choice >= 1 && choice <= 5) {
                     validInput = true;
                     if (choice == 4) {
                         if (human.isSpecialUsed()) {
                             validInput = false;
                             throw new SpecialAlreadyUsedException(); 
                        }
                         else if (!human.hasSpecial()) {
                        	 System.out.println("Has not special. Please make another choice.");
							 validInput = false;
						}
                         else {
							 validInput = true;
						}
                    }
                 }
                 else {
                     System.out.println("Please enter a number between 1 and 5.");
                 }
             } catch (NumberFormatException e) {
                 System.out.println("Invalid input. Please enter a valid number.");
             } catch (SpecialAlreadyUsedException e) {
                System.out.println(e.getMessage());
                System.out.println("Please enter another option.");
            }
         }
         switchOptions(choice, human);
     }
	 
	 private void actionOpponent(Opponent opponent){
		 Human<? extends Weapon> randomHuman = selectRandomHumanForOpponent();
		 selectRandomOpponentActions(opponent, randomHuman);
	 }
	 
	 private void displayAttackTypeOptions() {
		 System.out.println("*\n[1] First Attack Type "
				 			 + "[2] Second Attack Type");
	 }
	 
	 private void displayActionOptions() {
		 System.out.println("\n[1] Punch\r\n"
				 			+ "[2] Attack with weapon\r\n"
				 			+ "[3] Guard\r\n"
				 			+ "[4] Special Action\r\n"
				 			+ "[5] Run");
		 System.out.println("\n");
	 }
	 private ArrayList<Integer> getValidOpponents() {
		 ArrayList<Integer> validIds = new ArrayList<Integer>();
		 for (Opponent opponent : opponentArrayList) {
			 validIds.add(opponent.getOpponentId());
			 //System.out.println(opponent.getOpponentId() + "		"); 
		 }
		 //System.out.println(validIds.toString());
		return validIds;
		
	}
	 private Opponent selectedOpponent() {
		 try{
			 boolean validInput = false;
			 int opponentId = 0;
			 System.out.print("Please choose an opponent id between: ");
			 System.out.println(getValidOpponents().toString());
			 while (!validInput) {
				 String input = scanner.nextLine();
				 try {
					 opponentId = Integer.parseInt(input);
					 if (getValidOpponents().contains(opponentId)) {
						 validInput = true;
					 } else {
						 System.out.println("Please choose an opponent id between: ");
						 getValidOpponents();
					 }
				 } catch (NumberFormatException e) {
					 System.out.println("Invalid input. Please enter a valid number.");
				 }
			 }
			 for (Opponent opponent : opponentArrayList) {
				 if (opponentId == opponent.getOpponentId()) {
					 return opponent;
				 }
			 }
		 }
		 catch (Exception e) {
			 System.out.println(e.getMessage());
		 }
		 return null;
	 }

	 private void skip(Human<? extends Weapon> human) {
		 System.out.println("Skipping Turn");
		 human.setSkippedTurns(human.getSkippedTurns() - 1);
	 }
	 
	 private void skip(Opponent opponent) {
		 System.out.println("Skipping Turn");
		 opponent.setSkippedTurns(opponent.getSkippedTurns() - 1);
	 }
	 
	 private void selectRandomOpponentActions(Opponent opponent, Human<? extends Weapon> targetHuman) {
         Random random = new Random();
         int actionNumber = random.nextInt(3)+1; 
         switch(actionNumber) {
         case 1:
             opponent.attack(targetHuman);
             break;
         case 2:
             opponent.guard();
             break;
         case 3:
             opponent.special(targetHuman, opponentArrayList);
             if (opponent instanceof Goblin) {
        		 selectRandomOpponentActions(opponent, targetHuman);
        	 }
             if ((opponent instanceof Wolf) && ((Wolf) opponent).isSpecialSuccesful()) {
            	 wolfSpecialAttack();
            	 Wolf wolf = (Wolf) opponent;
            	 wolf.setSpecialSuccesful(false);
        	 }
             break;
        default:
            break;
         } 
     }
	 
	 private Human<? extends Weapon> selectRandomHumanForOpponent() {
         Random random = new Random();
         int index = random.nextInt(humanArrayList.size());
         return humanArrayList.get(index);
     }
	 
	 private void wolfSpecialAttack(){
		    Opponent newWolf = opponentArrayList.get(opponentArrayList.size()-1);
	         Turn turn = new Turn("Opponent "+ String.valueOf(newWolf.getOpponentId()), 1.0);
	         turnArrayList.add(turn);
	         turnsMap.put(newWolf, turn); // Use opponent object as key
			 reverseTurnsMap.put(turn, newWolf);
	         
		 
	 }	 

	 private void createTurnArrayList() {
         turnArrayList = new ArrayList<>();
         PriorityQueue<Turn> turnOrderCopy = new PriorityQueue<>(turnOrder);

         while (!turnOrderCopy.isEmpty()) {
                turnArrayList.add(turnOrderCopy.poll());
            }
 
     }
	 
	 public PriorityQueue<Turn> getTurnOrder() {
		 return turnOrder;
	 }
	 
	 public ArrayList<Opponent> getOpponentArrayList() {
		 return opponentArrayList;
	 }
			 
	 public class QueueAndMapInitializer{ 
		 
		 public QueueAndMapInitializer() {	 
		 }
		 
		 public void createTurnOrder() {
			 turnOrder = new PriorityQueue<>(Comparator.comparingDouble(this::getSpeedForTurn).reversed());
			 turnOrder.addAll(turnsMap.values());
		 }

		 // Helper method to get speed attribute for associated object in a Turn
		 private double getSpeedForTurn(Turn turn) {
			 Object associatedObject = reverseTurnsMap.get(turn);
			 if (associatedObject instanceof Opponent) {
				 return ((Opponent) associatedObject).getSpeed();
				 } 
			 else if (associatedObject instanceof Human) {
				 return ((Human<?>) associatedObject).getSpeed(); 
			 }
			 return 0; 
		 }
		
		 public void createTurnsMap() {
			 turnsMap = new HashMap<>();
			 reverseTurnsMap = new HashMap<>();		 
			 // Create turns for opponents
			 if (opponentArrayList != null) {
				 for (Opponent opponent : opponentArrayList) {
					 Turn turn = new Turn("Opponent "+String.valueOf(opponent.getOpponentId()), 1.0);
					 turnsMap.put(opponent, turn); // Use opponent object as key
					 reverseTurnsMap.put(turn, opponent); // Store turn-object association
				 }
			 }
			 // Create turns for humans
			 if (humanArrayList != null) {
				 for (Human<? extends Weapon> human : humanArrayList) {
					 Turn turn = new Turn(human.getName(), 1.0);
					 turnsMap.put(human, turn); // Use human object as key
					 reverseTurnsMap.put(turn, human); // Store turn-object association
				 }
			 }
		 }	 
	 }
	 	 
	 public class Initializer{
		 			
		 public Initializer(){
			 opponentArrayList = new ArrayList<>();
			 humanArrayList = new ArrayList<>();
			 scanner = new Scanner(System.in);
		 }
		 
		 private ArrayList<Human<? extends Weapon>> createHumanList(){		
			 Map<String, Supplier<? extends Weapon>> weaponSuppliers = new HashMap<>();
			 weaponSuppliers.put("SWORD", Sword::new);
			 weaponSuppliers.put("BOW", Bow::new);
			 weaponSuppliers.put("SPEAR", Spear::new);
			 int characterNum = getCharacterNumber();	    
			
			 for (int i=1; i<=characterNum; i++) {
				 String characterName = getCharacterName(humanArrayList , i);
				 String randomWeapon = getRandomWeapon();
				 String randomJob = getRandomJob();												
				 Human<? extends Weapon> human = null;
				 switch (randomJob) {
				 case "HUNTER":
					 Weapon hunterWeapon = weaponSuppliers.get(randomWeapon).get();
					 hunterWeapon.setAdditionalAttack(getRandomAdditionalAttack());
					 
					 human = new Hunter<>(characterName, getRandomHumanPoints(), getRandomHumanAttackStat(), getRandomHumanSpeed(), hunterWeapon);
					 break;
				 case "KNIGHT":
					 Weapon knightWeapon = weaponSuppliers.get(randomWeapon).get();
					 knightWeapon.setAdditionalAttack(getRandomAdditionalAttack());
					 human = new Knight<>(characterName, getRandomHumanPoints(), getRandomHumanAttackStat(), getRandomHumanSpeed(), knightWeapon);
					 break;
				 case "VILLAGER":
					 Weapon villagerWeapon = weaponSuppliers.get(randomWeapon).get();
					 villagerWeapon.setAdditionalAttack(getRandomAdditionalAttack());
					 human = new Villager<>(characterName, getRandomHumanPoints(), getRandomHumanAttackStat(), getRandomHumanSpeed(), villagerWeapon);
					 break;
				 case "SQUIRE":
					 Weapon squireWeapon = weaponSuppliers.get(randomWeapon).get();
					 squireWeapon.setAdditionalAttack(getRandomAdditionalAttack());
					 human = new Squire<>(characterName, getRandomHumanPoints(), getRandomHumanAttackStat(), getRandomHumanSpeed(), squireWeapon);
					 break;
				 default:
					 System.out.println("Invalid type name selected");
					 break;               
				 }
				 humanArrayList.add(human);							
			 }
			 return humanArrayList;														
		 }
					 
		 private ArrayList<Opponent> createOpponentList(){
			 for (int i = 1; i<=getRandomNumber(1, 4); i++) {
				 Opponent opponent = null;
				 String opponentType = getRandomOpponentString();
				 switch (opponentType) {
				 case "ORC":
					 opponent = new Orc(i, getRandomOpponentPoints(), getRandomOpponentAttackStat(), getRandomOpponentSpeed());
					 break;
				 case "GOBLIN":
					 opponent = new Goblin(i, getRandomOpponentPoints(), getRandomOpponentAttackStat(), getRandomOpponentSpeed());
					 break;
				 case "SLIME":
					 opponent = new Slime(i, getRandomOpponentPoints(), getRandomOpponentAttackStat(), getRandomOpponentSpeed());
					 break;
				 case "WOLF":
					 opponent = new Wolf(i, getRandomOpponentPoints(), getRandomOpponentAttackStat(), getRandomOpponentSpeed());
					 break;
				 default:
					 System.out.println("Invalid type name selected");
					 break;               
				 }
				 opponentArrayList.add(opponent);				   	  
			 }
			 return opponentArrayList;	  			 
		 }
		 
		 //we do random number in that way because of readability		 
		 private int getRandomNumber(int min, int max) {
	        Random random = new Random();
	        return random.nextInt((max - min) + 1) + min;
		 }
		 	    		 
		 private int getRandomOpponentPoints() {
			 return getRandomNumber(50, 150);			
		 }			
		 private int getRandomOpponentAttackStat() {
			 return getRandomNumber(5, 25);			
		 }			
		 private int getRandomOpponentSpeed() {
			 return getRandomNumber(1, 90);			
		 }		 
		 private int getRandomHumanPoints() {
			 return getRandomNumber(100, 150);			
		 }		 
		 private int getRandomHumanAttackStat() {
			 return getRandomNumber(20, 40);			
		 }			
		 private int getRandomHumanSpeed() {
			 return getRandomNumber(10, 99);			
		 }		 

		 private int getCharacterNumber() {
			 
		        int number;
		        while (true) {
		            System.out.print("Please enter the number of characters to create: ");
		            try {
		                number = Integer.parseInt(scanner.nextLine());
		                if (number >= 1 && number <= 3) {
		                    break; // Input is valid, break out of the loop
		                } else {
		                    System.out.println("Please enter an integer between 1 and 3.");
		                }
		            } catch (NumberFormatException e) {
		            	System.out.println("That's not an integer! Please enter an integer.");
		            }
		        }
		        return number;
		 }
		 				 
		 private int getRandomAdditionalAttack() {
			 return getRandomNumber(10, 20);			
		 }
		 
		 private String getCharacterName(ArrayList<Human<? extends Weapon>> humanArrayList, int count) {
			 
			    String name = null;
			    boolean isUnique = false;
			    do {
			        try {
			            System.out.print("\nEnter the name of your " + count + ". character: "+"\n");
			            name = scanner.nextLine();
			            isUnique = isNameUnique(name, humanArrayList);
			            if (!isUnique) {
			                throw new NotAUniqueNameException("Name is not unique. Please enter a different name.");
			                }
			            } 
			        catch (NotAUniqueNameException e) {
			            System.out.println("Error: " + e.getMessage());
			        }
			    }while (!isUnique);
			    return name;
		 }
		 		 
		 private boolean isNameUnique(String name, ArrayList<Human<? extends Weapon>> humanArrayList) {
		        for (Human<? extends Weapon> human : humanArrayList) {
		            if (human.getName().equals(name)) {
		                return false;
		                }
		            }
		        return true;
		        }
		  		 
		 private String getRandomOpponentString() {
		        OpponentNames[] values = OpponentNames.values();
		        Random random = new Random();
		        int index = random.nextInt(values.length); 
		        return values[index].toString(); // Return the string representation of the random enum value
		 }
		 		 	 
		 private String getRandomWeapon() {
				WeaponNames[] values = WeaponNames.values();
		        Random random = new Random();
		        int index = random.nextInt(values.length); 
		        return values[index].toString();						
		 }
		 
		 private String getRandomJob() {
				CharacterJobs[] values = CharacterJobs.values();
		        Random random = new Random();
		        int index = random.nextInt(values.length); 
		        return values[index].toString();						
		 }
		 		
	 }

	 public class Menu {
		 
		 public void displayturnOrder(TBGame tbgame) {
			 // Retrieve and process turns in the order of speed attributes
			 PriorityQueue<Turn> turnOrder = tbgame.getTurnOrder();
			 PriorityQueue<Turn> turnOrderCopy = new PriorityQueue<>(turnOrder);
		        	        
			 System.out.print("\n\n*** Turn Order: ");
			 // Iterate through the copy to view elements without removing them
			 while (!turnOrderCopy.isEmpty()) {
				 Turn nextTurn = turnOrderCopy.poll();			 
				 System.out.print(nextTurn.getOwnerId()+" > ");	            
			 }
			 System.out.println("***\n");
		 }
		        
		 public void display(TBGame.Initializer initializer, ArrayList<Opponent> opponentList) {
			 System.out.println("Welcome to TBGame!");
			 System.out.println("\n");
			 displayInitialOpponents(opponentList);
			 ArrayList<Human<? extends Weapon>> humanList = initializer.createHumanList();
			 displayInitialCharacters(humanList);
			 System.out.println("\nThe Battle Starts!");
			 //
			 //buralara oyunun geri kalan menusu gelecek while döngüsü içinde (while all characters dead or run or opponents are dead)
			 //				
		 }		
			
		 private void displayInitialOpponents(ArrayList<Opponent> oppList) {
			 System.out.println("These opponents appeared in front of you:");
			 for(Opponent opponent : oppList) {
				 int opponentId = opponent.getOpponentId();
				 String opponentType = opponent.toString();
				 int opponentPoint = opponent.getPoints();
				 int opponentAttack = opponent.getAttack();
				 int opponentSpeed = opponent.getSpeed();
				 System.out.println("Id: "+ opponentId+", "+"Type: "+opponentType+", "+ "Points: "+ opponentPoint+", " +"Attack: "+opponentAttack+", "+ "Speed: "+opponentSpeed);
			 }
			 System.out.println(" ");			 
		 }
		 
		 private void displayInitialCharacters(ArrayList<Human<? extends Weapon>> humanList) {
			 int count = 1;
			 for (Human<? extends Weapon> human : humanList) {
				 String humanName = human.getName();
				 String humanJob= human.toString();
				 int humanPoint = human.getPoints();
				 int humanStamina = human.getStamina();
				 int humanAttack = human.getAttack();
				 int humanSpeed = human.getSpeed();
				 String humanWeapon = human.getWeapon().toString();
				 int humanWeaponAttack = human.getWeapon().getAdditionalAttack();
				 System.out.println("\nThe stats of your "+count+". "+"character:");
				 System.out.println(humanName+", "+"Job: "+humanJob+ ", "+"Points: "+humanPoint+", "+"Stamina: "+ humanStamina+", "+ "Attack: "+
						 			humanAttack+", "+ "Speed: "+humanSpeed+", "+"Weapon: "+ humanWeapon+" with "+"+"+humanWeaponAttack+" attack");
				 count++;
				 System.out.println();										
			 }
		 } 
	 }
}