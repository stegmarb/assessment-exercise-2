package com.assessment.Exercise;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class Machinery<T> {

    List<? extends Group<T>> classify(final T[] things,
                                      Map<String, Predicate<T>> constraints,
                                      Comparator<T> comparator) {
        List<Group<T>> result = new ArrayList<>();
        if (things.length == 0) {
            return result;
        }
        Set<String> keySet = constraints.keySet();
        for (String key : keySet) {
            Group<T> group = (Group<T>) Group.builder()
                    .groupId(key)
                    .build();
            group.setThings(Arrays.stream(things)
                    .filter(t -> constraints.get(key).test(t))
                    .sorted(comparator).collect(Collectors.toList()));
            result.add(group);
        }
        return result;
    }
}
