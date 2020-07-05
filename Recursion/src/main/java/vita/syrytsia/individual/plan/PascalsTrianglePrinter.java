package vita.syrytsia.individual.plan;

import java.util.LinkedList;
import java.util.List;

class PascalsTrianglePrinter {

    private PascalsTrianglePrinter() {
        //hides public constructor
    }

    static void print(PascalsTriangle pascalsTriangle) {
        final long maxNum = getMaxPascalsTriangleValue(pascalsTriangle);
        final int cellWidth = Long.toString(maxNum).length() + 2;

        pascalsTriangle.getElementsList().forEach(longs -> {
            longs.forEach(l -> {
                String value = l.toString();
                final int totalPad = cellWidth - value.length();
                final int rightPad = totalPad / 2;
                final int leftPad = totalPad - rightPad;
                if (rightPad > 0) {
                    value = String.format("%s%" + rightPad + "s", value, "");
                }
                if (leftPad > 0) {
                    value = String.format("%" + leftPad + "s%s", "", value);
                }
                System.out.print(value);
            });
            System.out.println();
        });
    }

    private static long getMaxPascalsTriangleValue(PascalsTriangle pascalsTriangle) {
        LinkedList<List<Long>> pascalsTriangleRows = (LinkedList<List<Long>>) pascalsTriangle.getElementsList();
        List<Long> elements = pascalsTriangleRows.get((pascalsTriangleRows.size() / 2) + (pascalsTriangleRows.size() % 2));
        return elements.get(elements.size() - 1);
    }
}
