package vita.syrytsia.individual.plan;

public class FibonacciCounter {

    public static int calculateFibonacci(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Cannot calculate fibonacci numbers for input less then zero!");
        } else if (number == 0) {
            return 0;
        } else if (number <= 2) {
            return 1;
        }
        return calculateFibonacci(number - 1) + calculateFibonacci(number - 2);
    }
}
