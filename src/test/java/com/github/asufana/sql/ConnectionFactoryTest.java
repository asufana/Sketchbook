package com.github.asufana.sql;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.*;

import org.junit.*;

import com.github.asufana.sql.ConnectionFactory.DatabaseType;

public class ConnectionFactoryTest extends BaseTest {
    
    private final String dbUrl = "jdbc:h2:mem:test";
    private final String dbUser = "sa";
    private final String dbPass = "";
    
    @Test
    public void testCreate() {
        final Connection conn = ConnectionFactory.create(DatabaseType.H2,
                                                         dbUrl,
                                                         dbUser,
                                                         dbPass);
        assertThat(conn, is(notNullValue()));
    }
    
}
