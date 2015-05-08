package com.github.asufana.javassist;

import java.io.*;

import javassist.*;

import org.junit.*;

public class EnhancerTest {
    
    @Test
    public void testEnhance() throws FileNotFoundException, IOException, RuntimeException, CannotCompileException {
        Enhancer.enhance("com.github.asufana.javassist.SomeClass");
    }
    
}
