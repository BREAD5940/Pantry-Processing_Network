package org.team5940.pantry.processing_network.data_flow;

import java.util.Map;

import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.NodeUtils;
import org.team5940.pantry.processing_network.ValueNode;
import org.team5940.pantry.processing_network.data_flow.DataFlowNodesTest.TestEnum;

/**
 * A ValueNode that returns a value based on the state the Enum SourceNode is
 * in.
 * 
 * @author David Boles and Michael Bentley
 *
 * @param <T>
 *            The type of value this node returns. All of the SourceNodes this
 *            class accesses are of this type.
 * @param <S>
 *            The type of Enum this MultiplexerValueNode corresponds to.
 */
public class MultiplexerValueNode<T extends Object, S extends Enum<S>> extends ValueNode<T> {

	ValueNode<Enum<? extends S>> stateSource;
	Map<Enum<? extends S>, ValueNode<? extends T>> valueSourcesMap;

	public MultiplexerValueNode(Network network, ValueNode<Enum<? extends S>> stateSource,
			Map<Enum<? extends S>, ValueNode<? extends T>> valueSourcesMap)
			throws IllegalArgumentException, IllegalStateException {
		super(network, NodeUtils.concatValueNodes(stateSource, NodeUtils.valueNodesMapToArray(valueSourcesMap)));
		
		if(stateSource == null) {
			throw new IllegalArgumentException("Null State Source");
		}
		
		if(valueSourcesMap == null) {
			throw new IllegalArgumentException("Null Value Sources Map");
		}
		
		this.stateSource = stateSource;

		this.valueSourcesMap = valueSourcesMap;
	}

	@Override
	protected T updateValue() {
		Enum<? extends S> currentEnum = this.stateSource.getValue();
		ValueNode<? extends T> sourceNode = this.valueSourcesMap.get(currentEnum);
		return (sourceNode != null) ? sourceNode.getValue() : null;
	}
}
