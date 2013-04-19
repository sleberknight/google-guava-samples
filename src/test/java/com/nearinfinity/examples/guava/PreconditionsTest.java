package com.nearinfinity.examples.guava;

import org.junit.Test;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class PreconditionsTest {

    @Test
    public void testCheckNotNull_WhenIsNotNull() {
        String input = "foo";
        checkNotNull(input, "input cannot be null");
    }

    @Test(expected = NullPointerException.class)
    public void testCheckArgument_WhenIsNull() {
        String input = null;
        checkNotNull(input, "input cannot be null");
    }

    @Test
    public void testCheckArgument_WhenArgumentIsValid() {
        validateAbleToDrink(21);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCheckArgument_WhenArgumentIsNotValid() throws Exception {
        try {
            validateAbleToDrink(20);
        } catch (Exception ex) {
            assertThat(ex.getMessage(), is("age must be greater than 21"));
            throw ex;
        }
    }

    private void validateAbleToDrink(int age) {
        final int drinkingAge = 21;
        checkArgument(age >= drinkingAge, "age must be greater than %s", drinkingAge);
    }

}
