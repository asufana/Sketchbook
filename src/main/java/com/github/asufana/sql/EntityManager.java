package com.github.asufana.sql;

import java.sql.*;
import java.util.*;

import lombok.*;

import com.github.asufana.sql.functions.mapping.*;
import com.github.asufana.sql.functions.query.*;

@Getter
public class EntityManager<T> {
    
    private final Connection connection;
    private final Class<T> klass;
    private String sql;
    private List<Object> params;
    
    EntityManager(final Connection connection, final Class<T> klass) {
        this.connection = connection;
        this.klass = klass;
    }
    
    public EntityManager<T> where(final String sql, final List<Object> params) {
        this.sql = sql;
        this.params = params;
        return this;
    }
    
    public Row<T> select() {
        final RowList<T> rowList = Query.executeQuery(connection,
                                                      sql,
                                                      params,
                                                      rs -> RowFactory.create(klass,
                                                                              rs));
        return rowList.first();
    }
}
