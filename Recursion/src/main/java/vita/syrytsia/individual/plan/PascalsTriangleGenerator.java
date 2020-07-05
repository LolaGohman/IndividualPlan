package vita.syrytsia.individual.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class PascalsTriangleGenerator {

    private PascalsTriangleGenerator() {
        //hides public constructor
    }

    static PascalsTriangle generate(int numberOfRows) {
        if (numberOfRows < 0) {
            throw new IllegalArgumentException("Pascals triangle cannot contain negative " +
                    "number of rows!");
        }
        return generatePascalsTriangle(new PascalsTriangle(), numberOfRows);
    }

    private static PascalsTriangle generatePascalsTriangle(PascalsTriangle pascalsTriangle, final int numberOfRows) {
        if (pascalsTriangle.size() == numberOfRows) {
            return pascalsTriangle;
        } else if (pascalsTriangle.size() == 0) {
            List<Long> initialRow = Stream.generate(() -> 1L)
                    .limit(numberOfRows)
                    .collect(Collectors.toList());
            pascalsTriangle.addRow(initialRow);
            return generatePascalsTriangle(pascalsTriangle, numberOfRows);
        } else {
            List<Long> lastRow = pascalsTriangle.getLast();
            final int nextRowSize = lastRow.size() - 1;
            List<Long> nextRow = new ArrayList<>(nextRowSize);
            for (int i = 0; i < nextRowSize; i++) {
                nextRow.add(generateElement(lastRow, nextRow, i));
            }
            pascalsTriangle.addRow(nextRow);
            return generatePascalsTriangle(pascalsTriangle, numberOfRows);
        }
    }

    private static long generateElement(List<Long> lastElements, List<Long> nextElements, int index) {
        return getLeftElement(index, nextElements) + getElementAbove(index, lastElements);
    }

    private static long getLeftElement(int currentIndex, List<Long> list) {
        return currentIndex - 1 < 0 ? 0 : list.get(currentIndex - 1);
    }

    private static long getElementAbove(int index, List<Long> aboveList) {
        return index < 0 || index >= aboveList.size() ? 0 : aboveList.get(index);
    }
}
