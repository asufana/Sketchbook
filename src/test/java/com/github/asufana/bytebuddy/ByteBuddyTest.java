package com.github.asufana.bytebuddy;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import net.bytebuddy.*;
import net.bytebuddy.agent.*;
import net.bytebuddy.dynamic.loading.*;
import net.bytebuddy.implementation.*;
import net.bytebuddy.matcher.*;

import org.junit.*;

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
    
    public static class SomeClass {
        @Override
        public String toString() {
            return "Hello";
        }
    }
}
