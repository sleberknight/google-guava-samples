package com.nearinfinity.examples.guava;

import com.google.common.reflect.Reflection;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReflectionTest {

    @Test
    public void testGetPackageName_FromClass() {
        assertThat(Reflection.getPackageName(String.class), is("java.lang"));
    }

    @Test
    public void testGetPackageName_FromString() {
        assertThat(Reflection.getPackageName("java.lang.String"), is("java.lang"));
    }

}
