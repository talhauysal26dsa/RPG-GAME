package exceptionPackage;

import java.io.Serializable;

public class SpecialAlreadyUsedException extends Exception implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public SpecialAlreadyUsedException() {
		// TODO Auto-generated constructor stub
		super("Special skill already used");
	}
	public SpecialAlreadyUsedException(String message) {
		super(message);
		
		
	}
	
}
