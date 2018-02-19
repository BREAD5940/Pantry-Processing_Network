package org.team5940.pantry.processing_network;

import java.util.Set;

import com.google.gson.JsonArray;
import org.team5940.pantry.logging.LabeledObject;
import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.logging.messages.events.ErrorEventMessage;
import org.team5940.pantry.logging.messages.values.NumberValueMessage;

import java.util.HashSet;

/**
 * A Network is a thread that updates a set of {@link Node}s. <br>
 * <br>
 * Timing on a network is done in cycles where internally events should appear
 * to occur simultaneously (code running within a single cycle should not act
 * differently because of time passing during that cycle). Once a network is
 * started, cycles occur every cycleDelay, some number of microseconds
 * (1/1,000,000 second). Due to technical limitations (specifically the amount
 * of time it actually takes a network to complete a cycle and potentially the
 * accuracy of System.nanoTime()) cycles may occur more slowly than specified
 * but they will never occur more quickly. For timing needs inside of Nodes,
 * {@link Network#getLastCycleStart()} is available. <br>
 * <br>
 * Nodes are added to a network automatically when they are initialized (as long
 * as the network has not already been started). Nodes must declare on
 * initialization any {@link ValueNode}s they utilize which forces them to form
 * a directed acyclic graph: no "looping" dependencies are present. This is
 * necessary due to the "internally instantaneous" nature of network cycles, you
 * cannot have operations that (indirectly) rely on themselves: the cycle would
 * never complete. <br>
 * <br>
 * Nodes can perform some sort of operation up to once every cycle, when their
 * doUpdate method is internally called. A node is manually updated (if they
 * haven't already been) by the network on a cycle if node.requiresUpdate()
 * returns true. If updating the node returns an exception it is caught, the
 * network continues to try to function. Value nodes are also updated when
 * another node calls node.getValue(). This system of updating values within
 * cycles is advantageous because code using the values is guaranteed an
 * up-to-date value (to within this cycle) but the value can be buffered (not
 * recalculated, saving computer resources) because it is guaranteed not to
 * change.
 * 
 * @author Michael Bentley, David Boles
 * 
 */
public class Network extends Thread implements LabeledObject {

	/**
	 * The lowest number of microseconds between the start of cycles for this
	 * Network.
	 */
	long cycleDelay;

	/**
	 * The Nodes that this Network updates.
	 */
	Set<Node> nodes;

	/**
	 * The current cycle this Network is on.
	 */
	long currentCycle = 0;

	/**
	 * The value of System.nanoTime()/1000 when this is first started.
	 */
	long startTime;

	/**
	 * The value of System.nanoTime()/1000 when the last (potentially currently
	 * executing) cycle started.
	 */
	long lastCycleStart;

	/**
	 * The logger that is used to log messages.
	 */
	Logger logger;

	/**
	 * Creates a new Network with the given cycle delay.
	 * 
	 * @param cycleDelay
	 *            The minimum number of microseconds between the beginning of
	 *            cycles. Must be greater than or equal to 0.
	 */
	public Network(long cycleDelay, Logger logger) {
		if (logger == null) {
			throw new IllegalArgumentException("Logger is null");
		}
		this.logger = logger;
		if (cycleDelay < 0) {
			this.logger.throwError(this, new IllegalArgumentException("Illegal Value For cycleDelay"));
		}
		this.cycleDelay = cycleDelay;
		this.nodes = new HashSet<>();
	}

	@Override
	public void run() {
		this.startTime = microTime();
		while (!this.isInterrupted()) {
			// Starts cycle.
			this.lastCycleStart = microTime();
			this.currentCycle++;
			// Updates Nodes.
			for (Node node : this.nodes) {
				if (node.requiresUpdate()) {
					try {
						node.update();
					} catch (IllegalUpdateThreadException e) {
						this.logger.log(new ErrorEventMessage(this, e));
					}
				}
			}
			// Ends cycle and delays extra time.
			long cycleEndTime = microTime();
			long extraTime = this.cycleDelay - (cycleEndTime - this.lastCycleStart);

			if (extraTime > 0) {
				try {
					Thread.sleep(Math.floorDiv(extraTime, 1000), (int) Math.floorMod(extraTime, 1000));
				} catch (InterruptedException e) {
					this.logger.log(new ErrorEventMessage(this, e));
				}
			} else {
				this.logger.log(new NumberValueMessage(this, extraTime, NumberValueMessage.MICROSECONDS_UNIT));
			}
		}
	}

	/**
	 * Adds a Node for this Network.
	 * 
	 * @param node
	 *            The Node to add to this Network.
	 * @throws IllegalStateException
	 *             the Network has already been started.
	 * @throws IllegalArgumentException
	 *             node is null, this isn't its network, or it enumerates sources
	 *             that aren't a part of this network.
	 */
	protected synchronized void addNode(Node node) throws IllegalStateException, IllegalArgumentException {
		argumentChecksForAddNode(node);
		this.nodes.add(node);
	}

	private void argumentChecksForAddNode(Node node) throws IllegalStateException, IllegalArgumentException {
		if (this.getState() != Thread.State.NEW) {
			this.logger.throwError(this, new IllegalStateException("Network Already Started"));
		}
		if (node == null) {
			this.logger.throwError(this, new IllegalArgumentException("Null Node"));
		}
		if (node.getNetwork() != this) {
			this.logger.throwError(this, new IllegalArgumentException("Wrong Node Network"));
		}
		for (Node source : node.enumerateSources()) {
			if (!this.nodes.contains(source)) {
				this.logger.throwError(this, new IllegalArgumentException("Node Source Not In Network"));
			}
		}
	}

	/**
	 * Gets the cycle delay of the network.
	 * 
	 * @return The minimum number of microseconds between the start of cycles.
	 */
	public long getCycleDelay() {
		return this.cycleDelay;
	}

	/**
	 * Gets the last cycle this network started (potentially a currently executing
	 * cycle).
	 * 
	 * @return The current cycle this Network is on.
	 * @throws IllegalStateException
	 *             if the network has not been started.
	 */
	public long getLastCycle() {
		if (this.isAlive()) {
			return currentCycle;
		} else {
			this.logger.throwError(this, new IllegalStateException("Network not started"));
			return 0;
		}
	}

	/**
	 * Gets the time in microseconds from when the network started to the start of
	 * the last cycle (potentially a currently executing cycle).
	 * 
	 * @return Microseconds since network start to last cycle start.
	 * @throws IllegalStateException
	 *             if the network has not been started.
	 */
	public long getLastCycleStart() {
		if (this.isAlive()) {
			return this.lastCycleStart - this.startTime;
		} else {
			this.logger.throwError(this, new IllegalStateException("Network not started"));
			return 0;
		}
	}
	

	/**
	 * Internal method for getting System.nanoTime() as microseconds.
	 * 
	 * @return System.nanoTime()/1000
	 */
	private long microTime() {
		return System.nanoTime() / 1000;
	}

	@Override
	public JsonArray getLabel() {
		JsonArray label = new JsonArray();
		label.add("network");
		return label;
	}
}
