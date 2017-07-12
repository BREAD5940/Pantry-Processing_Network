package org.team5940.pantry.processing_network;

import java.util.HashSet;
import java.util.Set;

/**
 * A Node is a component of a single Network. Nodes perform some sort of operation 0 or 1 time(s) every network cycle: the first time update() is successfully called on a given network cycle.
 * @author Michael Bentley, David Boles
 * 
 */
public abstract class Node {

	/**
	 * Stores this' {@link Network}.
	 */
	Network network;
	
	/**
	 * Stores the SourceNodes to return a copy of in enumerateSources().
	 */
	Set<SourceNode<?>> sources;
	
	/**
	 * Whether this requires an update by default.
	 */
	boolean requireUpdate;
	
	/**
	 * The last cycle this was updated.
	 */
	long lastUpdate = -1;
	
	/**
	 * Initialize a new Node.
	 * @param network The {@link Network} that this node is a part of.
	 * @param sources The {@link SourceNode}s used by this, if null assumes no sources.
	 * @param requireUpdate Whether to, by default, require updates. The requiresUpdate() method can always be overridden to change behavior.
	 * @throws IllegalArgumentException network is null, or one of the {@link SourceNode}s in source is null.
	 * @throws IllegalStateException network has already been started.
	 */
	public Node(Network network, Set<SourceNode<?>> sources, boolean requireUpdate) throws IllegalArgumentException, IllegalStateException {
		
		sources = checkNodeForErrors(network, sources);
		
		this.sources = sources;
		this.network = network;
		this.requireUpdate = requireUpdate;
		this.network.addNode(this);
		
		
	}

	private Set<SourceNode<?>> checkNodeForErrors(Network network, Set<SourceNode<?>> sources)
			throws IllegalArgumentException, IllegalStateException {
		if(network == null) {
			//TODO log
			throw new IllegalArgumentException("Null Network");
		}
		
		if(network.getState() != Thread.State.NEW) {
			//TODO log
			throw new IllegalStateException("Network Already Started");
		}
		
		if(sources == null) {
			sources = new HashSet<>();
		}
		
		for(SourceNode<?> sourceNode:sources) {
			if(sourceNode == null) {
				throw new IllegalArgumentException("SourceNode is Null");
			}
		}
		return sources;
	}
	
	/**
	 * Internal method for performing the node's update. It can only be called 0 or 1 time(s) per Network cycle.
	 */
	protected abstract void doUpdate();

	/**
	 * Updates this if it has not yet been updated the current network cycle.
	 * @throws IllegalUpdateThreadException The thread calling this method is not this' network.
	 */
	public final void update() throws IllegalUpdateThreadException {
		if(this.network == Thread.currentThread()) {
			updateNodeIfNotYetUpdatedThisCycle();
		}else {
			throw new IllegalUpdateThreadException("Not MY Network: " + Thread.currentThread().toString());
		}
	}

	private void updateNodeIfNotYetUpdatedThisCycle() {
		if(this.network.getLastCycle() != this.lastUpdate) {//They would both be -1 if the network hasn't been started but it shouldn't get there without it being started due to the previous line.
			this.doUpdate();
			this.lastUpdate = this.network.getLastCycle();//lastUpdate only changes after update completes.
		}
	}
	
	/**
	 * This method is used to tell the network whether the node requires an update (update() will be definitely be called this cycle). It should NOT be assumed that this method will only be called once per cycle and the value should not change in successive calls in the same cycle.
	 * @return True if this node should be updated this cycle, false if it can be not updated.
	 */
	public boolean requiresUpdate() {
		return this.requireUpdate;
	}

	/**
	 * Gets the Nodes that this Node retrieves data from. Sources should not change after initialization. The set returned must be clone and not any internally used reference. If this does not have any sources it should return an empty Set, NOT null.
	 * @return A Set containing any Nodes that this uses.
	 */
	public Set<SourceNode<?>> enumerateSources() {
		HashSet<SourceNode<?>> out = new HashSet<>();
		synchronized (this.sources) {
				this.sources.forEach(node -> out.add(node));
		}
		return out;
	}
	
	/**
	 * Gets this' {@link Network}.
	 * @return This' Network.
	 */
	public Network getNetwork() {
		return this.network;
	}
	
	/**
	 * Gets the last cycle that this completed an update. If this method is called in the middle of an node update it will return the last cycle before this one that it updated. If it has never been updated it will return -1.
	 * @return The last cycle this node updated.
	 */
	public long getLastUpdateCycle() {
		return this.lastUpdate;
	}
	
}
