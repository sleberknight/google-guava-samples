package com.nearinfinity.examples.guava;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.Sets;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class BiMapTest {

    private BiMap<String, Integer> _bimap;

    @Before
    public void setUp() {
        _bimap = HashBiMap.create();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDoesNotPermitAddingKeyHavingValueAlreadyInBiMap() {
        _bimap.put("answer", 42);
        _bimap.put("something else", 42);
    }

    @Test
    public void testForcePutOverwritesAnyExistingEntryWithSameValue() {
        _bimap.put("answer", 42);
        _bimap.forcePut("something else", 42);

        assertThat(_bimap.get("answer"), is(nullValue()));
        assertThat(_bimap.get("something else"), is(42));
    }

    @Test
    public void testInverse() {
        _bimap.put("foo", 1);
        _bimap.put("bar", 2);
        _bimap.put("baz", 3);

        BiMap<Integer, String> inverse = _bimap.inverse();
        assertTrue(inverse.containsKey(1));
        assertTrue(inverse.containsKey(2));
        assertTrue(inverse.containsKey(3));

        assertThat(inverse.get(1), is("foo"));
        assertThat(inverse.get(2), is("bar"));
        assertThat(inverse.get(3), is("baz"));
    }

    @Test
    public void testValues() {
        _bimap.put("foo", 1);
        _bimap.put("bar", 2);
        _bimap.put("baz", 3);

        Set<Integer> values = _bimap.values();
        Set<Integer> expected = Sets.newHashSet(1, 2, 3);
        assertThat(values, is(expected));
    }
}
