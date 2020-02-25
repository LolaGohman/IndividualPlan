package vita.syrytsia.individual.plan;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PascalsTriangleCounterTest {

    @ParameterizedTest
    @MethodSource("cells")
    void calculateValueForArrayCell(int i, int j, int expectedValue) {
        assertEquals(expectedValue, PascalsTriangleCounter.getValueForCell(i, j));
    }

    @ParameterizedTest
    @MethodSource("failedCells")
    void triangleCounterShouldFailWhenCellIndexIsLessThan0(int i, int j){
        assertThrows(IllegalArgumentException.class, () -> PascalsTriangleCounter.getValueForCell(i, j));
    }

    private static Object[][] cells() {
        return new Object[][]{
                {0, 0, 1},
                {1, 1, 1},
                {3, 2, 3},
                {6, 3, 20},
                {6, 4, 15},
                {8, 4, 70},
                {8, 4, 70}
        };
    }

    private static Object[][] failedCells() {
        return new Object[][]{
                {0, -1},
                {1, -1},
                {-3, 2},
                {-6, 0},
                {0, -50},
                {6, -12},
                {1, 23},
                {5, 19}
        };
    }

}