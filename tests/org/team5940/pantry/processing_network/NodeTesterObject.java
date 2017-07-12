package org.team5940.pantry.processing_network;

import java.util.Set;

import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.Node;
import org.team5940.pantry.processing_network.SourceNode;

public class NodeTesterObject extends Node{

	boolean didRun = false;
	
	public NodeTesterObject(Network network, Set<SourceNode<?>> sources, boolean requireUpdate)
			throws IllegalArgumentException, IllegalStateException {
		super(network, sources, requireUpdate);
	}

	@Override
	protected void doUpdate() {
		didRun = true;
	}

}