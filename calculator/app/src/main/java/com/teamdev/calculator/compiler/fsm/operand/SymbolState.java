package com.teamdev.calculator.compiler.fsm.operand;

import com.teamdev.booby.runtime.RuntimeEnvironment;
import com.teamdev.calculator.compiler.InputCharacterStream;
import com.teamdev.calculator.compiler.fsm.State;
import com.teamdev.calculator.runtime.ShuntingYardStack;

public class SymbolState extends State<ShuntingYardStack> {

    @Override
    public boolean tryTransition(InputCharacterStream characterStream, ShuntingYardStack builder) {

        RuntimeEnvironment environment = RuntimeEnvironment.getInstance();

        if(String.valueOf(characterStream.getCurrentSymbol()).matches("[a-zA-Z]")){
            Double value = environment.getValue(String.valueOf(characterStream.getCurrentSymbol()));
            if(value != null) {
                builder.pushOperand(value);
                characterStream.increasePointer();
                return true;
            }
        }
        return false;
    }
}
