package com.nearinfinity.examples.guava;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JoinerTest {

    @Test
    public void testJoinList() {
        List<String> fruits = Arrays.asList("apple", "orange", "guava");
        String joined = Joiner.on(", ").join(fruits);
        assertThat(joined, is("apple, orange, guava"));
    }

    @Test
    public void testJoinList_SkippingNullValues() {
        List<String> fruits = Arrays.asList("apple", null, "orange", null, null, "guava");
        String joined = Joiner.on(", ").skipNulls().join(fruits);
        assertThat(joined, is("apple, orange, guava"));
    }

    @Test
    public void testJoinList_SubstitutingNullValues() {
        List<String> fruits = Arrays.asList("apple", null, "orange", null, null, "guava");
        String joined = Joiner.on(", ").useForNull("banana").join(fruits);
        assertThat(joined, is("apple, banana, orange, banana, banana, guava"));
    }

    @Test
    public void testJoinMap_UsingMapJoiner() {
        HashMap<String, Integer> map = Maps.newLinkedHashMap();
        map.put("Bob", 42);
        map.put("Alice", 36);
        map.put("Diane", 29);

        String joined = Joiner.on(", ").withKeyValueSeparator(": ").join(map);
        assertThat(joined, is("Bob: 42, Alice: 36, Diane: 29"));
    }
}
