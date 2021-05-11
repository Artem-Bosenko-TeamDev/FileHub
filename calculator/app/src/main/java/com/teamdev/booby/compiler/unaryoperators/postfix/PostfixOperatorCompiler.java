package com.teamdev.booby.compiler.unaryoperators.postfix;

import com.teamdev.booby.runtime.UnaryOperatorOutputChain;
import com.teamdev.calculator.compiler.ElementCompiler;
import com.teamdev.calculator.compiler.InputCharacterStream;
import com.teamdev.calculator.runtime.ShuntingYardStack;
import com.teamdev.calculator.runtime.command.Command;
import com.teamdev.calculator.runtime.holder.value.DoubleValueHolder;

import java.util.Optional;

@SuppressWarnings("ClassWithTooManyTransitiveDependents")
public class PostfixOperatorCompiler implements ElementCompiler<ShuntingYardStack> {

    @Override
    public Optional<Command<ShuntingYardStack>> compile(InputCharacterStream stream) {
        PostfixOperatorFiniteStateMachine machine = new PostfixOperatorFiniteStateMachine();
        UnaryOperatorOutputChain unaryOperatorOutputChain = new UnaryOperatorOutputChain();

        if(machine.execute(stream,unaryOperatorOutputChain)){
            return Optional.of(stack -> stack.pushOperand(new DoubleValueHolder(unaryOperatorOutputChain.execute())));
        }
        return Optional.empty();
    }
}
