package com.assessment.Exercise;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class MachineryClassifyBySizeAndSortByAgeTest {
    private Machinery<Egg> underTest = new Machinery<>();
    private Map<String, Predicate<Egg>> constraints = new HashMap<>();

    @BeforeEach
    void setUp() {
        constraints.put("S", egg -> egg.getSize() <= 5);
        constraints.put("M", egg -> egg.getSize() > 5 && egg.getSize() <= 10);
        constraints.put("L", egg -> egg.getSize() > 10 && egg.getSize() <= 15);
        constraints.put("X", egg -> egg.getSize() > 15);
    }

    @Test
    void shouldGetEmptyGroupsOnEmptyInput() {
        // when
        final List<? extends Group> classified = underTest.classify(new Egg[]{}, constraints, Comparator.comparingInt(Egg::getAge));

        // then
        assertThat(classified, notNullValue());
        assertThat(classified, empty());
    }

    @Test
    void shouldGetOrderedSmallMediumLargeSizeAndOutlierGroupsHavingSizeClassifierAndAgeSorting() {
        // given
        final Egg small = new Egg(1, 1);
        final Egg anotherSmall = new Egg(5, 2);

        final Egg medium = new Egg(6, 1);
        final Egg anotherMedium = new Egg(10, 3);

        final Egg large = new Egg(11, 1);

        final Egg outlier = new Egg(16, 1);

        final Group expectedSmallSizeGroup = Group.builder()
                .groupId("S")
                .thing(small)
                .thing(anotherSmall)
                .build();

        final Group expectedMediumSizeGroup = Group.builder()
                .groupId("M")
                .thing(medium)
                .thing(anotherMedium)
                .build();

        final Group expectedLargeSizeGroup = Group.builder()
                .groupId("L")
                .thing(large)
                .build();

        final Group expectedOutlierGroup = Group.builder()
                .groupId("X")
                .thing(outlier)
                .build();

        // when
        final List<? extends Group<Egg>> classified = underTest.classify(new Egg[]{small, outlier, anotherSmall, medium, anotherMedium, large}, constraints, Comparator.comparingInt(Egg::getAge));

        // then
        assertThat(classified, hasSize(4));
        assertThat(classified, containsInAnyOrder(expectedSmallSizeGroup, expectedMediumSizeGroup, expectedLargeSizeGroup, expectedOutlierGroup));
    }
}