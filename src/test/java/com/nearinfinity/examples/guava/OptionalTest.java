package com.nearinfinity.examples.guava;

import com.google.common.base.Optional;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class OptionalTest {

    @Test
    public void testOptional_HavingValue_IsPresent() {
        Optional<Integer> answer = Optional.of(42);
        assertTrue(answer.isPresent());
    }

    @Test(expected = IllegalStateException.class)
    public void testOptional_ThrowsException_OnGetOfAbsentValue() {
        Optional<String> value = Optional.absent();
        assertFalse(value.isPresent());
        value.get();
    }

    @Test
    public void testOptional_FromNullable_IsPresentWhenNotNull() {
        Optional<String> value = Optional.fromNullable("foo");
        assertTrue(value.isPresent());
        assertThat(value.get(), is("foo"));
    }

    @Test
    public void testOptional_FromNullable_IsNotPresentWhenNull() {
        String s = null;
        Optional<String> value = Optional.fromNullable(s);
        assertFalse(value.isPresent());
    }
}
