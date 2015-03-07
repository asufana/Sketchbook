package com.github.asufana.sql;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.*;

import org.h2.tools.*;
import org.junit.*;

import com.github.asufana.sql.ConnectionFactory.DatabaseType;

public class ConnectionFactoryTest {
    
    static final String dbUrl = "jdbc:h2:mem:test";
    static final String dbUser = "sa";
    static final String dbPass = "";
    private Server h2 = null;
    
    @Before
    public void before() throws SQLException {
        h2 = Server.createWebServer(new String[] {"-webAllowOthers"});
    }
    
    @After
    public void after() {
        h2.stop();
    }
    
    @Test
    public void testCreate() throws SQLException {
        final Connection conn = ConnectionFactory.create(DatabaseType.H2,
                                                         dbUrl,
                                                         dbUser,
                                                         dbPass);
        assertThat(conn, is(notNullValue()));
        assertThat(conn.isClosed(), is(false));
        
        conn.close();
        assertThat(conn.isClosed(), is(true));
    }
    
}
