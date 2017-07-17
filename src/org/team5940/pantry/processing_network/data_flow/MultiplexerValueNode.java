package org.team5940.pantry.processing_network.data_flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.SourceNode;

public class MultiplexerValueNode<T, S> extends SourceNode<T> {

	public MultiplexerValueNode(Network network, SourceNode<Enum<? extends S>> stateSource, SourceNode<? extends T>... valueSources)
			throws IllegalArgumentException, IllegalStateException {
		super(network, false, );
		
	}

	@Override
	protected T updateValue() {
		return null;
	}
	
}
