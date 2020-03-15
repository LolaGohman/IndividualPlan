package vita.syrytsia.individual.plan;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ListBinarySearcherTest {

    @ParameterizedTest
    @MethodSource("source")
    <T extends Comparable<T>> void sortList(List<T> list, T elementToFind, int expectedResult) {
        ListBinarySearcher<T> searcher = new ListBinarySearcher<>(list);
        assertEquals(expectedResult, searcher.searchElementInList(elementToFind));
    }

    @ParameterizedTest
    @MethodSource("failedSource")
    <T extends Comparable<T>> void shouldThrowExceptionWhenListIsNotSorted(List<T> list) {
        assertThrows(IllegalArgumentException.class, () -> new ListBinarySearcher<>(list));
    }

    private static Object[][] source() {
        return new Object[][]{
                {List.of(1, 2, 18, 20, 34), 1, 0},
                {List.of(1, 2, 18, 20, 34), 18, 2},
                {List.of(1, 2, 18, 20, 34), 34, 4},

                {List.of(2, 2, 18, 20, 34), 2, 0},
                {List.of(3, 3, 3, 3, 3), 3, 2},
                {List.of(-2, 0, 1, 3, 4, 4), 4, 4},

                {List.of("a", "ab", "bcd", "ya"), "ya", 3},
                {List.of("ac", "ac", "ac", "dc", "dc"), "ac", 2},

                {List.of("ac"), "ac", 0},
                {List.of(2), 2, 0},

                {List.of(5, 6, 7, 9, 11, 13), 8, -1},
                {List.of(8, 9, 10, 22, 33), 37, -1},
                {List.of(-8, 9, 10, 22, 33), 16, -1},
                {List.of(-8, 9, 10, 22, 33), 25, -1},
                {List.of(-8, 9, 10, 22, 33), 682, -1},
                {List.of(-8, 9, 10, 22, 33), -682, -1},

                {List.of(), 25, -1},
                {List.of(), 682, -1},
                {List.of(), -682, -1}
        };
    }

    private static Object[][] failedSource() {
        return new Object[][]{
                {List.of(5, 6, 7, 9, 13, -13)},
                {List.of(6, 5, 7, 9, 11, 13)},
                {List.of(6, 5, 7, 9, 11, 13)},
                {List.of(2, 1)}
        };
    }
}