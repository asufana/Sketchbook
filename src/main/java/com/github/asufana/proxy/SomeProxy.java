package com.github.asufana.proxy;

import java.lang.reflect.*;

public class SomeProxy {
    
    private final Some some;
    private final Object proxy;
    
    private SomeProxy(final Some some) {
        this.some = some;
        proxy = Proxy.newProxyInstance(Some.class.getClassLoader(),
                                       new Class[] {Some.class},
                                       new SomeHandler());
    }
    
    public static Some createProxy(final Some some) {
        final SomeProxy obj = new SomeProxy(some);
        return Some.class.cast(obj.proxy);
    }
    
    private class SomeHandler implements InvocationHandler {
        
        @Override
        public Object invoke(final Object proxy,
                             final Method method,
                             final Object[] args) throws Throwable {
            System.out.println("before");
            final Object o = method.invoke(some, args);
            System.out.println("after");
            return o;
        }
        
    }
}
