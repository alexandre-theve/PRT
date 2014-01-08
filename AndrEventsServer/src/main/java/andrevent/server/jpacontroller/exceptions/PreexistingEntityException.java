package andrevent.server.jpacontroller.exceptions;

public class PreexistingEntityException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5001681462992991601L;
	
	public PreexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public PreexistingEntityException(String message) {
        super(message);
    }
}
