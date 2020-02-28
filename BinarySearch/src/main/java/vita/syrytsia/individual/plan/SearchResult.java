package vita.syrytsia.individual.plan;

import java.util.Objects;

class SearchResult<T extends Comparable> implements Comparable {

    private int originalIndex;
    private T element;

    SearchResult(int originalIndex, T element) {
        this.originalIndex = originalIndex;
        this.element = element;
    }

    T getElement() {
        return element;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchResult<?> result = (SearchResult<?>) o;
        return originalIndex == result.originalIndex &&
                Objects.equals(element, result.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(originalIndex, element);
    }

    @Override
    public int compareTo(Object o) {
        return element.compareTo(o);
    }

    @Override
    public String toString() {
        return originalIndex + " " + element;
    }
}
