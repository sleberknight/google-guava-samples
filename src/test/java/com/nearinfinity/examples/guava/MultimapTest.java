package com.nearinfinity.examples.guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MultimapTest {

    private Multimap<String, String> _multimap;

    @Before
    public void setUp() {
        _multimap = ArrayListMultimap.create();
    }

    @Test
    public void testReturnsEmptyCollection_WhenNoValuesForKey() {
        Collection<String> smiths = _multimap.get("Smith");
        assertNotNull(smiths);
        assertTrue(smiths.isEmpty());
    }

    @Test
    public void testSingleElementCollection_WithOneValueForKey() {
        _multimap.put("Smith", "John");
        Collection<String> smiths = _multimap.get("Smith");
        assertNotNull(smiths);
        assertThat(smiths.size(), is(1));
        assertThat(smiths.iterator().next(), is("John"));
    }

    @Test
    public void testCollection_WithMultipleValuesForKey() {
        _multimap.put("Smith", "John");
        _multimap.put("Smith", "Alice");
        _multimap.put("Smith", "Bobby");
        Collection<String> smiths = _multimap.get("Smith");
        assertNotNull(smiths);
        assertThat(smiths.size(), is(3));
        ImmutableList<String> smithsList = ImmutableList.copyOf(smiths);
        assertThat(smithsList, is(Arrays.asList("John", "Alice", "Bobby")));
    }
}
