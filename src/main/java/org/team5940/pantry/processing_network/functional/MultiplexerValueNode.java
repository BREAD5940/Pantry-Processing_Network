package org.team5940.pantry.processing_network.functional;

import java.util.Map;

import org.team5940.pantry.logging.LoggingUtils;
import org.team5940.pantry.logging.loggers.Logger;
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
	 * A map of different enum values corresponding to ValueNodes. This tells what
	 * this node should return based on what the enum from the stateSource.
	 */
	Map<Enum<? extends S>, ValueNode<? extends T>> valueSourcesMap;

	/**
	 * The default value to return when there is no node corresponding to the enum
	 * value.
	 */
	ValueNode<? extends T> defaultValueNode;

	/**
	 * Creates a new MultiplexerValueNode. This returns the value from a ValueNode
	 * based on the corresponding enum in the valueSourcesMap from the stateSource.
	 * If the corresponding value in the valueSourcesMap does not exist this will
	 * return the default ValueNode value. If the stateSource returns null as the
	 * enum this will return the value from the ValueNode corresponding to null in
	 * the map and if none exist it will return the default ValueNode value as
	 * expected.
	 * 
	 * @param network
	 *            This Node's network.
	 * @param logger
	 *            This' Logger
	 * @param stateSource
	 *            The state to tell which corresponding value this node should
	 *            return.
	 * @param valueSourcesMap
	 *            A Map which tells what ValueNode value this should return based on
	 *            the the current enum the stateSource returns.
	 * @param defaultValueNode
	 *            The value to return when there is no node corresponding to the
	 *            current enum value.
	 * @throws IllegalArgumentException
	 *             If stateSource or valueSourcesMap is null.
	 * @throws IllegalStateException
	 *             If the network is already running.
	 */
	public MultiplexerValueNode(Network network, Logger logger, String label, ValueNode<Enum<? extends S>> stateSource,
			Map<Enum<? extends S>, ValueNode<? extends T>> valueSourcesMap, ValueNode<? extends T> defaultValueNode)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label, ProcessingNetworkUtils.concatValueNodes(
				ProcessingNetworkUtils.valueNodesMapToArray(valueSourcesMap), stateSource, defaultValueNode));

		LoggingUtils.checkArgument(valueSourcesMap);

		this.stateSource = stateSource;
		this.valueSourcesMap = valueSourcesMap;
		this.defaultValueNode = defaultValueNode;
	}

	/**
	 * Creates a new MultiplexerValueNode. This returns the value from a ValueNode
	 * based on the corresponding enum in the valueSourcesMap from the stateSource.
	 * If the corresponding value in the valueSourcesMap does not exist this will
	 * return the default ValueNode value. If the stateSource returns null as the
	 * enum this will return the value from the ValueNode corresponding to null in
	 * the map and if none exist it will return the default ValueNode value as
	 * expected.
	 * 
	 * @param network
	 *            This' Network
	 * @param logger
	 *            This' Logger
	 * @param stateSource
	 *            The state to tell which corresponding value this node should
	 *            return.
	 * @param valueSourcesMap
	 *            A Map which tells what ValueNode value this should return based on
	 *            the the current enum the stateSource returns.
	 * @param defaultValue
	 *            The value to return when there is no node corresponding to the
	 *            current enum value. This will create a ConstantValueNode from the
	 *            value to use. The label will be "Multiplexer Constant Default
	 *            Value: " + label.
	 */
	public MultiplexerValueNode(Network network, Logger logger, String label, ValueNode<Enum<? extends S>> stateSource,
			Map<Enum<? extends S>, ValueNode<? extends T>> valueSourcesMap, T defaultValue) {
		this(network, logger, label, stateSource, valueSourcesMap,
				new ConstantValueNode<>(network, logger, "Multiplexer Constant Default Value: " + label, defaultValue));
		
		LoggingUtils.checkArgument(valueSourcesMap);
	}

	@Override
	protected T updateValue() {
		Enum<? extends S> currentEnum = this.stateSource.getValue();
		ValueNode<? extends T> sourceNode = this.valueSourcesMap.get(currentEnum);
		return (sourceNode != null) ? sourceNode.getValue() : this.defaultValueNode.getValue();
	}
}
