package org.team5940.pantry.processing_network.functional;

import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;

/**
 * A node that returns a constant value of type T. 
 * @author Michael Bentley
 *
 * @param <T> The type of value this node returns. 
 */
public class ConstantValueNode<T> extends ValueNode<T> {
	
	/**
	 * The value this node always returns. 
	 */
	final T value;
	
	/**
	 * Creates a Node which returns a constant value of type T. 
	 * @param network The network this node belongs to. 
	 * @param value The value this node always returns. 
	 */
	public ConstantValueNode(Network network, T value)
			throws IllegalArgumentException, IllegalStateException {
		super(network);
		
		this.value = value;
	}

	@Override
	public T getValue() {
		return value;
	}

	@Override
	protected T updateValue() {
		// TODO Auto-generated method stub
		return null;
	}
}
