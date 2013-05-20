package com.nearinfinity.examples.guava;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.junit.Test;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ClassPathTest {

    @Test
    public void testFindClassesInGuavaBasePackage() throws IOException {
        ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());

        String guavaBasePackageName = Function.class.getPackage().getName();
        ImmutableSet<ClassPath.ClassInfo> classes = classPath.getTopLevelClasses(guavaBasePackageName);

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

    @Test
    public void testGetResources() throws IOException {
        ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
        ImmutableSet<ClassPath.ResourceInfo> resources = classPath.getResources();
        assertThat(resources.contains("some-resource.txt"), is(true));
    }

    @Test
    public void testGetTopLevelClasses_InSpecificPackage() throws IOException {
        ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());

        ImmutableSet<ClassPath.ClassInfo> topLevelClasses =
                classPath.getTopLevelClasses(HashingExample.class.getPackage().getName());
        Collection<ClassPath.ClassInfo> filtered = Collections2.filter(topLevelClasses,
                new Predicate<ClassPath.ClassInfo>() {
                    @Override
                    public boolean apply(@Nullable ClassPath.ClassInfo input) {
                        return input.getName().equals(HashingExample.class.getName());
                    }
                });
        assertThat(filtered.size(), is(1));
        ClassPath.ClassInfo classInfo = filtered.iterator().next();
        assertThat(classInfo.getName(), is(HashingExample.class.getName()));
    }
}
