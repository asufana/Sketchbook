package com.github.asufana.proxy;

import org.junit.*;

public class SomeImplTest {
    
    @Test
    public void test() {
        final Some some = new SomeImpl();
        some.doSomething(1);
        
        final Some proxy = SomeProxy.createProxy(some);
        proxy.doSomething(2);
    }
    
}
