package vita.syrytsia.individual.plan;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListBinarySearcherTest {

    @ParameterizedTest
    @MethodSource("source")
    <T extends Comparable> void sortList(List<T> list, T elementToFind, Optional<SearchResult<T>> expectedResult) {
        assertEquals(ListBinarySearcher.searchElementInList(list, elementToFind), expectedResult);
    }

    private static Object[][] source() {
        return new Object[][]{
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 0, Optional.of(new SearchResult(3, 0))},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 2, Optional.of(new SearchResult(0, 2))},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 3, Optional.of(new SearchResult(1, 3))},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 4, Optional.of(new SearchResult(2, 4))},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 12, Optional.of(new SearchResult(5, 12))},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 18, Optional.of(new SearchResult(4, 18))},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 16, Optional.of(new SearchResult(8, 16))},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 44, Optional.of(new SearchResult(9, 44))},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 55, Optional.of(new SearchResult(7, 55))},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 22, Optional.of(new SearchResult(6, 22))},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44, -22)), -22, Optional.of(new SearchResult(10, -22))},
                {new ArrayList<>(Collections.singletonList(2)), 2, Optional.of(new SearchResult(0, 2))},

                {new ArrayList<>(Arrays.asList("cc", "bcs", "abc")), "abc", Optional.of(new SearchResult(2, "abc"))},
                {new ArrayList<>(Arrays.asList("yucc", "xcbcs", "pabc")), "xcbcs", Optional.of(new SearchResult(1, "xcbcs"))},

                {new ArrayList<>(Arrays.asList("aaa", "bbb", "aaa", "aaa", "ccc")), "aaa", Optional.of(new SearchResult(3, "aaa"))},
                {new ArrayList<>(Arrays.asList(111, 45, 45, 666, 666, 45)), 45, Optional.of(new SearchResult(5, 45))},

                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 56, Optional.empty()},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 34, Optional.empty()},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 666, Optional.empty()},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 73, Optional.empty()},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 566, Optional.empty()},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 11, Optional.empty()},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 56, Optional.empty()},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 1, Optional.empty()},
                {new ArrayList<>(Arrays.asList(2, 3, 4, 0, 18, 12, 22, 55, 16, 44)), 5, Optional.empty()},
        };
    }
}