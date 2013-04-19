package com.nearinfinity.examples.guava;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class SplitterTest {

    @Test
    public void testBasicSplit() {
        String input = "apple,orange,banana,guava";
        Iterable<String> split = Splitter.on(',').split(input);
        assertFruitsList(split);
    }

    @Test
    public void testSplit_HavingEmptyValues_AndRequiringTrimming() {
        String input = ",, ,apple, orange  ,,, banana,, ,guava, ,,";
        Iterable<String> split = Splitter.on(',').omitEmptyStrings().trimResults().split(input);
        assertFruitsList(split);
    }

    private void assertFruitsList(Iterable<String> split) {
        List<String> fruits = Lists.newArrayList(split);
        assertThat(fruits, is(Arrays.asList("apple", "orange", "banana", "guava")));
    }
}
