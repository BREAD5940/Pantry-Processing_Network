package org.team5940.pantry.processing_network;

import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;

public class SourceNodeTesterObject extends ValueNode<Integer> {
	
	boolean requiresUpdate;

	public SourceNodeTesterObject(Network network, boolean requireUpdate, ValueNode<?> source)
			throws IllegalArgumentException, IllegalStateException {
		super(network, source);
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