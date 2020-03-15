package vita.syrytsia.individual.plan;

import java.util.ArrayList;
import java.util.List;

class PascalsTriangleGenerator {

    static PascalsTriangle generate(int numberOfRows) {
        if (numberOfRows < 0) {
            throw new IllegalArgumentException("Pascals triangle cannot contain negative " +
                    "number of rows!");
        }
        PascalsTriangle pascalsTriangle = new PascalsTriangle();
        return generatePascalsTriangle(pascalsTriangle, 0, numberOfRows);
    }

    private static PascalsTriangle generatePascalsTriangle(
            PascalsTriangle pascalsTriangle,
            int currentIndex,
            int numberOfRows
    ) {
        if (pascalsTriangle.size() == numberOfRows) {
            return pascalsTriangle;
        } else {
            List<Long> currentIndexElements = new ArrayList<>();
            if (currentIndex == 0) {
                currentIndexElements.add(1L);
            } else {
                for (int j = 0; j <= currentIndex; j++) {
                    List<Long> aboveList = getElementsAbove(pascalsTriangle, currentIndex);
                    currentIndexElements.add(getValueForCell(j, aboveList));
                }
            }
            pascalsTriangle.getElementsList().add(currentIndexElements);
            currentIndex = currentIndex + 1;
            return generatePascalsTriangle(pascalsTriangle, currentIndex, numberOfRows);
        }
    }

    private static List<Long> getElementsAbove(PascalsTriangle triangle, int currentIndex) {
        return currentIndex - 1 < 0 ? new ArrayList<>() : triangle.getElementsList().get(currentIndex - 1);
    }

    private static long getValueForCell(int index, List<Long> aboveList) {
        int indexOfLeftElement = index - 1;
        long leftElement;
        long rightElement;

        if (indexOfLeftElement < 0) leftElement = 0;
        else leftElement = aboveList.get(indexOfLeftElement);

        if (index > aboveList.size() - 1) rightElement = 0;
        else rightElement = aboveList.get(index);

        return leftElement + rightElement;
    }

}
