package exceptionPackage;

import java.io.Serializable;

public class InsufficientStaminaException extends Exception implements Serializable{

	private static final long serialVersionUID = 1L;//? make compiler happy !!!!!!!!!!!
	public InsufficientStaminaException() {
		super("Insufficient Stamina!");
	}
	public InsufficientStaminaException(String message) {
		super(message);
	}
	

}
