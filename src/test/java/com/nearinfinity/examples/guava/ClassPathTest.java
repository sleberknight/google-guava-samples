package com.nearinfinity.examples.guava;

import static java.util.stream.Collectors.toSet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertTrue;

import com.google.common.base.CharMatcher;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath;
import org.hamcrest.MatcherAssert;
import org.junit.Test;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

@SuppressWarnings("UnstableApiUsage")
public class ClassPathTest {

    @Test
    public void testFindClassesInGuavaBasePackage() throws IOException {
        ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());

        String guavaBasePackageName = Function.class.getPackage().getName();
        ImmutableSet<ClassPath.ClassInfo> classes = classPath.getTopLevelClasses(guavaBasePackageName);

        Collection<Object> classNames = Collections2.transform(classes, new Function<>() {
            @Nullable
            @Override
            public Object apply(ClassPath.ClassInfo input) {
                return input.getSimpleName();
            }
        });

        assertTrue(classNames.contains(Function.class.getSimpleName()));
        assertTrue(classNames.contains(CharMatcher.class.getSimpleName()));
    }

    @Test
    public void testGetResources() throws IOException {
        ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());
        Set<String> resources = classPath.getResources().stream().map(ClassPath.ResourceInfo::getResourceName).collect(toSet());
        MatcherAssert.assertThat(resources.contains("some-resource.txt"), is(true));
    }

    @Test
    public void testGetTopLevelClasses_InSpecificPackage() throws IOException {
        ClassPath classPath = ClassPath.from(Thread.currentThread().getContextClassLoader());

        ImmutableSet<ClassPath.ClassInfo> topLevelClasses =
                classPath.getTopLevelClasses(HashingExample.class.getPackage().getName());
        Collection<ClassPath.ClassInfo> filtered = Collections2.filter(topLevelClasses,
                new Predicate<ClassPath.ClassInfo>() {
                    @Override
                    public boolean apply(ClassPath.ClassInfo input) {
                        return input.getName().equals(HashingExample.class.getName());
                    }
                });
        MatcherAssert.assertThat(filtered.size(), is(1));
        ClassPath.ClassInfo classInfo = filtered.iterator().next();
        MatcherAssert.assertThat(classInfo.getName(), is(HashingExample.class.getName()));
    }
}
