package com.github.asufana.aspectj;

import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.*;
import org.junit.*;

import com.jcabi.aspects.*;

public class AspectJTest {
    
    @Test
    public void testFoo() throws Exception {
        final int power = new Foo().power(1, 2);
        System.out.println("Power:" + power);
    }
    
    @Aspect
    public static class MethodLogger {
        
        @Around("execution(* *(..)) && @annotation(Loggable)")
        public Object around(final ProceedingJoinPoint point) throws Throwable {
            final long start = System.currentTimeMillis();
            final Object result = point.proceed();
            System.out.println(String.format("#%s(%s): %s in %[msec]s",
                                             MethodSignature.class.cast(point.getSignature())
                                                                  .getMethod()
                                                                  .getName(),
                                             point.getArgs(),
                                             result,
                                             System.currentTimeMillis() - start));
            return result;
        }
    }
    
    public static class Foo {
        @Loggable
        public int power(final int x, final int p) {
            return (int) Math.pow(x, p);
        }
    }
}
