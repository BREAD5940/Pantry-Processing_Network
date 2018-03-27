package org.team5940.pantry.processing_network.functional;

import org.team5940.pantry.processing_network.Network;
import org.team5940.pantry.processing_network.ValueNode;
import org.team5940.pantry.logging.loggers.Logger;

public class PDNode extends ValueNode<Double> {

    ValueNode<? extends Number> _position;
    ValueNode<? extends Number> _velocity;
    ValueNode<? extends Number> targetPosition;
    final double Kp;
    final double Kd;

    public PDNode(Network network, Logger logger, String label, ValueNode<? extends Number> position, ValueNode<? extends Number> velocity, ValueNode<? extends  Number> targetPosition,int positionConstant, int derivativeConstant) throws IllegalArgumentException, IllegalStateException {
        super(network, logger, label, sourcesArray, position, velocity);

        _position = position;
        _velocity = velocity;
        Kp = positionConstant;
        Kd = derivativeConstant;
        this.targetPosition = targetPosition;
    }

    @Override
    protected Double updateValue() {
        return Kp*(targetPosition.getValue().doubleValue() - _position.getValue().doubleValue())+Kd*(0 - _velocity.getValue().doubleValue());
    }
}
