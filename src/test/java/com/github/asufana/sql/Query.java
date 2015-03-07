package com.github.asufana.sql;

import java.sql.*;
import java.util.*;

import com.github.asufana.sql.exceptions.*;

public class Query {
    
    public static Integer execute(final Connection connection, final String sql) {
        return execute(connection, sql, Collections.emptyList());
    }
    
    public static Integer execute(final Connection connection,
                                  final String sql,
                                  final List<Object> params) {
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            final PreparedStatement paramdPreparedStatement = setParameters(prepareStatement,
                                                                            params);
            return paramdPreparedStatement.executeUpdate();
        }
        catch (final SQLException e) {
            throw new MicroORMException(e, null, sql);
        }
    }
    
    private static PreparedStatement setParameters(final PreparedStatement prepareStatement,
                                                   final List<Object> params) throws SQLException {
        if (params.size() == 0) {
            return prepareStatement;
        }
        for (int i = 0; i < params.size(); i++) {
            prepareStatement.setObject(i + 1, params.get(i));
        }
        return prepareStatement;
    }
    
    public static ResultSet executeQuery(final Connection connection,
                                         final String sql) {
        return executeQuery(connection, sql, Collections.emptyList());
    }
    
    public static ResultSet executeQuery(final Connection connection,
                                         final String sql,
                                         final List<Object> params) {
        try (PreparedStatement prepareStatement = connection.prepareStatement(sql)) {
            final PreparedStatement paramdPreparedStatement = setParameters(prepareStatement,
                                                                            params);
            return paramdPreparedStatement.executeQuery();
        }
        catch (final SQLException e) {
            throw new MicroORMException(e, null, sql);
        }
        
    }
    
}
