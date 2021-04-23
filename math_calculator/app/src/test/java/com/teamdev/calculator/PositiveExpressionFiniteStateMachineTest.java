package com.teamdev.calculator;


import com.teamdev.calculator.impl.CalculatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;



import java.util.stream.Stream;

public class PositiveExpressionFiniteStateMachineTest {


    public static Stream<Arguments> positiveExpression(){
        return Stream.of(
                Arguments.of(11.4147,"8.15+6.7-3.123*1.1"),
                Arguments.of(45.0,"6*7+3"),
                Arguments.of(15.0,"3+2*6"),
                Arguments.of(6.0,"5+4-7-2+6")
        );
    }

    @ParameterizedTest
    @MethodSource("positiveExpression")
    public void executePositiveExpressionTest(double expected, String inputChain){
        Calculator calculator = new CalculatorImpl();
        double result = calculator.calculate(inputChain);
        Assertions.assertEquals(expected, result);
    }
}
