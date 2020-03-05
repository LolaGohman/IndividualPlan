package vita.syrytsia.individual.plan;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SorterTest {

    @Test
    void insertionSort() {
        int[] array =  {1, 0, -3, 6, 4, 30, 33, -45, 23, 13, 13};
        assertArrayEquals(new int[]{-45, -3, 0, 1,4, 6, 13, 13, 23, 30, 33},
                Sorter.insertionSort(array));
    }

    @Test
    void bubbleSort() {
        int[] array =  {1, 0, -3, 6, 4, 30, 33, -45, 23, 13, 13};
        assertArrayEquals(new int[]{-45, -3, 0, 1,4, 6, 13, 13, 23, 30, 33},
                Sorter.bubbleSort(array));
    }
}
