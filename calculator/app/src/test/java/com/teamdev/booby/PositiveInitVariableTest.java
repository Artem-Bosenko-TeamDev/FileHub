package com.teamdev.booby;

import com.teamdev.booby.impl.BoobyCompilerFactoryImpl;
import com.teamdev.booby.impl.BoobyImpl;
import com.teamdev.booby.runtime.RuntimeEnvironment;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

@SuppressWarnings("ClassWithTooManyTransitiveDependencies")
class PositiveInitVariableTest {

    static Stream<Arguments> positiveInitVariable(){
        return Stream.of(/*
                Arguments.of("a=5.5;b=a+5;c=a+b;println(c);"),
                Arguments.of("a=5;b=7;c=avg(a,b);println(a,b,c)"),
                Arguments.of("a=2;l=a^a;println(l);b=l+1;println(a)"),
                Arguments.of("a=5<3;println(a);a=6;println(a)"),
                Arguments.of("a=0;a=a+1;println(a)"),
                Arguments.of("c=10;println(c);b=c>21;println(b)"),
                Arguments.of("c=-5.68898;println(c)"),*/
                Arguments.of("c=2;println(++c);println(c)"),
                Arguments.of("c=5;println(c++);println(c)")
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
