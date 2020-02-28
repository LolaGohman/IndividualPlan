package vita.syrytsia.individual.plan;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        List<Integer> elements = Stream.generate(new Random()::nextInt)
                .limit(1_000_000).
                 collect(Collectors.toList());
        int elementToFind = 9_000_00;


        final long startTime = System.currentTimeMillis();
        System.out.println(ListBinarySearcher.searchElementInList(elements, elementToFind));
        final long finishTime = System.currentTimeMillis();
        System.out.println("Time spent to find element in binary way: " + (finishTime - startTime));
    }
}
