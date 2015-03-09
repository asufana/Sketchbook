package com.github.asufana.sql;

import java.sql.*;
import java.util.*;

import lombok.*;

/*
 * interface plan

 MicroORM orm = new MicroORM(connection);
 EntityManager<Member> memberTable = orm.on(Member.class);

 //Prepare
 orm.query("drop table if exists x").execute();

 //INSERT
 Map<String, Object> values = ...
 Row<Member> member = memberTable.values(values).insert();

 //SELECT
 Row<Member> single = memberTable.where("name=?", name).select();
 Member member = single.get();
 RowList<Member> fetchs = memberTable.where("name=?", name).fetch();
 List<Member> list = list.list();

 //UPDATE
 memberTable.where(ここどうするか。。).update();

 //DELETE
 memberTable.where("name=?", name).delete();
 memberTable.where("name=?", name).select().delete();
 memberTable.where("name=?", name).list().delete();

 //to Row
 Row<Member> member = new Row(member);

 */

@Getter
public class MicroORM {
    
    private final Connection connection;
    
    public MicroORM(final Connection connection) {
        this.connection = connection;
    }
    
    public <T> EntityManager<T> on(final Class<T> klass) {
        return new EntityManager<T>(connection, klass);
    }
    
    public <T> T first() {
        return null;
    }
    
    public <T> List<T> fetch() {
        return null;
    }
}
