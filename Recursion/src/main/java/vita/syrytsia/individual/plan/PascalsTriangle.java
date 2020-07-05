package vita.syrytsia.individual.plan;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

class PascalsTriangle {

    private final Deque<List<Long>> elementsList = new LinkedList<>();

    Deque<List<Long>> getElementsList() {
        return elementsList;
    }

    List<Long> getLast() {
        return elementsList.getLast();
    }

    int size() {
        return elementsList.size();
    }

    void addRow(List<Long> row) {
        elementsList.add(row);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PascalsTriangle that = (PascalsTriangle) o;
        return Objects.equals(elementsList, that.elementsList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(elementsList);
    }
}
