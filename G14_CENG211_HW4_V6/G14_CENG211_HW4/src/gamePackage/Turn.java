package gamePackage;

public class Turn {	
	private String ownerId;
    private double attackModifier;
    
    public Turn(String ownerId, double attackModifier) {
    	this.ownerId= ownerId;
    	this.attackModifier = attackModifier;
    }
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
	public double getAttackModifier() {
		return attackModifier;
	}
	public void setAttackModifier(double attackModifier) {
		this.attackModifier = attackModifier;
	}

}
