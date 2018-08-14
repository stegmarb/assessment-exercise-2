package com.assessment.Exercise;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MachineryClassifyByAgeAndSortBySizeTest {
    private Machinery<Egg> underTest = new Machinery<>();
    private Map<String, Predicate<Egg>> constraints = new HashMap<>();

    @BeforeEach
    void setUp() {
        constraints.put("EDIBLE", egg -> egg.getAge() < 10);
        constraints.put("ROTTEN", egg -> egg.getAge() >= 10);
    }

    @Test
    void shouldGetEmptyGroupsOnEmptyInput() {
        // when
        final List<? extends Group<Egg>> classified = underTest.classify(new Egg[]{}, constraints, Comparator.comparingInt(Egg::getAge));

        // then
        assertThat(classified, notNullValue());
        assertThat(classified, empty());
    }

    @Test
    void shouldGetEdibleRottenGroupsHavingAgeClassifierAndDescSizeSorting() {
        // given
        final Egg edibleEgg = new Egg(5, 9);

        final Egg rottenEgg = new Egg(5, 15);
        final Egg anotherRottenEgg = new Egg(1, 10);

        final Group expectedRottenGroup = Group.builder()
                .groupId("ROTTEN")
                .thing(anotherRottenEgg)
                .thing(rottenEgg)
                .build();

        final Group expectedEdibleGroup = Group.builder()
                .groupId("EDIBLE")
                .thing(edibleEgg)
                .build();

        // when
        final List<? extends Group<Egg>> classified = underTest.classify(new Egg[]{edibleEgg, rottenEgg, anotherRottenEgg}, constraints, Comparator.comparingInt(Egg::getAge));

        // then
        assertThat(classified, hasSize(2));
        assertThat(classified, containsInAnyOrder(expectedEdibleGroup, expectedRottenGroup));
    }
}

