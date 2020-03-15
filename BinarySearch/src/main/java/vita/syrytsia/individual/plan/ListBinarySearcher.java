package vita.syrytsia.individual.plan;

import java.util.List;
import java.util.Optional;

public class ListBinarySearcher<T extends Comparable<T>> {

    // TODO @AndrewG: how to remember original order without SearchResult object memory overhead?
    // TODO @AndrewG: in real life current approach will not work. Consider we add 10_000 times by 1 element to the list and call search each time? OR just call search over 1m elements 10k times?

    private List<T> sortedElements;

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

    private Optional<Integer> searchElement(
            int leftBound,
            int rightBound,
            T element
    ) {
        int size = getSizeOfInnerArray(leftBound, rightBound);
        int centerIndex = getCenterIndexOfInnerArray(leftBound, rightBound);
        T centerElement = sortedElements.get(centerIndex);
        int elCompareResult = element.compareTo(centerElement);

        if (elCompareResult == 0) {
            return Optional.of(centerIndex);
        } else if (size == 1) {
            return Optional.empty();
        }
        int newLeftBound = elCompareResult < 0 ? leftBound : centerIndex + 1;
        int newRightBound = elCompareResult < 0 ? Math.max(centerIndex - 1, newLeftBound) : rightBound;
        return searchElement(newLeftBound, newRightBound, element);
    }

    private int getSizeOfInnerArray(int leftBound, int rightBound) {
        return rightBound - leftBound + 1;
    }

    private int getCenterIndexOfInnerArray(int leftBound, int rightBound) {
        return (rightBound + leftBound) / 2;
    }
}