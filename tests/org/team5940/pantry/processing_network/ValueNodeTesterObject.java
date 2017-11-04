package org.team5940.pantry.processing_network;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;

public class ValueNodeTesterObject extends ValueNode<Integer> {
	
	boolean requiresUpdate;

	public ValueNodeTesterObject(Network network, Logger logger, boolean requireUpdate, ValueNode<?>... sources)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger, sources);
		this.requiresUpdate = requireUpdate;
	}

	@Override
	public Integer updateValue() {
		// For Testing Purposes
		int ret = 3;
		return ret;
	}
	
	@Override
	public boolean requiresUpdate() {
		return this.requiresUpdate;
	}
}