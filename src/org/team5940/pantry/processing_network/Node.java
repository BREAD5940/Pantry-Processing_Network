package org.team5940.pantry.processing_network;

import java.util.HashSet;
import java.util.Set;

import org.team5940.pantry.logging.LabeledObject;
import org.team5940.pantry.logging.LoggingUtils;
import org.team5940.pantry.logging.loggers.Logger;

import com.google.gson.JsonArray;

/**
 * A Node is a component of a single Network. Nodes perform some sort of
 * operation 0 or 1 time(s) every network cycle: the first time update() is
 * successfully called on a given network cycle.
 * 
 * @author Michael Bentley, David Boles
 * 
 */
public abstract class Node implements LabeledObject {

	// TODO rename network cycle naming.

	/**
	 * Stores this' {@link Network}.
	 */
	Network network;

	/**
	 * Stores the SourceNodes to return a copy of in enumerateSources().
	 */
	Set<ValueNode<?>> sources;

	/**
	 * Whether this requires an update by default.
	 */
	boolean requireUpdate;

	/**
	 * The last cycle this was updated.
	 */
	long lastUpdate = -1;

	/**
	 * The logger this uses. Shocking.
	 */
	Logger logger;

	/**
	 * Initialize a new Node.
	 * 
	 * @param network
	 *            The {@link Network} that this node is a part of.
	 * @param sources
	 *            The {@link ValueNode}s used by this, if null assumes no
	 *            sources.
	 * @param logger
	 *            The {@link Logger} for this to use.
	 * @param requireUpdate
	 *            Whether to, by default, require updates. The requiresUpdate()
	 *            method can always be overridden to change behavior.
	 * @throws IllegalArgumentException
	 *             network is null, or one of the {@link ValueNode}s in source
	 *             is null.
	 * @throws IllegalStateException
	 *             network has already been started.
	 */
	public Node(Network network, Logger logger, boolean requireUpdate, ValueNode<?>... sourcesArray)
			throws IllegalArgumentException, IllegalStateException {

		Set<ValueNode<?>> sources;

		if (sourcesArray == null) {
			sources = new HashSet<>();
		} else {
			sources = generateSourcesSet(sourcesArray);
		}

		if (logger == null) {
			throw new IllegalArgumentException("Logger is null");
		}

		this.logger = logger;

		if (network == null) {
			this.logger.throwError(this, new IllegalArgumentException("Null Network"));
		}

		if (network.getState() != Thread.State.NEW) {
			this.logger.throwError(this, new IllegalStateException("Network Already Started"));
		}

		for (ValueNode<?> sourceNode : sources) {
			if (sourceNode == null) {
				this.logger.throwError(this, new IllegalArgumentException("SourceNode is Null"));
			}
		}

		this.sources = sources;
		this.network = network;
		this.requireUpdate = requireUpdate;
		this.network.addNode(this);

	}

	/**
	 * Creates a set of sources from varargs. This is used to make setting
	 * sources easier when using super constructor of node.
	 * 
	 * @param sourcesArray
	 *            The array of different sources the node is using.
	 * @return A set of sources identical to the inputed array.
	 */
	Set<ValueNode<?>> generateSourcesSet(ValueNode<?>... sourcesArray) {
		Set<ValueNode<?>> sources = new HashSet<ValueNode<?>>();
		for (ValueNode<?> source : sourcesArray) {
			if (source != null) {
				sources.add(source);
			}
		}
		return sources;
	}

	/**
	 * Internal method for performing the node's update. It can only be called 0
	 * or 1 time(s) per Network cycle.
	 */
	protected abstract void doUpdate();

	/**
	 * Updates this if it has not yet been updated the current network cycle.
	 * 
	 * @throws IllegalUpdateThreadException
	 *             The thread calling this method is not this' network.
	 */
	public final void update() throws IllegalUpdateThreadException {
		if (this.network == Thread.currentThread()) {
			updateIfNotYetUpdated();
		} else {
			throw new IllegalUpdateThreadException("Not MY Network: " + Thread.currentThread().toString());
		}
	}

	/**
	 * Updates the network if it has not been updated already.
	 */
	private void updateIfNotYetUpdated() {
		if (this.network.getLastCycle() != this.lastUpdate && this.network.getLastCycle() != -1) {
			this.doUpdate();

			// lastUpdate only changes after update completes.
			this.lastUpdate = this.network.getLastCycle();
		}
	}

	/**
	 * This method is used to tell the network whether the node requires an
	 * update (update() will be definitely be called this cycle). It should NOT
	 * be assumed that this method will only be called once per cycle and the
	 * value should not change in successive calls in the same cycle.
	 * 
	 * @return True if this node should be updated this cycle, false if it can
	 *         be not updated.
	 */
	public boolean requiresUpdate() {
		return this.requireUpdate;
	}

	/**
	 * Gets the {@link ValueNode}s (within its network) that this Node retrieves
	 * data from. Sources should not change after initialization. The set
	 * returned must be clone and not any internally used reference. If this
	 * does not have any sources it should return an empty Set, NOT null.
	 * 
	 * @return A Set containing any Nodes that this uses.
	 */
	public Set<ValueNode<?>> enumerateSources() {
		HashSet<ValueNode<?>> out = new HashSet<>();
		synchronized (this.sources) {
			this.sources.forEach(node -> out.add(node));
		}
		return out;
	}

	/**
	 * Gets this' {@link Network}.
	 * 
	 * @return This' Network.
	 */
	public Network getNetwork() {
		return this.network;
	}

	/**
	 * Gets the last cycle that this completed an update. If this method is
	 * called in the middle of an node update it will return the last cycle
	 * before this one that it updated. If it has never been updated it will
	 * return -1.
	 * 
	 * @return The last cycle this node updated.
	 * @throws IllegalStateException
	 *             If this node's network has not started.
	 */
	public long getLastUpdateCycle() throws IllegalStateException {
		if (!this.getNetwork().isAlive())
			throw new IllegalStateException("Network has not started");
		return this.lastUpdate;
	}
	
	@Override
	public JsonArray getLabel() {
		// TODO Find way to force children to override this method. 
		return LoggingUtils.chainPut(new JsonArray(), "Node");
	}

}
