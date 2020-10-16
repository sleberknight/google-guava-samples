package com.nearinfinity.examples.guava;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ObjectsTest {

    @Test
    public void testEquals() {
        assertTrue(Objects.equal("foo", "foo"));
        assertFalse(Objects.equal(null, "foo"));
        assertFalse(Objects.equal("foo", null));
        assertTrue(Objects.equal(null, null));
    }

    @Test
    public void testCompareTo() {
        Person billSmith = new Person("Bill", "Smith", 39);
        Person bobSmyth = new Person("Bob", "Smyth", 42);
        Person bobTucker = new Person("Bob", "Tucker", 35);
        Person bobTucker2 = new Person("Bob", "Tucker", 43);

        List<Person> people = ImmutableList.of(bobTucker2, bobSmyth, billSmith, bobTucker);
        ImmutableList<Person> sortedPeople = Ordering.natural().immutableSortedCopy(people);
        assertThat(sortedPeople, is(ImmutableList.of(billSmith, bobSmyth, bobTucker, bobTucker2)));
    }

    @Test
    public void testToStringHelper() {
        String result = MoreObjects.toStringHelper("SomeClass")
                .omitNullValues()
                .add("firstName", "Brian")
                .add("lastName", "Smith")
                .add("age", 42)
                .add("ssn", null)
                .toString();
        assertThat(result, is("SomeClass{firstName=Brian, lastName=Smith, age=42}"));
    }

    class Person implements Comparable<Person> {
        private String _firstName;
        private String _lastName;
        private int _age;

        Person(String firstName, String lastName, int age) {
            _firstName = firstName;
            _lastName = lastName;
            _age = age;
        }


        @Override
        public int compareTo(Person other) {
            return ComparisonChain.start()
                    .compare(_lastName, other._lastName, Ordering.natural().nullsLast())
                    .compare(_firstName, other._firstName, Ordering.natural().nullsFirst())
                    .compare(_age, other._age, Ordering.natural().nullsLast())
                    .result();
        }

        @Override
        public String toString() {
            return MoreObjects.toStringHelper(getClass())
                    .add("firstName", _firstName)
                    .add("lastName", _lastName)
                    .add("age", _age)
                    .toString();
        }
    }
}
