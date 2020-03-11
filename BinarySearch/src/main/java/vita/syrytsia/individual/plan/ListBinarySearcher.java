package vita.syrytsia.individual.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ListBinarySearcher {

    // TODO @AndrewG: how to remember original order without SearchResult object memory overhead?
    // TODO @AndrewG: in real life current approach will not work. Consider we add 10_000 times by 1 element to the list and call search each time? OR just call search over 1m elements 10k times?
    public static <T extends Comparable> Optional<SearchResult<T>> searchElementInList(List<T> elements, T element) {

        AtomicInteger index = new AtomicInteger();
        List<SearchResult<T>> originalElements = elements.stream()
                .map(e -> new SearchResult<>(index.getAndIncrement(), e))
                .sorted((searchResult1, searchResult2) -> searchResult1.getElement().compareTo(searchResult2.getElement()))
                .collect(Collectors.toList());
        return searchElement(originalElements, element);
    }

    private static <T extends Comparable> Optional<SearchResult<T>> searchElement(List<SearchResult<T>> elements, T element) {
        int centerIndex = (elements.size() - 1) / 2;
        SearchResult<T> centerElement = elements.get(centerIndex);

        int elCompareResult = element.compareTo(centerElement.getElement());
        if (elCompareResult == 0) {
            return Optional.of(centerElement);
        } else if (elements.size() == 1) {
            return Optional.empty();
        }
        int leftBound = elCompareResult  < 0 ?  0                                      : centerIndex + 1;
        int rightBound = elCompareResult < 0 ? (centerIndex > 0 ? centerIndex - 1 : 0) : elements.size() - 1;

        return searchElement(sublistInclusive(elements, leftBound, rightBound), element);
    }

    private static <T> List<T> sublistInclusive(List<T> elements, int leftBound, int rightBound) {
        List<T> list = new ArrayList<>();
        if (rightBound >= leftBound) {
            list = elements.subList(leftBound, rightBound);
            list.add(elements.get(rightBound));
        }
        return list;
    }
}