package com.pitchbook.bootcamp;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;

import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertFalse;

public class CollectionOperationsMReferenceTest {

    private CollectionOperations collectionOperations;

    @BeforeMethod
    public void setUp() {
        this.collectionOperations = new CollectionOperationsImpl();
    }

    @Test
    public void should_create_list_of_elements_provided_by_supplier() {
        List<Example> actualResult = collectionOperations.generate(Example::new, 3);
        List<Example> expectedResult = Arrays.asList(new Example(), new Example(), new Example());

        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void should_create_empty_list_when_count_is_0() {
        List<Example> actualResult = collectionOperations
                .generate(Example::new, 0);

        assertTrue(actualResult.isEmpty());
    }

    @Test
    public void should_create_list_of_objects_provided_by_special_function() {
        List<Integer> expectedResult = Arrays.asList(2, 5, 8, 11);
        List<Integer> actualResult = collectionOperations.iterate(2,
                CollectionOperationsMReferenceTest::calculateNextElement, 4);

        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void should_iterate_into_empty_list_when_count_is_0() {
        List<Integer> actualResult = collectionOperations.iterate(2,
                CollectionOperationsMReferenceTest::calculateNextElement, 0);
        assertTrue(actualResult.isEmpty());
    }

    private static Integer calculateNextElement(Integer x) {
        return x + 3;
    }

    @Test
    public void should_return_list_of_elements_matches_the_rule() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> actualResult = collectionOperations.filter(source,
                CollectionOperationsMReferenceTest::isValueMoreThan3);
        List<Integer> expectedResult = Arrays.asList(4, 5);

        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void should_return_empty_list_when_no_elements_matches_the_rule() {
        List<Integer> source = new ArrayList<>();

        List<Integer> actualResult = collectionOperations.filter(source,
                CollectionOperationsMReferenceTest::isValueMoreThan3);

        assertTrue(actualResult.isEmpty());
    }

    @Test
    public void should_return_list_of_objects_that_do_not_match_the_rule() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> actualResult = collectionOperations.filterNot(source,
                CollectionOperationsMReferenceTest::isValueMoreThan3);
        List<Integer> expectedResult = Arrays.asList(1, 2, 3);

        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void should_remove_only_first_element_that_matches_the_rule() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);

        List<Integer> actualResult = collectionOperations.removeFirst(source,
                CollectionOperationsMReferenceTest::isValueMoreThan3);
        List<Integer> expectedResult = Arrays.asList(1, 2, 3, 5);

        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void should_remove_only_last_element_that_matches_the_rule() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5, 1, 2);

        List<Integer> actualResult = collectionOperations.removeLast(source,
                CollectionOperationsMReferenceTest::isValueMoreThan3);
        List<Integer> expectedResult = Arrays.asList(1, 2, 3, 4, 1, 2);

