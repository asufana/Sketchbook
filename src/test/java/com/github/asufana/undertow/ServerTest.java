package com.github.asufana.undertow;

import org.junit.*;

public class ServerTest {
    
    @Test
    public void testStartAndStop() throws Exception {
        final Server server = new Server();
        server.get("/hoge", "hogeeeeeeeee");
        
        final Stoppable stoppable = server.start();
        System.out.println("START");
        
        stoppable.stop();
        System.out.println("STOP");
    }
}
