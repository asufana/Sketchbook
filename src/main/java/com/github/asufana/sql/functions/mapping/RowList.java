package com.github.asufana.sql.functions.mapping;

import java.util.*;

public class RowList<T> {
    
    private final List<Row<T>> rows;
    
    public RowList(final List<Row<T>> rows) {
        this.rows = rows;
    }
    
    public int size() {
        return rows.size();
    }
    
    public Row<T> first() {
        return rows.get(0);
    }
}
