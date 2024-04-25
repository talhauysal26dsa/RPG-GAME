package exceptionPackage;

import java.io.Serializable;

public class NotAUniqueNameException extends Exception implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public NotAUniqueNameException() {
		super("This name already taken!!!");
		// TODO Auto-generated constructor stub
	}
	
	public NotAUniqueNameException(String message) {
		super(message);
	}
}

