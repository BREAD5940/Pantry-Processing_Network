package org.team5940.pantry.processing_network.data_flow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.SourceNode;

import com.sun.xml.internal.bind.v2.model.util.ArrayInfoUtil;

public class MultiplexerValueNode<T, S> extends SourceNode<T> {

	public MultiplexerValueNode(Network network, SourceNode<Enum<? extends S>> stateSource,
			SourceNode<? extends T>... valueSources) throws IllegalArgumentException, IllegalStateException {
		super(network, NodeUtils.mergeArrays(new SourceNode[] { stateSource }, valueSources));
	}

	@Override
	protected T updateValue() {
		return null;
	}
}
