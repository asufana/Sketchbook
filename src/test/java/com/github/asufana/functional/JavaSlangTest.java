package com.github.asufana.functional;

import static java.util.stream.Collectors.*;

import java.util.*;
import java.util.stream.*;

import org.junit.*;

import javaslang.control.*;

public class JavaSlangTest {
    
    @Test
    //Listモナド的な処理
    public void testStream() throws Exception {
        
        //リストの要素があろうとなかろうと意識する必要がない
        final Integer result = Arrays.asList(1, 2, 3)
                                     .stream()
                                     .filter(n -> n > 5) //この時点で要素がなくなる
                                     .mapToInt(n -> n * n) //そんなことは意識せずに処理したいことを記述する
                                     .sum();
        
        //途中でうまくいかなかった際の例外処理は処理の最後に付与する
        final Integer anywayResult = Arrays.asList(1, 2, 3)
                                           .stream()
                                           .filter(n -> n > 5) //この時点で要素がなくなる
                                           .mapToInt(n -> n * n) //そんなことは意識せずに処理したいことを記述する
                                           .findAny()
                                           .orElse(0); //もし対象要素がなければこの値を返却することとする
        
        //-----------------------------------------------
        
        //リスト要素を処理した結果がリストに展開されると、List<List<T>> となるが、
        final List<List<Integer>> listListInt = Arrays.asList(1, 2, 3)
                                                      .stream()
                                                      .map(n -> someList(n)) //戻り値が List<T> の処理
                                                      .collect(toList()); //List<List<T>> が返却される
        
        //flatMapを使うことでリストのリストを平らにすることができる
        final List<Integer> listInt = Arrays.asList(1, 2, 3)
                                            .stream()
                                            .flatMap(n -> someList(n).stream()) //List<List<T>> を List<T> に flatten（平らに）する
                                            .collect(toList()); //List<T> が返却される
        
        //Listモナド的な処理
        //・リスト要素の有無を意識せずに処理できる
        //・リスト要素に対する処理結果がリストであっても問題ない
        //⇒リストの要素に対する処理のみを意識すれば良い！
    }
    
    //リストが返却される何らかの処理
    public List<Integer> someList(final Integer n) {
        return IntStream.range(1, n).mapToObj(i -> Integer.valueOf(i)).collect(toList());
    }
    
    //========================================
    
    @Test
    //Optionモナド的な処理
    public void testOptional() throws Exception {
        
        //値があろうとなかろうと意識する必要がない
        final Optional<Integer> result = Optional.ofNullable(1)
                                                 .map(n -> (Integer) null) //この時点でnullになる
                                                 .map(n -> n * n); //そんなことは意識せずに処理したいことを記述する
        
        //途中で値がNullになった際の例外処理は処理の最後に付与する
        final Integer anywayResult = Optional.ofNullable(1)
                                             .map(n -> (Integer) null)
                                             .map(n -> n * n)
                                             .orElse(0); //もし途中でNullだったらこの値を返却することとする
        
        //-----------------------------------------------
        
        //値を処理した結果がOptionだと、Optional<Optional<T>> となるが、
        final Optional<Optional<Integer>> optionOptionInt = Optional.ofNullable(1)
                                                                    .map(n -> someOption(n)); //戻り値が Optional<T> の処理 ⇒ Optional<Optoinal<T>> が返却される
        
        //flatMapを使うことでオプションのオプションをフラットにすることができる
        final Optional<Integer> optionInt = Optional.ofNullable(1).flatMap(n -> someOption(n)); //Optional<Optional<T>> を Optional<T> に flatten（平らに）する ⇒ Optional<T> が返却される
        
        //-----------------------------------------------
        
        //複数のOptionalを処理する場合でも、それぞれの値の有無を意識する必要がない
        final Optional<Integer> n1 = Optional.ofNullable(1);
        final Optional<Integer> n2 = Optional.ofNullable(2);
        final Optional<Integer> n3 = n1.flatMap(i1 -> n2.map(i2 -> i1 + i2)); //n1, n2 のいずれか、あるいは両方が null だろうが意識せずに処理したいことを記述すればよい
        
        //Optionモナド的な処理
        //・値の有無を意識せずに処理できる
        //・値に対する処理結果がオプションであっても問題ない
        //⇒値に対する処理のみを意識すれば良い！
        
        //Optionalは要素を１つしか保持できないListと考えると分かりやすい
        //要素をフィルタする list.stream().filter(f) ⇒ optional.filter(f) //同じメソッド
        //要素を取り出して処理する list.stream().map(f) ⇒ optional.map(f) //同じメソッド
        //要素を取り出して処理する(戻り値なし) list.stream().foreach(f) ⇒ optional.ifPresent(f) //ちょっと違う
        
    }
    
