package vita.syrytsia.individual.plan;

class PascalsTriangleCounter {

    public static int getValueForCell(int i, int j) {
        if (j < 0 || i < 0) {
            throw new IllegalArgumentException("Index is not included into Pascals triangle!");
        } else if(j == 0 || j == i) {
            return 1;
        } else {
            return getValueForCell(i - 1, j - 1) + getValueForCell(i - 1, j);
        }
    }
}
