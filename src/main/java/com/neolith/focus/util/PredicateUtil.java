package com.neolith.focus.util;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PredicateUtil<T> {


    public static <K> Predicate<K> predicate(Predicate<K> predicate) {
        return predicate;
    }

    public static <R> Predicate<R> not(Predicate<R> predicate) {
        return predicate.negate();
    }

    public List<T> getListAnotContainListB(List<T> listA, List<T> listB) {
        List<T> intList = listA.stream()
                .filter(not(listB::contains))
                .collect(Collectors.toList());
        return intList;
    }

    public List<T> getCommonListItems(List<T> listA, List<T> listB) {
        List<T> intList = listA.stream()
                .filter(listB::contains)
                .collect(Collectors.toList());
        return intList;
    }

}
