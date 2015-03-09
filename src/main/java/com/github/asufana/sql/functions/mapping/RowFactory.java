package com.github.asufana.sql.functions.mapping;

import java.sql.*;
import java.util.*;

import org.objenesis.*;

import com.github.asufana.sql.exceptions.*;

public class RowFactory {
    private static final Objenesis objenesis = new ObjenesisStd();
    
    public static <T> RowList<T> create(final Class<T> klass, final ResultSet rs) {
        try {
            final List<Row<T>> rows = new ArrayList<>();
            while (rs.next()) {
                rows.add(new Row<T>(newInstance(klass, rs)));
            }
            return new RowList<T>(rows);
        }
        catch (final SQLException e) {
            throw new MicroORMException(e);
        }
    }
    
    private static <T> T newInstance(final Class<T> klass, final ResultSet rs) throws SQLException {
        final T instance = createInstance(klass);
        return setFields(instance, rs);
    }
    
    private static <T> T createInstance(final Class<T> klass) {
        return objenesis.newInstance(klass);
    }
    
    private static <T> T setFields(final T instance, final ResultSet rs) throws SQLException {
        final ResultSetMetaData meta = rs.getMetaData();
        for (int i = 0; i < meta.getColumnCount(); i++) {
            final String columnName = meta.getColumnName(i + 1);
            final Object value = rs.getObject(i + 1);
        }
        return instance;
    }
    
}
