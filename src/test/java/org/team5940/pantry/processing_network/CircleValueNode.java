package org.team5940.pantry.processing_network;

import org.team5940.pantry.logging.loggers.Logger;

public class CircleValueNode<T> extends ValueNode<T> {

	ValueNode<T> setValueNode;

	public CircleValueNode(Network network, Logger logger, String label)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label);
	}

	public void setGetValue(ValueNode<T> setValueNode) {
		this.setValueNode = setValueNode;
	}

	@Override
	protected T updateValue() {
		if (this.setValueNode == null) {
			this.getLogger().throwError(this, new IllegalStateException("Network started without setValueNode set"));
		}
		return this.setValueNode.getValue();
	}

}
