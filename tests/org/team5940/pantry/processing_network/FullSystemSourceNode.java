package org.team5940.pantry.processing_network;

import java.util.Set;

public class FullSystemSourceNode extends SourceNode<Integer> {

	public FullSystemSourceNode(Network network, boolean requireUpdate, SourceNode<?> source)
			throws IllegalArgumentException, IllegalStateException {
		super(network, requireUpdate, source);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	  public Integer updateValue() {
		  //For Tests of Entire Network with Number of Nodes and Source Nodes
		 return 3;
	  } 

}