    //オプションが返却される何らかの処理
    public Optional<Integer> someOption(final Integer n) {
        return Optional.ofNullable(n);
    }
    
    //========================================
    
    //Optionalを例外にも利用したい
    //・Optionalでは処理結果がnullであることは分かるが、
    //・nullとなったその理由（例外内容）を知ることができない
    //・例外内容に応じた振る舞いを行いたい場合には、Optionalでは約不足
    
    //そこでEither登場
    //・Either<Left, Right> という２つの入れ物を持つ
    //・ただしいずれかひとつしか値を入れることができない
    //・慣例として左に例外を、右に正常時戻り値を入れる（正しいのrightにかけている）
    
    @Test
    //Eitherモナド的な処理
    //＊Java8にEitherは含まれていないので、JAVASLANGライブラリを利用
    public void testEither() throws Exception {
        
        //someEitherの処理結果が例外であろうとなかろうと意識する必要がない
        final Either<Object, Integer> result = someEither(1) //この時点で例外が発生している
                                                             .map(n -> n * 100); //そんなことは意識せずに処理したいことを記述する
        
        //例外有無や例外内容に応じて振る舞いを行うには
        if (result.isRight()) {
            System.out.println("Success:" + result.right().get());
        }
        else {
            System.out.println("Failure:" + result.left().get());
        }
        
        //途中でエラーになった際にデフォルト値があるのであれば最後に付与する
        final Integer anywayResult = someEither(1).map(n -> n * 100).getOrElse(0);
        
        //-----------------------------------------------
        
        //値を処理した結果がEitherだと、Either<Either<T>> となるが、
        final Either<Object, Either<Object, Integer>> eitherEitherInt = someEither(1).map(n -> someEither(n));
        
        //flatMapを使うことでEitherのEitherをフラットにすることができる
        final Either<Object, Integer> eitherInt = someEither(1).flatMap(n -> someEither(n));
        
        //-----------------------------------------------
        
        //複数の処理の場合でも、それぞれの例外の有無を意識する必要がない
        final Either<Object, Integer> e1 = someEither(1);
        final Either<Object, Integer> e2 = someEither(2);
        final Object e3 = e1.flatMap(n1 -> e2.map(n2 -> n1 + n2)).getOrElse(1);
        
        //Eitherモナド的な処理
        //・例外の有無を意識せずに処理できる
        //・値に対する処理の結果が例外(Either)であっても問題ない
        //⇒値に対する処理のみを意識すれば良い！
    }
    
    //例外が発生しうる何らかの処理
    private Either<Object, Integer> someEither(final Integer i) {
        // ほんとはこんな感じの処理
        // try {
        //     //compute...
        //     return Either.right(1);
        // }
        // catch (final Exception e) {
        //     return Either.left(e);
        // }
        
        return Either.left(new RuntimeException());
    }
    
    //========================================
    
    //例外が発生しうる何らかの処理
    private Integer someCompute() {
        //compute...
        throw new RuntimeException();
    }
    
    @Test
    //例外をラップする
    public void testTry() throws Exception {
        
        //例外が発生しうる処理をTryラップしてEitherを得る
        final Either<Throwable, Integer> trySomeCompute = Try.of(() -> someCompute()).toEither();
        
        //例外を意識せずに処理を記述する、例外時の結果を最後に付与する
        final Integer resultInt = trySomeCompute.map(res -> res * 100).getOrElse(0);
    }
    
    @Test
    //エラーになり得る複数の処理を意識せずに記述する
    public void test01() throws Exception {
        
        final Either<Throwable, Integer> result = Try.of(() -> 1 / 1)
                                                     .mapTry(x -> 1 / 0)
                                                     .mapTry(x -> 1)
                                                     .toEither();
    }
    
}
