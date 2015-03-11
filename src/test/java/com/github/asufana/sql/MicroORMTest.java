package com.github.asufana.sql;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import lombok.*;

import org.junit.*;

import com.github.asufana.sql.annotations.*;
import com.github.asufana.sql.functions.mapping.*;
import com.github.asufana.sql.functions.query.*;
import com.github.asufana.sql.functions.util.*;

public class MicroORMTest extends BaseTest {
    
    private final MicroORM orm = new MicroORM(connection);
    
    //TODO multi @PK annotation
    //TODO insert return value
    //TODO multi select
    //TODO parameterd count()
    
    @Before
    @Test
    public void testExecute() {
        //CREATE TABLE
        assertThat(Query.execute(connection, "DROP TABLE IF EXISTS x"), is(0));
        assertThat(Query.execute(connection, "CREATE TABLE x ("
                + "id integer unsigned auto_increment primary key,"
                + "name varchar(255) not null)"), is(0));
        
        //INSERT
        final EntityManager<Member> em = orm.on(Member.class);
        assertThat(em.count(), is(0));
        em.values(new MapBuilder<String, String>().put("name", "foo").build())
          .insert();
        em.values(new MapBuilder<String, String>().put("name", "bar").build())
          .insert();
        assertThat(em.count(), is(2));
    }
    
    @Test
    public void testSelect() {
        final EntityManager<Member> em = orm.on(Member.class);
        final Row<Member> row = em.where("NAME=?", "foo").select();
        assertThat(row, is(notNullValue()));
        
        final Member member = row.get();
        assertThat(member.name(), is("foo"));
        
        assertThat(em.where("NAME=?", "bar").select().get().name(), is("bar"));
    }
    
//    @Test
//    public void testSelectList() {
//        final EntityManager<Member> em = orm.on(Member.class);
//        final Row<Member> row = em.where("NAME=?", "foo").select();
//        assertThat(row, is(notNullValue()));
//
//        final Member member = row.get();
//        assertThat(member.name(), is("foo"));
//    }
    
    @Test
    public void testUpdate() {
        final EntityManager<Member> em = orm.on(Member.class);
        final Row<Member> row = em.where("NAME=?", "foo")
                                  .values(new MapBuilder<String, String>().put("name",
                                                                               "bar")
                                                                          .build())
                                  .update();
        assertThat(row, is(notNullValue()));
        
        final Member member = row.get();
        assertThat(member.name(), is("bar"));
    }
    
    @Test
    public void testDelete() {
        final EntityManager<Member> em = orm.on(Member.class);
        assertThat(em.where("NAME=?", "foo").delete(), is(1));
        assertThat(em.count(), is(1));
    }
    
    @Table("x")
    @Getter
    public static class Member {
        @PK
        private final Integer id;
        private final String name;
        
        public Member(final Integer id, final String name) {
            this.id = id;
            this.name = name;
        }
    }
}
