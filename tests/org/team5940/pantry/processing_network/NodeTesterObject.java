package org.team5940.pantry.processing_network;

import java.util.Set;

import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.Node;
import org.team5940.pantry.processing_network.ValueNode;

public class NodeTesterObject extends Node{

	int updateCount = 0;
	ValueNode<?> source;
	
	public NodeTesterObject(Network network, boolean requireUpdate, ValueNode<?> source)
			throws IllegalArgumentException, IllegalStateException {
		super(network, requireUpdate, source);
		this.source = source;
	}

	@Override
	protected void doUpdate() {
		this.updateCount++;
		if (this.source != null)
			source.getValue();
	}
	
	public boolean getDidRun() {
		return (this.updateCount > 0);
	}
	
	public int getUpdateCount() {
		return this.updateCount;
	}
}