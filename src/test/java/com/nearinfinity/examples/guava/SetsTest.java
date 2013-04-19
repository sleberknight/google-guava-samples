package com.nearinfinity.examples.guava;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SetsTest {

    @Test
    public void testSetDifference() {
        Set<String> set1 = Sets.newHashSet("apple", "orange", "guava");
        Set<String> set2 = Sets.newHashSet("guava", "clementine");

        Sets.SetView<String> diff12 = Sets.difference(set1, set2);
        assertThat(diff12.immutableCopy(), is(ImmutableSet.of("apple", "orange")));

        Sets.SetView<String> diff21 = Sets.difference(set2, set1);
        assertThat(diff21.immutableCopy(), is(ImmutableSet.of("clementine")));
    }
}
