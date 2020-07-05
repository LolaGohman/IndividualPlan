package vita.syrytsia.individual.plan;

import java.util.List;
import java.util.Optional;

public class ListBinarySearcher<T extends Comparable<T>> {

    private final List<T> sortedElements;

    ListBinarySearcher(List<T> elements) {
        if (!isSorted(elements)) {
            throw new IllegalArgumentException("Please, sort list before using binary search");
        }
        this.sortedElements = elements;
    }

    public int searchElementInList(T element) {
        if (sortedElements.isEmpty()) {
            return -1;
        }
        return searchElement(0, sortedElements.size() - 1, element).orElse(-1);
    }

    private boolean isSorted(List<T> elements) {
        for (int i = 0; i < elements.size() - 1; i++) {
            if (elements.get(i).compareTo(elements.get(i + 1)) > 0) {
                return false;
            }
        }
        return true;
    }

    private Optional<Integer> searchElement(int leftBound, int rightBound, T element) {
        final int centerIndex = (rightBound + leftBound) / 2;
        final T centerElement = sortedElements.get(centerIndex);
        final int elCompareResult = element.compareTo(centerElement);
        if (elCompareResult == 0) {
            return Optional.of(centerIndex);
        } else if (rightBound == leftBound) {
            return Optional.empty();
        }
        return elCompareResult < 0 ?
                searchElement(leftBound, Math.max(centerIndex - 1, leftBound), element) :
                searchElement(centerIndex + 1, rightBound, element);
    }
}