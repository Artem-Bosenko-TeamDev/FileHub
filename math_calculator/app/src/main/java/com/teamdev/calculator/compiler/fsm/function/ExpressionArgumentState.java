package com.teamdev.calculator.compiler.fsm.function;

import com.teamdev.calculator.CompilerFactoryImpl;
import com.teamdev.calculator.compiler.InputCharacterStream;
import com.teamdev.calculator.compiler.TypeOfExpressionElement;
import com.teamdev.calculator.compiler.fsm.State;
import com.teamdev.calculator.runtime.Command;
import com.teamdev.calculator.runtime.FunctionScope;
import com.teamdev.calculator.runtime.ShuntingYardStack;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.Log4jLoggerAdapter;


import java.util.Optional;

public class ExpressionArgumentState extends State<FunctionScope> {
    private final Log4jLoggerAdapter logger = (Log4jLoggerAdapter) LoggerFactory.getLogger(ExpressionArgumentState.class);

    @Override
    public boolean tryTransition(InputCharacterStream characterStream, FunctionScope output) {
        logger.info("Start transition for Argument expression in function");
        Optional<Command<ShuntingYardStack>> command = new CompilerFactoryImpl().create(TypeOfExpressionElement.EXPRESSION).compile(characterStream);
        ShuntingYardStack stack = new ShuntingYardStack();

        if (command.isPresent()) {
            command.get().execute(stack);
            output.addArgument(stack.calculate());
            logger.info("transition successful");
            return true;
        }
        return false;
    }
}
