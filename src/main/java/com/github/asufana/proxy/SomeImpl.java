package com.github.asufana.proxy;

public class SomeImpl implements Some {
    
    @Override
    public void doSomething(final Integer value) {
        System.out.println("Value: " + value);
    }
    
}
