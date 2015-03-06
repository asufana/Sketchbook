package com.github.asufana.sql;

import java.sql.*;

import org.h2.tools.*;
import org.junit.*;

public abstract class BaseTest {
    
    protected static Server h2;
    
    @BeforeClass
    public static void BeforeClass() throws SQLException {
        h2 = Server.createWebServer(new String[] {"-webAllowOthers"});
    }
    
    @AfterClass
    public static void AfterClass() {
        h2.stop();
    }
}
