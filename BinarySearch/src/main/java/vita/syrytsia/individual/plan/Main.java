package vita.syrytsia.individual.plan;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) {
        AtomicInteger integer = new AtomicInteger();
        List<Integer> elem = Stream.generate(integer::getAndIncrement)
                .limit(1_000_000)
                .sorted()
                .collect(Collectors.toList());

        ListBinarySearcher<Integer> searcher = new ListBinarySearcher<>(elem);
        final long startTime = System.currentTimeMillis();
        System.out.println(searcher.searchElementInList(1_999_99));
        System.out.println("Time spent to find element in binary way: "
                + (System.currentTimeMillis() - startTime) + " ms");
    }
}
