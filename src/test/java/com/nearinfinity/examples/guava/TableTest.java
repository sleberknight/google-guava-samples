package com.nearinfinity.examples.guava;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TableTest {

    private Table<String, String, Integer> _table;

    @Before
    public void setUp() {
        _table = HashBasedTable.create();
        _table.put("Smith", "Alice", 42);
        _table.put("Smith", "Bob", 41);
        _table.put("Smith", "Diane", 12);
        _table.put("Smith", "Carlos", 9);
        _table.put("Jones", "Tom", 36);
        _table.put("Jones", "Brenda", 34);
        _table.put("Potter", "Alice", 26);
    }

    @Test
    public void testTableSize() {
        assertThat(_table.size(), is(7));
    }

    @Test
    public void testGet() {
        assertThat(_table.get("Jones", "Tom"), is(36));
    }

    @Test
    public void testTableRows() {
        Map<String, Integer> smiths = _table.row("Smith");
        assertThat(smiths.size(), is(4));
        assertThat(smiths.get("Alice"), is(42));

        Map<String, Integer> jones = _table.row("Jones");
        assertThat(jones.size(), is(2));
        assertThat(jones.get("Brenda"), is(34));
    }

    @Test
    public void testTableColumn() {
        Map<String, Integer> aliceColumns = _table.column("Alice");
        assertThat(aliceColumns.size(), is(2));
        assertThat(aliceColumns.get("Smith"), is(42));
        assertThat(aliceColumns.get("Potter"), is(26));
    }
}
