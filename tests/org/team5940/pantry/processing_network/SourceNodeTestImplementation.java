package org.team5940.pantry.processing_network; 
 
import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.SourceNode; 
 
public class SourceNodeTestImplementation extends SourceNode<Integer> { 
 
  public SourceNodeTestImplementation(Network network, boolean requireUpdate, SourceNode<?> source) 
      throws IllegalArgumentException, IllegalStateException { 
    super(network, requireUpdate, source); 
  } 
 
  @Override 
  protected Integer updateValue() { 
    return 1; 
  } 
} 