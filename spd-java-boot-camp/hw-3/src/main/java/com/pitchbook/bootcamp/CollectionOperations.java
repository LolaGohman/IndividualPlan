package com.pitchbook.bootcamp;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface CollectionOperations {

    <E> List<E> generate(Supplier<E> producer, int count);


    <E> List<E> iterate(E seed, Function<? super E, ? extends E> function, int count);


    <E> List<E> filter(List<E> elements, Predicate<E> filter);


    <E> List<E> filterNot(List<E> elements, Predicate<E> filter);


    <E> List<E> removeFirst(List<E> elements, Predicate<E> predicate);


    <E> List<E> removeLast(List<E> elements, Predicate<E> predicate);


    <E> List<E> removeAll(List<E> elements, Predicate<? super E> predicate);


    <E> List<E> reverse(List<E> elements);


    <E> Optional<E> head(List<E> elements);


    <E> List<E> tail(List<E> elements);


    <E> boolean anyMatch(List<E> elements, Predicate<E> predicate);


    <E> boolean allMatch(List<E> elements, Predicate<E> predicate);


    <E> boolean noneMatch(List<E> elements, Predicate<E> predicate);


    <T, R> List<R> map(List<T> elements, Function<T, R> mappingFunction);


    <T> List<Tuple<Integer, T>> zipWithIndex(List<T> elements);


    <E> Optional<E> max(List<E> elements, Comparator<E> comparator);


    <E> Optional<E> min(List<E> elements, Comparator<E> comparator);


    <E> List<E> distinct(List<E> elements);


    <E> List<E> distinctBy(List<E> elements, Comparator<? super E> comparator);


    <E> void forEach(List<E> elements, Consumer<E> consumer);


    <E> Optional<E> reduce(List<E> elements, BinaryOperator<E> accumulator);


    <E> E reduce(E seed, List<E> elements, BinaryOperator<E> accumulator);


    <E> Map<Boolean, List<E>> partitionBy(List<E> elements, Predicate<E> predicate);


    <T, K> Map<K, List<T>> groupBy(List<T> elements, Function<T, K> classifier);


    <T, K, U> Map<K, U> toMap(List<T> elements, Function<T, K> keyFunction,
                              Function<T, U> valueFunction, BinaryOperator<U> mergeFunction
    );

}
