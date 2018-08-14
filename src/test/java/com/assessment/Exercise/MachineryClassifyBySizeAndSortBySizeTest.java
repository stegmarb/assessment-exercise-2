package com.assessment.Exercise;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

class MachineryClassifyBySizeAndSortBySizeTest {
    private Machinery<Egg> underTest = new Machinery<>();
    private Map<String, Predicate<Egg>> constraints = new HashMap<>();

    @BeforeEach
    void setUp() {
        constraints.put("S", egg -> egg.getSize() <= 5);
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
    void shouldGetSmallSizeGroupHavingSizeClassifier() {
        // given
        final Egg smallEgg = new Egg(1, 15);

        final Group expectedGroup = Group.builder()
                .groupId("S")
                .thing(smallEgg)
                .build();

        // when
        final List<? extends Group<Egg>> classified = underTest.classify(new Egg[]{smallEgg}, constraints, Comparator.comparingInt(Egg::getSize));

        // then
        assertThat(classified, hasSize(1));
        assertThat(classified.get(0), equalTo(expectedGroup));
    }

    @Test
    void shouldGetOrderedSmallSizeGroupHavingSizeClassifierAndSizeSorting() {
        // given
        final Egg small = new Egg(1, 1);
        final Egg anotherSmall = new Egg(3, 1);

        final Group expectedGroup = Group.builder()
                .groupId("S")
                .thing(small)
                .thing(anotherSmall)
                .build();

        // when
        final List<? extends Group<Egg>> classified = underTest.classify(new Egg[]{anotherSmall, small}, constraints, Comparator.comparingInt(Egg::getSize));

        // then
        assertThat(classified, hasSize(1));
        assertThat(classified, hasItems(equalTo(expectedGroup)));
    }
}