package com.github.asufana.sql;

import java.lang.reflect.*;
import java.sql.*;
import java.util.*;
import java.util.stream.*;

import org.apache.commons.lang3.*;

import com.github.asufana.sql.annotations.*;
import com.github.asufana.sql.functions.mapping.*;
import com.github.asufana.sql.functions.query.*;

public class EntityManager<T> {
    
    private final Connection connection;
    private final Class<T> klass;
    private String sql;
    private List<Object> params;
    private Map<String, String> values;
    
    EntityManager(final Connection connection, final Class<T> klass) {
        this.connection = connection;
        this.klass = klass;
    }
    
    private String tableName() {
        final Table annotation = klass.getAnnotation(Table.class);
        return annotation != null
                ? annotation.value()
                : klass.getSimpleName();
    }
    
    private List<Field> pkFields() {
        return Arrays.asList(klass.getFields())
                     .stream()
                     .filter(field -> field.getAnnotation(PK.class) != null)
                     .collect(Collectors.toList());
    }
    
    private Field pkField() {
        return pkFields().get(0);
    }
    
    public Integer count() {
        return count(null, null);
    }
    
    private Integer count(final String sql, final List<Object> params) {
        final String whereString = StringUtils.isNotEmpty(sql)
                ? "WHERE " + sql
                : "";
        return Query.executeQuery(connection,
                                  String.format("SELECT count(*) FROM %s %s",
                                                tableName(),
                                                whereString),
                                  params,
                                  rs -> {
                                      rs.next();
                                      return rs.getInt(1);
                                  });
    }
    
    public EntityManager<T> where(final String sql, final Object... params) {
        this.sql = sql;
        this.params = Arrays.asList(params);
        return this;
    }
    
    public EntityManager<T> values(final Map<String, String> values) {
        this.values = values;
        return this;
    }
    
    public void insert() {
        Query.execute(connection,
                      String.format("INSERT INTO %s (%s) VALUES (%s)",
                                    tableName(),
                                    insertColumns(),
                                    insertValues()));
    }
    
    private String insertColumns() {
        return values.keySet().stream().collect(Collectors.joining(","));
    }
    
    private String insertValues() {
        return values.values()
                     .stream()
                     .collect(Collectors.joining(",", "'", "'"));
    }
    
    public Row<T> select() {
        return selectList().first();
    }
    
    public RowList<T> selectList() {
        return Query.executeQuery(connection,
                                  String.format("SELECT * FROM %s WHERE %s",
                                                tableName(),
                                                sql),
                                  params,
                                  rs -> RowFactory.create(klass, rs));
    }
    
    public Row<T> update() {
        Query.execute(connection, String.format("UPDATE %s SET %s WHERE %s",
                                                tableName(),
                                                updateValues(),
                                                sql), params);
        return select();
    }
    
    private String updateValues() {
        return "name='bar'";
//        return values.entrySet().stream()
//              .map((k, v) -> String.format("%s=%s", k,v))
//                     .collect(Collectors.joining(","));
    }
    
    public Integer delete() {
        return Query.execute(connection,
                             String.format("DELETE FROM %s WHERE %s",
                                           tableName(),
                                           sql),
                             params);
    }
    
}
