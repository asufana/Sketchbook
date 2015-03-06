package com.github.asufana.sql;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.*;

import org.junit.*;

import com.github.asufana.sql.ConnectionFactory.DatabaseType;

public class ConnectionFactoryTest {
    
    private final String dbUrl = "jdbc:postgresql://127.0.0.1:5432/postgres";
    private final String dbUser = "postgres";
    private final String dbPass = "";
    
    @Test
    public void testCreate() {
        final Connection conn = ConnectionFactory.create(DatabaseType.PostgreSQL,
                                                         dbUrl,
                                                         dbUser,
                                                         dbPass);
        assertThat(conn, is(notNullValue()));
    }
    
}
