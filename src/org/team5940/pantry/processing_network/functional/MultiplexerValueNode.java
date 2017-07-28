package org.team5940.pantry.processing_network.functional;

import java.util.Map;

import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ProcessingNetworkUtils;
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
public class MultiplexerValueNode<T extends Object, S extends Enum<S>> extends ValueNode<T> {

	/**
	 * A source that returns the current state which tells what the
	 * MultiplexerValueNode should return.
	 */
	ValueNode<Enum<? extends S>> stateSource;

	/**
	 * A map of different enum values corresponding to ValueNodes. This tells
	 * what this node should return based on what the enum from the stateSource.
	 */
	Map<Enum<? extends S>, ValueNode<? extends T>> valueSourcesMap;

	/**
	 * Creates a new MultiplexerValueNode. This returns the value from a
	 * ValueNode based on the corresponding enum in the valueSourcesMap from the
	 * stateSource. If the corresponding value in the valueSourcesMap does not
	 * exist this will return null. If the stateSource returns null as the enum
	 * this will return the value from the ValueNode corresponding to null in
	 * the map and if none exist it will return null as expected.
	 * 
	 * @param network
	 *            This Node's network.
	 * @param stateSource
	 *            The state to tell which corresponding value this node should
	 *            return.
	 * @param valueSourcesMap
	 *            A Map which tells what ValueNode value this should return
	 *            based on the the current enum the stateSource returns.
	 * @throws IllegalArgumentException
	 *             If stateSource or valueSourcesMap is null.
	 * @throws IllegalStateException
	 *             If the network is already running.
	 */
	public MultiplexerValueNode(Network network, ValueNode<Enum<? extends S>> stateSource,
			Map<Enum<? extends S>, ValueNode<? extends T>> valueSourcesMap)
			throws IllegalArgumentException, IllegalStateException {
		super(network, ProcessingNetworkUtils.concatValueNodes(stateSource, ProcessingNetworkUtils.valueNodesMapToArray(valueSourcesMap)));

		if (stateSource == null) {
			throw new IllegalArgumentException("Null State Source");
		}

		if (valueSourcesMap == null) {
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
