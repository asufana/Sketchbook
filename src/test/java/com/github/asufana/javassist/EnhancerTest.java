package com.github.asufana.javassist;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.io.*;
import java.lang.reflect.*;

import javassist.*;

import org.junit.*;

public class EnhancerTest {
    
    @Test
    public void testEnhance() throws NotFoundException, CannotCompileException, IOException {
        Enhancer.enhance("com.github.asufana.javassist.SomeClass");
        
        for (final Method method : SomeClass.class.getDeclaredMethods()) {
            System.out.println(method.getName());
        }
        assertThat(SomeClass.count(), is(1L));
    }
}
