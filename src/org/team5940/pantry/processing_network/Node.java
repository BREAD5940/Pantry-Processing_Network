package org.team5940.pantry.processing_network;

import java.util.Collection;

/**
 * 
 * @author mbent A node is an element in {@link Network} which is updated every
 *         cycle.
 */
public abstract class Node {

	/**
	 * Update the node. Run 0 - 1 times during a Network cycle.
	 */
	protected abstract void doUpdate();

	/**
	 * Checks if this Node has been updated during the Network cycle yet and if
	 * not it runs doUpdate().
	 */
	public final void update() {

	}

	/**
	 * Gets the Nodes that this Node retrieves data from. Sources should not
	 * change after initialization.
	 * 
	 * @return The Nodes that this uses.
	 */
	public abstract Collection<Node> enumerateSources();
}
