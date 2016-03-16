package com.github.asufana.functional;

import javaslang.control.*;

import org.junit.*;

public class JavaSlangTest {
    
    @Test
    //エラーになり得る複数の処理を直列で実行する（どこかでエラーになり得る）
    public void test01() throws Exception {
        
        final Either<Throwable, Integer> result = Try.of(() -> 1 / 1)
                                                     .mapTry(x -> x + 1)
                                                     .mapTry(x -> x / 0)
                                                     .toEither();
        if (result.isLeft()) {
            System.out.println("Fail:" + result.left().get());
        }
        else {
            System.out.println("Success:" + result.right().get());
        }
    }
    
    @Test
    //エラーになり得る複数の処理を並列で実行する（どちらもエラーになり得る）
    public void test02() throws Exception {
        final Either<Throwable, Object> result = calc1().flatMap(x -> calc2().map(y -> x + y));
        if (result.isLeft()) {
            System.out.println("Fail:" + result.left().get());
        }
        else {
            System.out.println("Success:" + result.right().get());
        }
    }
    
    public Either<Throwable, Integer> calc1() {
        return Try.of(() -> 1 / 1).toEither();
    }
    
    public Either<Throwable, Integer> calc2() {
        return Try.of(() -> 1 / 0).toEither();
    }
    
}
