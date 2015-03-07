package com.github.asufana.sql;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.sql.*;

import org.junit.*;

public class QueryTest extends BaseTest {
    
    @Test
    public void testExecute() {
        assertThat(Query.execute(connection, "DROP TABLE IF EXISTS x"), is(0));
        assertThat(Query.execute(connection,
                                 "CREATE TABLE x (id integer unsigned auto_increment primary key, name varchar(255) not null)"),
                   is(0));
        assertThat(Query.execute(connection,
                                 "INSERT INTO x (name) VALUES (?),(?)",
                                 toList("foo", "bar")), is(2));
        final ResultSet resultSet = Query.executeQuery(connection,
                                                       "SELECT * FROM x");
        assertThat(resultSet, is(notNullValue()));
    }
    
}
