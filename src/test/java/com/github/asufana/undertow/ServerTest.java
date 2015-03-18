package com.github.asufana.undertow;

import org.junit.*;

public class ServerTest {
    
    @Test
    public void test() throws Exception {
        final Server server = new Server();
        
        final Stoppable stoppable = server.start();
        System.out.println("START");
        
        stoppable.stop();
        System.out.println("STOP");
    }
}
