package vita.syrytsia.individual.plan;

import java.util.List;

class PascalsTrianglePrinter {

    static void print(PascalsTriangle pascalsTriangle) {
        long maxNum = getMaxPascalsTriangleValue(pascalsTriangle);
        int cellWidth = Long.toString(maxNum).length();
        int numberOfRows = pascalsTriangle.size();
        for (int i = 0; i< numberOfRows; i++) {
            int whitespaceBetweenValueAndBeginningOfArray = (numberOfRows - 1 - i) * (cellWidth + 1) / 2;
            if (whitespaceBetweenValueAndBeginningOfArray > 0) {
                System.out.printf("%" + whitespaceBetweenValueAndBeginningOfArray + "s", "");
            }
            for (int j = 0; j <= i; j++) {
                String value = pascalsTriangle.getElementsList().get(i).get(j).toString();
                int totalPad = cellWidth - value.length();
                int rightPad = totalPad / 2;
                int leftPad = totalPad - rightPad;

                if (rightPad > 0) {
                    value = String.format("%s%" + rightPad + "s", value, "");
                }
                if (leftPad > 0) {
                    value = String.format("%" + leftPad + "s%s", "", value);
                }
                System.out.print(value);
                if (j < i) {
                    System.out.print(" ");
                } else {
                    System.out.println();
                }
            }

        }
    }

    private static long getMaxPascalsTriangleValue(PascalsTriangle pascalsTriangle) {
        List<Long> bottomRow = pascalsTriangle.getLast();
        return bottomRow.get(bottomRow.size() / 2);
    }
}
