package vita.syrytsia.individual.plan;

public class Main {

    public static void main(String[] args) {
        long start = System.nanoTime();
//        PascalsTrianglePrinter.printPascalsTriangle(40);
        PascalsTriangleCounter.getValueForCell(39, 19);
        System.out.println("execution time = " + (System.nanoTime() - start) / 1_000_000.0 + " ms");
        // TODO @AndrewG: pascal's triangle data structure
    }
}
