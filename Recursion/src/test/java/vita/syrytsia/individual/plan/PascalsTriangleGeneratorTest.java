package vita.syrytsia.individual.plan;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PascalsTriangleGeneratorTest {

    @Test
    void generatePascalsTriangle() {
        PascalsTriangle actual = PascalsTriangleGenerator.generate(7);
        PascalsTriangle expected = new PascalsTriangle();

        expected.addRow(List.of(1L, 1L, 1L, 1L, 1L, 1L, 1L));
        expected.addRow(List.of(1L, 2L, 3L, 4L, 5L, 6L));
        expected.addRow(List.of(1L, 3L, 6L, 10L, 15L));
        expected.addRow(List.of(1L, 4L, 10L, 20L));
        expected.addRow(List.of(1L, 5L, 15L));
        expected.addRow(List.of(1L, 6L));
        expected.addRow(List.of(1L));

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