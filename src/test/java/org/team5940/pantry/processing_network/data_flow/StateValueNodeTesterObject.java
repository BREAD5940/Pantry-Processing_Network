package org.team5940.pantry.processing_network.data_flow;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;
import org.team5940.pantry.processing_network.data_flow.MultiplexerValueNodeTest.TestEnum;

public class StateValueNodeTesterObject extends ValueNode<TestEnum> {

	int currentRun = -1;

	public StateValueNodeTesterObject(Network network, Logger logger, String label)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger, label);
	}

	@Override
	protected TestEnum updateValue() {
		currentRun++;
		if (currentRun == 0) {
			return TestEnum.TEST1;
		} else if (currentRun == 1) {
			return TestEnum.TEST2;
		} else if (currentRun == 2) {
			return null;
		} else {
			return TestEnum.TEST3;
		}
	}
}
