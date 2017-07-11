package org.team5940.pantry.processing_network;

/**
 * An exception to indicate that you are attempting to update a {@link Node} from a Thread other than its {@link Network}.
 * @author David Boles
 *
 */
public class IllegalUpdateThreadException extends Exception {
	
	private static final long serialVersionUID = 4200120984195754803L;

	/**
	 * Initialize a new {@link IllegalUpdateThreadException}.
	 */
	public IllegalUpdateThreadException() {
		
	}

	/**
	 * Initialize a new {@link IllegalUpdateThreadException} with a message.
	 * @param message The String of the message to include.
	 */
	public IllegalUpdateThreadException(String message) {
		super(message);
	}

	/**
	 * Initialize a new {@link IllegalUpdateThreadException} with a cause.
	 * @param cause The Throwable cause.
	 */
	public IllegalUpdateThreadException(Throwable cause) {
		super(cause);
	}

	/**
	 * Initialize a new {@link IllegalUpdateThreadException} with a message and a cause.
	 * @param message The String of the message to include.
	 * @param cause The Throwable cause.
	 */
	public IllegalUpdateThreadException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalUpdateThreadException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
