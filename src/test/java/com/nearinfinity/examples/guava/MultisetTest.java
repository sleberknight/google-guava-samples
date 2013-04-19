package com.nearinfinity.examples.guava;

import com.google.common.base.Splitter;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MultisetTest {

    private static final String DOCUMENT = "the quick brown fox jumped over the lazy brown dog into the river";

    @Test
    public void testWordCounting_WithoutGuava() {
        Map<String, Integer> counts = Maps.newHashMap();
        Iterable<String> words = Splitter.on(' ').split(DOCUMENT);
        for (String word : words) {
            Integer count = counts.get(word);
            if (count == null) {
                counts.put(word, 1);
            } else {
                counts.put(word, count + 1);
            }
        }
        assertThat(counts, is(expectedWordCounts()));
    }

    @Test
    public void testWordCounting_WithMultiset() {
        Iterable<String> words = Splitter.on(' ').split(DOCUMENT);
        Multiset<String> counts = HashMultiset.create(words);

        Map<String, Integer> expectedCounts = expectedWordCounts();
        for (Multiset.Entry<String> entry : counts.entrySet()) {
            String element = entry.getElement();
            assertThat(counts.count(element), is(expectedCounts.get(element)));
        }
    }

    private Map<String, Integer> expectedWordCounts() {
        Map<String, Integer> expected = Maps.newHashMap();
        expected.put("brown", 2);
        expected.put("dog", 1);
        expected.put("fox", 1);
        expected.put("into", 1);
        expected.put("jumped", 1);
        expected.put("lazy", 1);
        expected.put("over", 1);
        expected.put("quick", 1);
        expected.put("river", 1);
        expected.put("the", 3);
        return expected;
    }

}
