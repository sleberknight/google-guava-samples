package com.nearinfinity.examples.guava;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.junit.Test;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

public class ClassPathTest {

    @Test
    public void testFindClassesInGuavaBasePackage() throws IOException {
        ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
        ImmutableSet<ClassPath.ClassInfo> classes = classPath.getTopLevelClasses("com.google.common.base");

        Collection<Object> classNames = Collections2.transform(classes, new Function<ClassPath.ClassInfo, Object>() {
            @Nullable
            @Override
            public Object apply(@Nullable ClassPath.ClassInfo input) {
                return input.getSimpleName();
            }
        });

        assertTrue(classNames.contains(Function.class.getSimpleName()));
        assertTrue(classNames.contains(CharMatcher.class.getSimpleName()));
    }
}
