package com.github.asufana.sql.functions.mapping;

import lombok.*;

@Getter
public class Row<T> {
    private final Object instance;
    
    public Row(final Object instance) {
        this.instance = instance;
    }
    
    @SuppressWarnings("unchecked")
    public T object() {
        return (T) instance;
    }
    
    @SuppressWarnings("unchecked")
    public T get() {
        return (T) instance;
    }
}
