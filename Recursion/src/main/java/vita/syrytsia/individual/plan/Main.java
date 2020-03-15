package vita.syrytsia.individual.plan;

public class Main {

    public static void main(String[] args) {
        long start = System.nanoTime();
        PascalsTriangle pascalsTriangle = PascalsTriangleGenerator.generate(220);
        PascalsTrianglePrinter.print(pascalsTriangle);
        System.out.println("execution time = " + (System.nanoTime() - start) / 1_000_000.0 + " ms");
        // TODO @AndrewG: pascal's triangle data structure
    }
}
