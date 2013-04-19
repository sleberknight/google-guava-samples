package com.nearinfinity.examples.guava;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.Nullable;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class FunctionalProgrammingTest {

    private ImmutableList<Integer> _numbers;

    @Before
    public void setUp() {
        Range<Integer> range = Range.openClosed(0, 10);
        ContiguousSet<Integer> contiguousSet = ContiguousSet.create(range, DiscreteDomain.integers());
        _numbers = contiguousSet.asList();
    }

    @Test
    public void testTransformingCollections_UsingImperativeStyle() {
        List<Integer> squares = Lists.newArrayListWithExpectedSize(_numbers.size());
        for (Integer number : _numbers) {
            squares.add(number * number);
        }

        List<Integer> expected = Lists.newArrayList(1, 4, 9, 16, 25, 36, 49, 64, 81, 100);
        assertThat(squares, is(expected));
    }

    @Test
    public void testTransformingCollections_UsingGuavaFP_FluentIterable() {
        FluentIterable<Integer> squares = FluentIterable.from(_numbers)
                .transform(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(@Nullable Integer input) {
                        checkNotNull(input, "nulls are not allowed here!");
                        return input * input;
                    }
                });

        assertThat(squares.toList(), is(ImmutableList.of(1, 4, 9, 16, 25, 36, 49, 64, 81, 100)));
    }

    @Test
    public void testFilteringCollections_UsingImperativeStyle() {
        List<Integer> evens = Lists.newArrayList();
        for (Integer number : _numbers) {
            if (number % 2 == 0) {
                evens.add(number);
            }
        }

        List<Integer> expected = Lists.newArrayList(2, 4, 6, 8, 10);
        assertThat(evens, is(expected));
    }

    @Test
    public void testFilteringCollections_UsingGuavaFP_FluentIterable() {
        FluentIterable<Integer> evens = FluentIterable.from(_numbers)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean apply(@Nullable Integer input) {
                        checkNotNull(input, "nulls are not allowed here!");
                        return input % 2 == 0;
                    }
                });

        assertThat(evens.toList(), is(ImmutableList.of(2, 4, 6, 8, 10)));
    }

    @Test
    public void testFilteringAndTransformingCollections_UsingImperativeStyle() {
        List<Integer> squaresOfEvens = Lists.newArrayList();
        for (Integer number : _numbers) {
            if (number % 2 == 0) {
                squaresOfEvens.add(number * number);
            }
        }

        List<Integer> expected = Lists.newArrayList(4, 16, 36, 64, 100);
        assertThat(squaresOfEvens, is(expected));
    }

    @Test
    public void testFilteringAndTransformingCollections_UsingGuavaFP_FluentIterable() {
        FluentIterable<Integer> squaresOfEvens = FluentIterable.from(_numbers)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean apply(@Nullable Integer input) {
                        checkNotNull(input, "nulls are not allowed here!");
                        return input % 2 == 0;
                    }
                })
                .transform(new Function<Integer, Integer>() {
                    @Nullable
                    @Override
                    public Integer apply(@Nullable Integer input) {
                        checkNotNull(input, "nulls are not allowed here!");
                        return input * input;
                    }
                });

        assertThat(squaresOfEvens.toList(), is(ImmutableList.of(4, 16, 36, 64, 100)));
    }

}
