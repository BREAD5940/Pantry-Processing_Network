package org.team5940.pantry.processing_network; 
 
import org.team5940.pantry.processing_network.Network; 
import org.team5940.pantry.processing_network.SourceNode; 
 
public class SourceNodeTestImplementation extends SourceNode<Integer> { 
 
  public SourceNodeTestImplementation(Network network, boolean requireUpdate) 
      throws IllegalArgumentException, IllegalStateException { 
    super(network, null, requireUpdate); 
  } 
 
  @Override 
  protected Integer updateValue() { 
    return 1; 
  } 
} 