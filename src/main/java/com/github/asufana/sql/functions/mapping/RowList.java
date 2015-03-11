package com.github.asufana.sql.functions.mapping;

import java.util.*;
import java.util.stream.*;

public class RowList<T> {
    
    private final List<Row<T>> rows;
    
    public RowList(final List<Row<T>> rows) {
        if (rows != null && rows.size() != 0) {
            this.rows = Collections.unmodifiableList(rows);
        }
        else {
            this.rows = Collections.emptyList();
        }
    }
    
    public int size() {
        return rows.size();
    }
    
    public boolean isEmpty() {
        return rows.size() == 0;
    }
    
    public List<T> toList() {
        return rows.stream().map(row -> row.get()).collect(Collectors.toList());
    }
    
    @SuppressWarnings("unchecked")
    public Row<T> first() {
        return isEmpty()
                ? (Row<T>) Row.EMPTY
                : rows.get(0);
    }
}
