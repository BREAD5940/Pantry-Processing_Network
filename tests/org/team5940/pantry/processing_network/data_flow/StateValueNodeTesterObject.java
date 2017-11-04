package org.team5940.pantry.processing_network.data_flow;

import org.team5940.pantry.logging.loggers.Logger;
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;
import org.team5940.pantry.processing_network.data_flow.MultiplexerValueNodeTest.TestEnum;

public class StateValueNodeTesterObject extends ValueNode<Enum<? extends TestEnum>> {
	
	int currentRun = -1;

	public StateValueNodeTesterObject(Network network, Logger logger)
			throws IllegalArgumentException, IllegalStateException {
		super(network, logger);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected Enum<? extends TestEnum> updateValue() {
		currentRun++;
		if (currentRun == 0) {
			return TestEnum.TEST1;
		}
		else if (currentRun == 1) {
			return TestEnum.TEST2;
		}
		else if (currentRun == 2) {
			return null;
		}
		else {
			return TestEnum.TEST3;
		}
	}
}
