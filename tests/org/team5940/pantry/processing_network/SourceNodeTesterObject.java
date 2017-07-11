package org.team5940.pantry.processing_network; 
 
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.SourceNode; 
 
public class SourceNodeTesterObject extends SourceNode<Integer> { 
 
  public SourceNodeTesterObject(Network network, boolean requireUpdate) 
      throws IllegalArgumentException, IllegalStateException { 
    super(network, null, requireUpdate); 
  }

  @Override
  public Integer updateValue() {
	  // TODO Auto-generated method stub
	  return null;
  } 
 
} 