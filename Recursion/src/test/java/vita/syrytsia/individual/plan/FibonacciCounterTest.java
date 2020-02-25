package vita.syrytsia.individual.plan;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FibonacciCounterTest {

    @ParameterizedTest
    @MethodSource("fibonacciArguments")
    void calculateFibonacciNumber(int index, int expectedResult) {
        assertEquals(FibonacciCounter.calculateFibonacci(index), expectedResult);
    }

    @ParameterizedTest
    @MethodSource("fibonacciArgumentsFail")
    void throwExceptionWhenTryToCountFibonacciNumberForIndexLessThenZero(int index) {
        assertThrows(IllegalArgumentException.class, () -> FibonacciCounter.calculateFibonacci(index));
    }

    private static Object[][] fibonacciArguments() {
        return new Object[][]{
                {0, 0},
                {1, 1},
                {2, 1},
                {3, 2},
                {4, 3},
                {5, 5},
                {10, 55},
                {15, 610}
        };
    }

    private static int[] fibonacciArgumentsFail() {
        return new int[]{
                -1,
                -3,
                -10
        };
    }
}
