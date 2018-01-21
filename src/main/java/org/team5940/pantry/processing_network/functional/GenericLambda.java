package org.team5940.pantry.processing_network.functional;

/**
 * Used as a Lambda expression with a single generic argument.
 * 
 * @author Michael Bentley
 *
 * @param <T>
 *            The type of argument for the lambda method.
 */
public interface GenericLambda<T> {

	/**
	 * The Lambda method.
	 * 
	 * @param argument
	 *            The argument of type T
	 */
	public void lambda(T argument);

}
