package com.teamdev.calculator.runtime.operators;

import com.teamdev.calculator.runtime.Operator;
import com.teamdev.calculator.runtime.holder.ValueHolder;
import com.teamdev.calculator.runtime.holder.doubletype.DoubleValueHolder;

/**
 * Note: this class has a natural ordering that is inconsistent with equals.
 */
public class Subtract implements Operator {
    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public ValueHolder<?> apply(ValueHolder<?> leftArgument, ValueHolder<?> rightArgument) {
        return new DoubleValueHolder((Double) leftArgument.getValue() - (Double) rightArgument.getValue());
    }

    @Override
    public int compareTo(Operator o) {
        return Integer.compare(this.getPriority(), o.getPriority());
    }
}
