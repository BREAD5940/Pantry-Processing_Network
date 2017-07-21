package org.team5940.pantry.processing_network.data_flow;

import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;
import org.team5940.pantry.processing_network.data_flow.DataFlowNodesTest.TestEnum;

public class StateValueNodeTesterObject extends ValueNode<Enum<? extends TestEnum>> {
	
	int currentRun = -1;

	public StateValueNodeTesterObject(Network network)
			throws IllegalArgumentException, IllegalStateException {
		super(network);
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
		else 
			return null;
	}
}
