package vita.syrytsia.individual.plan;

import static vita.syrytsia.individual.plan.PascalsTriangleCounter.getValueForCell;

class PascalsTrianglePrinter {

    static void printPascalsTriangle(int numberOfRows) {
        int maxNum = getMaxPascalsTriangleValue(numberOfRows);
        int cellWidth = Integer.toString(maxNum).length();

        for (int i = 0; i < numberOfRows; i++) {

            int whitespaceBetweenValueAndBeginningOfArray = (numberOfRows - 1 - i) * (cellWidth + 1) / 2;
            if (whitespaceBetweenValueAndBeginningOfArray > 0)
                System.out.printf("%" + whitespaceBetweenValueAndBeginningOfArray + "s", "");
            for (int j = 0; j <= i; j++) {
                String value = Integer.toString(getValueForCell(i, j));
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
                if (j < i)
                    System.out.print(" ");
                else
                    System.out.println();
            }
        }
    }

    private static int getMaxPascalsTriangleValue(int numberOfRows) {
        return getValueForCell(numberOfRows - 1, (numberOfRows - 1) / 2);
    }
}
