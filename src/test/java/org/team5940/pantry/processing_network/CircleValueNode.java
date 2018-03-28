package org.team5940.pantry.processing_network;

import org.team5940.pantry.logging.loggers.Logger;

public class CircleValueNode<T> extends ValueNode<T> {

	ValueNode<? extends T> setValueNode;
	T intialValue;
	boolean firstUpdate = true;

	public CircleValueNode(Network network, Logger logger, String label, T intialValue)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label);
		this.intialValue = intialValue;
	}

	public void setGetValue(ValueNode<? extends T> setValueNode) {
		System.out.println("Set Get Value: " + setValueNode.getLabel().toString());
		if (setValueNode == this) {
			this.getLogger().throwError(this, new IllegalArgumentException("Cannot set setValueNode to itself"));
		}
		this.setValueNode = setValueNode;
	}

	@Override
	protected T updateValue() {
		if (this.setValueNode == null) {
			this.getLogger().throwError(this, new IllegalStateException("Network started without setValueNode set"));
		}
		if (firstUpdate) {
			firstUpdate = false;
			return intialValue;
		}
		// TODO this is sketchy. Should replace.
		this.lastUpdate = this.getNetwork().getLastCycle();
		System.out.println("Circle Value: " + this.setValueNode.getValue());
		// System.out.println("Set Value Value: " + this.setValueNode.getValue());
		return this.setValueNode.getValue();
	}

}
