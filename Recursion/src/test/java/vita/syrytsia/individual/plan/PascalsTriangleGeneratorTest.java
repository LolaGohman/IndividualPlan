package vita.syrytsia.individual.plan;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PascalsTriangleGeneratorTest {

    @Test
    void generatePascalsTriangle() {
        PascalsTriangle actual = PascalsTriangleGenerator.generate(7);
        PascalsTriangle expected = new PascalsTriangle();

        LinkedList<List<Long>> elements = new LinkedList<>(
                List.of(
                        List.of(1L),
                        List.of(1L, 1L),
                        List.of(1L, 2L, 1L),
                        List.of(1L, 3L, 3L, 1L),
                        List.of(1L, 4L, 6L, 4L, 1L),
                        List.of(1L, 5L, 10L, 10L, 5L, 1L),
                        List.of(1L, 6L, 15L, 20L, 15L, 6L, 1L)));
        expected.setElementsList(elements);

        assertEquals(expected, actual);
    }

    @Test
    void generatePascalsTriangleWithZeroElements() {
        PascalsTriangle actual = PascalsTriangleGenerator.generate(0);
        PascalsTriangle expected = new PascalsTriangle();

        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowExceptionWhenTryToGenerateTriangleWithNegativeNumberOfRows() {

        assertThrows(
                IllegalArgumentException.class,
                () -> PascalsTriangleGenerator.generate(-1)
        );
    }

}