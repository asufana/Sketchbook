package com.github.asufana.sql.functions.query;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;

import com.github.asufana.sql.*;
import com.github.asufana.sql.functions.query.*;

public class QueryTest extends BaseTest {
    
    @Before
    @Test
    public void testExecute() {
        assertThat(Query.execute(connection, "DROP TABLE IF EXISTS x"), is(0));
        assertThat(Query.execute(connection, "CREATE TABLE x ("
                + "id integer unsigned auto_increment primary key,"
                + "name varchar(255) not null)"), is(0));
    }
    
    @Test
    public void testExecuteQuery() {
        assertThat(Query.execute(connection,
                                 "INSERT INTO x (name) VALUES (?),(?)",
                                 toList("foo", "bar")), is(2));
        assertThat(Query.executeQuery(connection,
                                      "SELECT * FROM x",
                                      rs -> rs.getString("name")),
                   is("foo"));
    }
    
}
