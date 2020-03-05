package vita.syrytsia.individual.plan;

class Sorter {

    static int[] insertionSort(int[] array) {
        for (int key = 1; key < array.length; key++) {
            int j = key;
            while (j > 0 && array[j] < array[j - 1]) {
                replaceElements(array, j - 1, j);
                j--;
            }
        }
        return array;
    }

    static int[] bubbleSort(int[] array) {
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i] > array[i + 1]) {
                    isSorted = false;

                    replaceElements(array, i, i + 1);
                }
            }
        }
        return array;
    }

    private static void replaceElements(int[] array, int index1, int index2) {
        int element1;
        element1 = array[index1];
        array[index1] = array[index2];
        array[index2] = element1;
    }
}
