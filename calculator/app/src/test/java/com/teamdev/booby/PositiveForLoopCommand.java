package com.teamdev.booby;

import com.teamdev.booby.impl.BoobyCompilerFactoryImpl;
import com.teamdev.booby.impl.BoobyImpl;
import com.teamdev.booby.runtime.RuntimeEnvironment;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@SuppressWarnings("ClassWithTooManyTransitiveDependencies")
public class PositiveForLoopCommand {

    static Stream<Arguments> positiveInitVariable(){
        return Stream.of(
                Arguments.of("for(i=0;i<5;i=i+1){println(i)}")
        );
    }

    @ParameterizedTest
    @MethodSource("positiveInitVariable")
    void executePositiveExpressionTest(String inputChain){
        System.out.println(inputChain);
        Booby calculator = new BoobyImpl(new BoobyCompilerFactoryImpl());
        RuntimeEnvironment environment = RuntimeEnvironment.getInstance();
        calculator.execute(inputChain, environment);
    }
}
