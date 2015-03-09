package com.github.asufana.sql;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import lombok.*;

import org.junit.*;

import com.github.asufana.sql.functions.mapping.*;
import com.github.asufana.sql.functions.query.*;

public class MicroORMTest extends BaseTest {
    
    @Before
    @Test
    public void testExecute() {
        assertThat(Query.execute(connection, "DROP TABLE IF EXISTS x"), is(0));
        assertThat(Query.execute(connection, "CREATE TABLE x ("
                + "id integer unsigned auto_increment primary key,"
                + "name varchar(255) not null)"), is(0));
        assertThat(Query.execute(connection,
                                 "INSERT INTO x (name) VALUES (?),(?)",
                                 toList("foo", "bar")), is(2));
    }
    
    @Test
    public void test() {
        final MicroORM orm = new MicroORM(connection);
        final EntityManager<Member> em = orm.on(Member.class);
        final Row<Member> row = em.where("select * from x where name=?",
                                       toList("foo")).select();
        assertThat(row, is(notNullValue()));
        
        Member member = row.get();
        assertThat(member.name(), is("foo"));
    }
    
    @Getter
    public static class Member {
        private final Integer id;
        private final String name;
        
        public Member(final Integer id, final String name) {
            this.id = id;
            this.name = name;
        }
    }
}
