package com.github.asufana.bytebuddy;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.*;

import net.bytebuddy.*;
import net.bytebuddy.agent.*;
import net.bytebuddy.dynamic.loading.*;
import net.bytebuddy.implementation.*;
import net.bytebuddy.matcher.*;

public class ByteBuddyTest {
    
    @Test
    public void testGenerateBySubClass() throws Exception {
        final Class<? extends Object> dynamicType = new ByteBuddy().subclass(Object.class)
                                                                   .method(ElementMatchers.named("toString"))
                                                                   .intercept(FixedValue.value("Hello world!"))
                                                                   .make()
                                                                   .load(getClass().getClassLoader(),
                                                                         ClassLoadingStrategy.Default.WRAPPER)
                                                                   .getLoaded();
        assertThat(dynamicType.newInstance().toString(), is("Hello world!"));
    }
    
    @Test
    public void testGenerateByRedefine() throws Exception {
        ByteBuddyAgent.installOnOpenJDK();
        new ByteBuddy().redefine(SomeClass.class)
                       .method(ElementMatchers.named("toString"))
                       .intercept(FixedValue.value("Hello world!"))
                       .make()
                       .load(getClass().getClassLoader(),
                             ClassReloadingStrategy.fromInstalledAgent());
        final SomeClass someClass = new SomeClass();
        assertThat(someClass.toString(), is("Hello world!"));
    }
    
    @Test
    public void testDynamicProxy() throws Exception {
        final Class<? extends SomeClass> instance = new ByteBuddy().subclass(SomeClass.class)
                                                                   .name("SubSomeClass")
                                                                   .make()
                                                                   .load(getClass().getClassLoader(),
                                                                         ClassLoadingStrategy.Default.WRAPPER)
                                                                   .getLoaded();
        assertThat(instance.newInstance().toString(), is("Hello"));
    }
    
    public static class SomeClass {
        @Override
        public String toString() {
            return "Hello";
        }
    }
}
