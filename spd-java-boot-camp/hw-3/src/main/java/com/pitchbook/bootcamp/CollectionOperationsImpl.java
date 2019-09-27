package com.pitchbook.bootcamp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class CollectionOperationsImpl implements CollectionOperations {

    @Override
    public <E> List<E> generate(Supplier<E> producer, int count) {
        List<E> resultList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            resultList.add(producer.get());
        }
        return resultList;
    }

    @Override
    public <E> List<E> iterate(E seed, Function<? super E, ? extends E> function, int count) {
        List<E> resultList = new ArrayList<>();
        if (count >= 1) {
            resultList.add(seed);
            for (int i = 1; i < count; i++) {
                resultList.add(function.apply(resultList.get(i - 1)));
            }
        }
        return resultList;
    }

    @Override
    public <E> List<E> filter(List<E> elements, Predicate<E> filter) {
        List<E> resultList = new ArrayList<>();
        for (E element : elements) {
            if (filter.test(element)) {
                resultList.add(element);
            }
        }
        return resultList;
    }

    @Override
    public <E> List<E> filterNot(List<E> elements, Predicate<E> filter) {
        List<E> resultList = new ArrayList<>();
        for (E element : elements) {
            if (!filter.test(element)) {
                resultList.add(element);
            }
        }
        return resultList;
    }

    @Override
    public <E> List<E> removeFirst(List<E> elements, Predicate<E> predicate) {
        List<E> resultList = elements != null ? new ArrayList<>(elements) : new ArrayList<>();
        for (E element : resultList) {
            if (predicate.test(element)) {
                resultList.remove(element);
                break;
            }
        }
        return resultList;
    }

    @Override
    public <E> List<E> removeLast(List<E> elements, Predicate<E> predicate) {
        List<E> resultList = elements != null ? new ArrayList<>(elements) : new ArrayList<>();
        resultList = removeFirst(reverse(resultList), predicate);
        return reverse(resultList);
    }

    @Override
    public <E> List<E> removeAll(List<E> elements, Predicate<? super E> predicate) {
        List<E> resultList = new ArrayList<>();
        for (E element : elements) {
            if (!predicate.test(element)) {
                resultList.add(element);
            }
        }
        return resultList;
    }

    @Override
    public <E> List<E> reverse(List<E> elements) {
        List<E> resultList = elements != null ? new ArrayList<>(elements) : new ArrayList<>();
        Collections.reverse(resultList);
        return resultList;
    }

    @Override
    public <E> Optional<E> head(List<E> elements) {
        return elements.isEmpty() ? Optional.empty() : Optional.ofNullable(elements.get(0));
    }

    @Override
    public <E> List<E> tail(List<E> elements) {
        if (elements != null && elements.size() > 1) {
            List<E> resultList = new ArrayList<>(elements);
            resultList.remove(0);
            return resultList;
        }
        return new ArrayList<>();
    }

    @Override
    public <E> boolean anyMatch(List<E> elements, Predicate<E> predicate) {
        for (E element : elements) {
            if (predicate.test(element)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public <E> boolean allMatch(List<E> elements, Predicate<E> predicate) {
        for (E element : elements) {
            if (!predicate.test(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public <E> boolean noneMatch(List<E> elements, Predicate<E> predicate) {
        for (E element : elements) {
            if (predicate.test(element)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public <T, R> List<R> map(List<T> elements, Function<T, R> mappingFunction) {
        List<R> resultList = new ArrayList<>();
        for (T element : elements) {
            resultList.add(mappingFunction.apply(element));
        }
        return resultList;
    }

    @Override
    public <T> List<Tuple<Integer, T>> zipWithIndex(List<T> elements) {
        return map(elements, x -> new Tuple<>(elements.indexOf(x), x));
    }

    @Override
    public <E> Optional<E> max(List<E> elements, Comparator<E> comparator) {
        List<E> copiedList = elements != null ? new ArrayList<>(elements) : new ArrayList<>();
        copiedList.removeIf(Objects::isNull);
        if (copiedList.isEmpty()) {
            return Optional.empty();
        }
        E maxElement = Collections.max(copiedList, comparator);
        return Optional.of(maxElement);
    }

    @Override
    public <E> Optional<E> min(List<E> elements, Comparator<E> comparator) {
        List<E> copiedList = elements != null ? new ArrayList<>(elements) : new ArrayList<>();
        copiedList.removeIf(Objects::isNull);
        if (copiedList.isEmpty()) {
            return Optional.empty();
        }
        E minElement = Collections.min(copiedList, comparator);
        return Optional.of(minElement);
    }

    @Override
    public <E> List<E> distinct(List<E> elements) {
        Set<E> uniqueElements = elements != null ? new HashSet<>(elements) : new HashSet<>();
        return new ArrayList<>(uniqueElements);
    }

    @Override
    public <E> List<E> distinctBy(List<E> elements, Comparator<? super E> comparator) {
        if (elements == null || comparator == null) {
            return new ArrayList<>();
        }
        Set<E> uniqueElements = new TreeSet<>(comparator);
        uniqueElements.addAll(elements);
        return new ArrayList<>(uniqueElements);
    }

    @Override
    public <E> void forEach(List<E> elements, Consumer<E> consumer) {
        if (elements != null && consumer != null) {
            elements.forEach(consumer);
        }
    }

    @Override
    public <E> Optional<E> reduce(List<E> elements, BinaryOperator<E> accumulator) {
        if (elements == null || elements.isEmpty()) {
            return Optional.empty();
        }
        E firstElement = elements.get(0);
        for (int i = 1; i < elements.size(); i++) {
            firstElement = accumulator.apply(firstElement, elements.get(i));
        }
        return Optional.of(firstElement);
    }

    @Override
    public <E> E reduce(E seed, List<E> elements, BinaryOperator<E> accumulator) {
        Optional<E> reducedElements = reduce(elements, accumulator);
        if (reducedElements.isPresent()) {
            return accumulator.apply(seed, reducedElements.get());
        }
        return seed;
    }

    @Override
    public <E> Map<Boolean, List<E>> partitionBy(List<E> elements, Predicate<E> predicate) {
        Map<Boolean, List<E>> resultMap = new HashMap<>();
        resultMap.put(true, filter(elements, predicate));
        resultMap.put(false, filterNot(elements, predicate));
        return resultMap;
    }

    @Override
    public <T, K> Map<K, List<T>> groupBy(List<T> elements, Function<T, K> classifier) {
        Map<K, List<T>> resultMap = new HashMap<>();
        List<K> classifiers = distinct(map(elements, classifier));
        for (K key : classifiers) {
            List<T> allValuesForKey = new ArrayList<>();
            for (T element : elements) {
                if (classifier.apply(element).equals(key)) {
                    allValuesForKey.add(element);
                }
            }
            resultMap.put(key, allValuesForKey);
        }
        return resultMap;
    }

    @Override
    public <T, K, U> Map<K, U> toMap(List<T> elements, Function<T, K> keyFunction,
                                     Function<T, U> valueFunction, BinaryOperator<U> mergeFunction) {
        Map<K, List<T>> primaryMap = groupBy(elements, keyFunction);
        Map<K, U> resultMap = new HashMap<>();
        for (K key : primaryMap.keySet()) {
            List<U> nonMergedValues = map(primaryMap.get(key), valueFunction);
            Optional<U> mergedValue = reduce(nonMergedValues, mergeFunction);
            mergedValue.ifPresent(u -> resultMap.put(key, u));
        }
        return resultMap;
    }

}
