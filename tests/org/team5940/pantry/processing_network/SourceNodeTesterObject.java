package org.team5940.pantry.processing_network;

import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.SourceNode;

public class SourceNodeTesterObject extends SourceNode<Integer> {

	public SourceNodeTesterObject(Network network, boolean requireUpdate, SourceNode<?> source)
			throws IllegalArgumentException, IllegalStateException {
		super(network, requireUpdate, source);
	}

	@Override
	public Integer updateValue() {
		// For Testing Purposes
		int ret = 3;
		return ret;
	}
}