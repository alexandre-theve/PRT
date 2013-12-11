package andrevent.server.jpacontroller.exceptions;

public class RollbackFailureException extends Exception {
    /**
	 * 
	 */
	private static final long serialVersionUID = 6375847980697451338L;
	
	public RollbackFailureException(String message, Throwable cause) {
        super(message, cause);
    }
    public RollbackFailureException(String message) {
        super(message);
    }
}
