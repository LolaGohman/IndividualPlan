package vita.syrytsia.individual.plan;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        final Random random = new Random();
        List<Integer> elements = Stream.generate(() -> random.nextInt(10000))
                .limit(1_000_000).
                 collect(Collectors.toList());
        int elementToFind = 900;


        final long startTime = System.currentTimeMillis();
        System.out.println(ListBinarySearcher.searchElementInList(elements, elementToFind));
        final long finishTime = System.currentTimeMillis();
        System.out.println("Time spent to find element in binary way: " + (finishTime - startTime) + " ms");
    }
}
