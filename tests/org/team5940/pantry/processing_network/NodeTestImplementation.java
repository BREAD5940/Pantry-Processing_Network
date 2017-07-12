package org.team5940.pantry.processing_network; 
 
import java.util.Arrays; 
import java.util.HashSet; 
import java.util.Set; 
import static org.junit.Assert.*; 
 
import org.team5940.pantry.processing_network.IllegalUpdateThreadException; 
import org.team5940.pantry.processing_network.Network; 
import org.team5940.pantry.processing_network.Node; 
import org.team5940.pantry.processing_network.SourceNode; 
 
public class NodeTestImplementation extends Node { 
   
  SourceNodeTestImplementation source; 
 
  public NodeTestImplementation(Network network, boolean requireUpdate, SourceNodeTestImplementation source) 
      throws IllegalArgumentException, IllegalStateException { 
    // TODO Improve the way the hashset is created.  
    super(network, new HashSet<SourceNode<?>>(Arrays.asList(source)), requireUpdate); 
     
    this.source = source; 
  } 
   
   
  @org.junit.Test 
  @Override 
  protected void doUpdate() { 
    if (this.getNetwork().getLastCycle() < 10) { 
      try { 
        assertEquals((Integer) 1, (Integer) this.source.getValue()); 
      } catch (IllegalUpdateThreadException e) { 
        e.printStackTrace(); 
      } 
    } 
  } 
} 
