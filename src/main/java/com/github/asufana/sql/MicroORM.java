package com.github.asufana.sql;

import java.sql.*;

import lombok.*;

@Getter
public class MicroORM {
    
    private final Connection connection;
    
    public MicroORM(final Connection connection) {
        this.connection = connection;
    }
    
    public <T> EntityManager<T> on(final Class<T> klass) {
        return new EntityManager<T>(connection, klass);
    }
}