        assertEquals(actualResult, expectedResult);
    }

    private static boolean isValueMoreThan3(int e) {
        return e > 3;
    }

    @Test
    public void should_remove_all_elements_that_match_the_rule() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5, 1, 2);

        List<Integer> actualResult = collectionOperations.removeAll(source,
                CollectionOperationsMReferenceTest::isValueLessThan4);
        List<Integer> expectedResult = Arrays.asList(4, 5);

        assertEquals(actualResult, expectedResult);
    }

    private static boolean isValueLessThan4(int e) {
        return e < 4;
    }

    @Test
    public void should_create_new_list_in_reverse_order() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5, 1, 2);

        List<Integer> actualResult = collectionOperations.reverse(source);
        List<Integer> expectedResult = Arrays.asList(2, 1, 5, 4, 3, 2, 1);

        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void should_return_first_element_from_list_if_exists() {
        List<Integer> source = Arrays.asList(1, 2, 3);

        Optional<Integer> actualResult = collectionOperations.head(source);

        assertEquals(actualResult, Optional.of(1));
    }

    @Test
    public void should_return_empty_optional_when_first_element_does_not_exist() {
        List<Integer> source = new ArrayList<>();

        Optional<Integer> actualResult = collectionOperations.head(source);

        assertTrue(actualResult.isEmpty());
    }

    @Test
    public void should_return_all_elements_from_list_except_the_first_one() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4);

        List<Integer> actualResult = collectionOperations.tail(source);
        List<Integer> expectedResult = Arrays.asList(2, 3, 4);

        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void should_return_empty_list_when_size_is_less_than_or_equal_to_1() {
        List<Integer> source = Collections.singletonList(9);

        List<Integer> actualResult = collectionOperations.tail(source);

        assertTrue(actualResult.isEmpty());
    }

    @Test
    public void should_return_true_if_at_least_one_element_matches_the_rule() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4);

        assertTrue(collectionOperations.anyMatch(source,
                CollectionOperationsMReferenceTest::isValueEqualsTo1));
    }

    @Test
    public void should_return_false_if_none_of_elements_matches_the_rule() {
        List<Integer> source = Arrays.asList(2, 3, 4);

        assertFalse(collectionOperations.anyMatch(source,
                CollectionOperationsMReferenceTest::isValueEqualsTo1));
    }

    private static boolean isValueEqualsTo1(int e) {
        return e == 1;
    }

    @Test
    public void should_return_true_only_if_all_elements_match_the_rule() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4);

        assertTrue(collectionOperations.allMatch(source,
                CollectionOperationsMReferenceTest::isValueMoreThan0));
    }

    private static boolean isValueMoreThan0(int e) {
        return e > 0;
    }

    @Test
    public void should_return_false_if_at_least_one_element_does_not_match_the_rule() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4);

        assertFalse(collectionOperations.allMatch(source,
                CollectionOperationsMReferenceTest::isValueMoreThan1));
    }

    private static boolean isValueMoreThan1(int e) {
        return e > 1;
    }

    @Test
    public void should_return_true_only_if_none_of_elements_matches_the_rule() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4);

        assertTrue(collectionOperations.noneMatch(source, CollectionOperationsMReferenceTest::isValueMoreThan10));
    }

    private static boolean isValueMoreThan10(int e) {
        return e > 10;
    }

    @Test
    public void should_return_false_if_at_least_one_element_matches_the_rule() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4);

        assertFalse(collectionOperations.noneMatch(source, CollectionOperationsMReferenceTest::isValueMoreThan3));
    }

    @Test
    public void should_transform_all_elements_into_another_types() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4);

        List<String> actualResult = collectionOperations.map(source, Object::toString);
        List<String> expectedResult = Arrays.asList("1", "2", "3", "4");

        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void should_zip_all_the_elements_with_indexes() {
        List<String> source = Arrays.asList("7", "8", "9");

        List<Tuple<Integer, String>> actualResult = collectionOperations.zipWithIndex(source);
        List<Tuple<Integer, String>> expectedResult = Arrays.asList(new Tuple<>(0, "7"),
                new Tuple<>(1, "8"), new Tuple<>(2, "9"));

        assertEquals(actualResult, expectedResult);
    }

    @Test
    public void should_find_max_element() {
        List<Integer> source = Arrays.asList(1, 9, 9, 6, 3, 0, 5);

        Optional<Integer> actualResult = collectionOperations.max(source, Integer::compareTo);

        assertEquals(actualResult, Optional.of(9));
    }

    @Test
    public void should_return_empty_optional_when_max_element_does_not_exist() {
        List<Integer> source = new ArrayList<>();

        Optional<Integer> actualResult = collectionOperations.max(source, Integer::compareTo);

        assertTrue(actualResult.isEmpty());
    }

    @Test
    public void should_find_min_element() {
        List<Integer> source = Arrays.asList(1, 9, 9, 6, 3, 0, 5);

        Optional<Integer> actualResult = collectionOperations.min(source, Integer::compareTo);

        assertEquals(actualResult, Optional.of(0));
    }

    @Test
    public void should_return_empty_optional_when_min_element_does_not_exist() {
        List<Integer> source = new ArrayList<>();

        Optional<Integer> actualResult = collectionOperations.min(source, Integer::compareTo);

        assertTrue(actualResult.isEmpty());
    }

    @Test
    public void should_return_list_of_unique_elements() {
        List<Integer> source = Arrays.asList(1, 9, 9, 6, 3, 3, 0, 5, 3, 9, 0);

        List<Integer> actualResult = collectionOperations.distinct(source);
        List<Integer> expectedResult = Arrays.asList(1, 9, 6, 3, 0, 5);

        assertCollectionEqualsIgnoringOrder(actualResult, expectedResult);
    }

    @Test
    public void should_distinct_elements_by_comparator() {
        List<Integer> source = Arrays.asList(1, 9, 9, 6, 3, 3, 0, 5, 3, 9, 0);

        List<Integer> actualResult = collectionOperations.distinctBy(source, Integer::compareTo);
        List<Integer> expectedResult = Arrays.asList(1, 9, 6, 3, 0, 5);

        assertCollectionEqualsIgnoringOrder(actualResult, expectedResult);
    }

    @Test
    public void should_apply_some_action_to_each_object() {
        List<Integer> source = Arrays.asList(1, 2, 3);
        List<Integer> testList = new ArrayList<>();
        Consumer<Integer> consumer = testList::add;

        collectionOperations.forEach(source, consumer);

        assertEquals(source, testList);
    }

    @Test
    public void should_return_one_element_from_reduced_list() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);

        Optional<Integer> actualResult = collectionOperations.reduce(source, Integer::sum);

        assertEquals(actualResult, Optional.of(15));
    }

    @Test
    public void should_return_empty_optional_when_try_to_reduce_empty_list() {
        List<Integer> source = new ArrayList<>();

        Optional<Integer> actualResult = collectionOperations.reduce(source, Integer::sum);

        assertTrue(actualResult.isEmpty());
    }

    @Test
    public void should_reduce_list_of_elements_with_some_seed() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5);

        int actualResult = collectionOperations.reduce(15, source, Integer::sum);

        assertEquals(actualResult, 30);
    }

    @Test
    public void should_return_seed_when_try_to_reduce_empty_list() {
        List<Integer> source = new ArrayList<>();

        int actualResult = collectionOperations.reduce(15, source, Integer::sum);

        assertEquals(actualResult, 15);
    }

    @Test
    public void should_partition_elements_by_some_rule() {
        List<Integer> source = Arrays.asList(1, 2, 3, 4, 5, 6);

        Map<Boolean, List<Integer>> actualResult = collectionOperations.partitionBy(source,
                CollectionOperationsMReferenceTest::isValueMoreThan3);
        Map<Boolean, List<Integer>> expectedResult = new HashMap<>();
        expectedResult.put(true, Arrays.asList(5, 4, 6));
        expectedResult.put(false, Arrays.asList(2, 1, 3));

        assertCollectionEqualsIgnoringOrder(actualResult.get(true), expectedResult.get(true));
        assertCollectionEqualsIgnoringOrder(actualResult.get(false), expectedResult.get(false));
    }

    @Test
    public void should_group_elements_into_map_by_some_rule() {
        List<String> source = Arrays.asList("ab", "abc", "gh", "a", "c");

        Map<Integer, List<String>> actualResult = collectionOperations
                .groupBy(source, String::length);
        Map<Integer, List<String>> expectedResult = new HashMap<>();
        expectedResult.put(1, Arrays.asList("c", "a"));
        expectedResult.put(2, Arrays.asList("ab", "gh"));
        expectedResult.put(3, Collections.singletonList("abc"));

        assertCollectionEqualsIgnoringOrder(actualResult.get(1), expectedResult.get(1));
        assertCollectionEqualsIgnoringOrder(actualResult.get(2), expectedResult.get(2));
        assertCollectionEqualsIgnoringOrder(actualResult.get(3), expectedResult.get(3));
    }

    @Test
    public void should_convert_elements_into_map_by_some_functions() {
        List<String> source = Arrays.asList("ab", "abc", "gh", "a", "c");
        Function<String, Integer> valueFunction = String::length;
        BinaryOperator<Integer> accumulator = Integer::sum;
        Function<String, String> keyFunction = CollectionOperationsMReferenceTest::convertStringToAnotherString;

        Map<String, Integer> actualResult = collectionOperations
                .toMap(source, keyFunction, valueFunction, accumulator);
        Map<String, Integer> expectedResult = new HashMap<>();
        expectedResult.put("One", 2);
        expectedResult.put("Two", 4);
        expectedResult.put("Other", 3);

        assertEquals(actualResult, expectedResult);
    }

    private static String convertStringToAnotherString(String a) {
        if (a.length() == 1) {
            return "One";
        } else if (a.length() == 2) {
            return "Two";
        } else {
            return "Other";
        }
    }

    private static <E> void assertCollectionEqualsIgnoringOrder(Collection<E> first,
                                                                Collection<E> second) {
        assertTrue(first.size() == second.size() &&
                first.containsAll(second) && second.containsAll(first));
    }

}
