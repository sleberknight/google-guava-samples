package com.nearinfinity.examples.guava;

import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeToken;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InvokableTest {

    // See http://docs.guava-libraries.googlecode.com/git-history/release/javadoc/index.html
    // as I took the example from there...had to see this actually work as it's a bit crazy.
    @Test
    public void testInvokableReturnAndOwnerType_ForListOfString() throws NoSuchMethodException {
        Method getMethod = List.class.getMethod("get", int.class);
        Invokable<List<String>, ?> invokable = new TypeToken<List<String>>() {}.method(getMethod);
        assertEquals(TypeToken.of(String.class), invokable.getReturnType()); // Not Object.class!
        assertEquals(new TypeToken<List<String>>() {}, invokable.getOwnerType());
    }

}
