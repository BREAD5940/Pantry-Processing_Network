package org.team5940.pantry.processing_network.data_flow;

import java.util.Map;

import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.NodeUtils;
import org.team5940.pantry.processing_network.ValueNode;

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
public class MultiplexerValueNode<T, S> extends ValueNode<T> {

	ValueNode<Enum<? extends S>> stateSource;
	Map<Enum<? extends S>, ValueNode<? extends T>> valueSourcesMap;

	public MultiplexerValueNode(Network network, ValueNode<Enum<? extends S>> stateSource,
			Map<Enum<? extends S>, ValueNode<? extends T>> valueSourcesMap)
			throws IllegalArgumentException, IllegalStateException {
		super(network, NodeUtils.mergeArrays(new ValueNode[] { stateSource },
				valueSourcesMap.values().toArray(new ValueNode[valueSourcesMap.size()])));
		this.stateSource = stateSource;
		this.valueSourcesMap = valueSourcesMap;
	}

	@Override
	protected T updateValue() {
		Enum<? extends S> currentEnum = this.stateSource.getValue();
		if (currentEnum == null)
			return null;
		ValueNode<? extends T> sourceNode = this.valueSourcesMap.get(currentEnum);
		return (sourceNode != null) ? sourceNode.getValue() : null;
	}
}
