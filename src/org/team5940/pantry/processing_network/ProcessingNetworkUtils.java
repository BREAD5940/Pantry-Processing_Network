package org.team5940.pantry.processing_network;

import java.util.Collection;
import java.util.Map;

/**
 * Various utilities that Nodes may need to use.
 * 
 * @author Michael Bentley
 *
 */
public class ProcessingNetworkUtils {

	/**
	 * This is a static utility method for constructing the array of value nodes
	 * that is passed to the Node class on initialization of a subclass. If any
	 * node passed in is null, the method simply returns a new empty array,
	 * leaving the checking and exception throwing to you (the person writing a
	 * constructor).
	 * 
	 * @param node
	 *            A {@link ValueNode} to add to the returned array.
	 * @param nodes
	 *            {@link ValueNode}(s) to add to the returned array.
	 * @return An array of {@link ValueNode}s containing node and the contents
	 *         of nodes unless either of them was/ contained null (in which case
	 *         an empty array).
	 */
	public static ValueNode<?>[] concatValueNodes(ValueNode<?> node, ValueNode<?>... nodes) {
		if (node != null && nodes != null) {
			for (ValueNode<?> containedNode : nodes) {
				if (containedNode == null) {
					return new ValueNode<?>[0];
				}
			}

			ValueNode<?>[] out = new ValueNode<?>[nodes.length + 1];

			out[0] = node;
			for (int i = 1; i < out.length; i++) {
				out[i] = nodes[i - 1];
			}

			return out;
		} else {
			return new ValueNode<?>[0];
		}
	}

	/**
	 * This is a static utility method for constructing the array of value nodes
	 * that is passed to the Node class on initialization of a subclass. If any
	 * node passed in is null, the method simply returns a new empty array,
	 * leaving the checking and exception throwing to you (the person writing a
	 * constructor).
	 * 
	 * @param nodeArray
	 *            A {@link ValueNode}s to add to the returned array.
	 * @param nodes
	 *            {@link ValueNode}(s) to add to the returned array.
	 * @return An array of {@link ValueNode}s containing the contents of
	 *         nodeArray and nodes unless either of them was/ contained null (in
	 *         which case an empty array).
	 */
	public static ValueNode<?>[] concatValueNodes(ValueNode<?>[] nodeArray, ValueNode<?>... nodes) {
		if (nodeArray != null && nodes != null) {
			for (ValueNode<?> containedNode : nodeArray) {
				if (containedNode == null) {
					return new ValueNode<?>[0];
				}
			}

			for (ValueNode<?> containedNode : nodes) {
				if (containedNode == null) {
					return new ValueNode<?>[0];
				}
			}

			ValueNode<?>[] out = new ValueNode<?>[nodeArray.length + nodes.length];

			for (int i = 0; i < nodeArray.length; i++) {
				out[i] = nodeArray[i];
			}
			for (int i = nodeArray.length; i < nodeArray.length + nodes.length; i++) {
				out[i] = nodes[i - nodeArray.length];
			}

			return out;
		} else {
			return new ValueNode<?>[0];
		}
	}

	/**
	 * This is a static utility method for constructing the array of value nodes
	 * that is passed to the Node class on initialization of a subclass. If any
	 * node passed in is null or the collection is null, the method simply
	 * returns a new empty array, leaving the checking and exception throwing to
	 * you (the person writing a constructor).
	 * 
	 * @param nodes
	 *            A collection of nodes to convert into an array.
	 * @return An array containing the contents of nodes unless it or it's
	 *         contents are null in which case a new empty array.
	 */
	public static ValueNode<?>[] valueNodesToArray(Collection<? extends ValueNode<?>> nodes) {
		if (nodes == null) {
			return new ValueNode<?>[0];
		}
		for (ValueNode<?> node : nodes) {
			if (node == null) {
				return new ValueNode<?>[0];
			}
		}
		return nodes.toArray(new ValueNode<?>[nodes.size()]);
	}

	/**
	 * This is a static utility method for constructing the array of value nodes
	 * that is passed to the Node class on initialization of a subclass. If any
	 * node passed in is null or the collection is null, the method simply
	 * returns a new empty array, leaving the checking and exception throwing to
	 * you (the person writing a constructor).
	 * 
	 * @param nodes
	 *            A map of containing {@link ValueNode}s as it's values to be
	 *            converted to an array. THE KEYS ARE NOT CONVERTED!
	 * @return An array containing the contents of nodes unless it or it's
	 *         contents are null in which case a new empty array.
	 */
	public static ValueNode<?>[] valueNodesMapToArray(Map<? extends Object, ? extends ValueNode<?>> nodes) {
		if (nodes == null) {
			return new ValueNode<?>[0];
		}
		return valueNodesToArray(nodes.values());
	}

	/**
	 * Checks an argument to see if it is null. Should only be used for checking
	 * constructor arguments for nodes.
	 * 
	 * @param argument
	 *            The argument to check if null.
	 * @throws IllegalArgumentException
	 *             If the argument is null.
	 */
	public static void checkArgument(Object argument) throws IllegalArgumentException {
		if (argument == null) {
			throw new IllegalArgumentException("Null Argument");
		}
	}

	/**
	 * Checks array to see if it is null, and then checks each of its individual
	 * values to see if it is null. Should only be used in constructor.
	 * 
	 * @param arguments
	 *            Array to check if it or any of its values are null.
	 * @throws IllegalArgumentException
	 *             Throws if any value is null.
	 */
	public static void checkArrayArguments(Object[] arguments) throws IllegalArgumentException {
		checkArgument(arguments);
		for (Object argument : arguments) {
			if (argument == null) {
				throw new IllegalArgumentException("Array Argument Null");
			}
		}
	}
}
