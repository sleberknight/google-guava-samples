package com.nearinfinity.examples.guava;

import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class Collections2Test {

    @Test
    public void testPermutations() {
        Collection<Integer> original = Lists.newArrayList(34, 12, 42);
        Collection<List<Integer>> permutations = Collections2.permutations(original);
        assertThat(permutations.size(), is(6));
        assertThat(permutations.contains(Lists.newArrayList(34, 12, 42)), is(true));
        assertThat(permutations.contains(Lists.newArrayList(34, 42, 12)), is(true));
        assertThat(permutations.contains(Lists.newArrayList(12, 34, 42)), is(true));
        assertThat(permutations.contains(Lists.newArrayList(12, 42, 34)), is(true));
        assertThat(permutations.contains(Lists.newArrayList(42, 12, 34)), is(true));
        assertThat(permutations.contains(Lists.newArrayList(42, 34, 12)), is(true));
    }

    @Test
    public void testOrderedPermutations() {
        Collection<Integer> original = Lists.newArrayList(34, 12, 42);
        Collection<List<Integer>> permutations = Collections2.orderedPermutations(original);
        assertThat(permutations.size(), is(6));
        assertThat(permutations.contains(Lists.newArrayList(12, 34, 42)), is(true));
        assertThat(permutations.contains(Lists.newArrayList(12, 42, 34)), is(true));
        assertThat(permutations.contains(Lists.newArrayList(34, 12, 42)), is(true));
        assertThat(permutations.contains(Lists.newArrayList(34, 42, 12)), is(true));
        assertThat(permutations.contains(Lists.newArrayList(42, 12, 34)), is(true));
        assertThat(permutations.contains(Lists.newArrayList(42, 34, 12)), is(true));
    }

    @Test
    public void testOrderedPermutationsUsingComparator() {
        Collection<Integer> original = Lists.newArrayList(34, 12, 42);
        Comparator<Integer> reverseComparator = new Comparator<Integer>() {
            @Override
            public int compare(Integer i1, Integer i2) {
                return i2.compareTo(i1);
            }
        };
        Collection<List<Integer>> permutations = Collections2.orderedPermutations(original, reverseComparator);
        assertThat(permutations.size(), is(6));
        assertThat(permutations.contains(Lists.newArrayList(42, 34, 12)), is(true));
        assertThat(permutations.contains(Lists.newArrayList(42, 12, 34)), is(true));
        assertThat(permutations.contains(Lists.newArrayList(34, 42, 12)), is(true));
        assertThat(permutations.contains(Lists.newArrayList(34, 12, 42)), is(true));
        assertThat(permutations.contains(Lists.newArrayList(12, 34, 42)), is(true));
        assertThat(permutations.contains(Lists.newArrayList(12, 42, 34)), is(true));
    }

}
