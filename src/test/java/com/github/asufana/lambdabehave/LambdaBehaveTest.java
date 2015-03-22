package com.github.asufana.lambdabehave;

import static com.insightfullogic.lambdabehave.Suite.*;

import java.math.*;

import org.junit.runner.*;

import com.insightfullogic.lambdabehave.*;

@RunWith(JunitSuiteRunner.class)
public class LambdaBehaveTest {
    {
        final Sample sample = new Sample();
        
        describe("includeTax",
                 it -> {
                     it.should("税込み価格が取得できること",
                               expect -> expect.that(sample.includeTax(100))
                                               .is(108));
                     
                     it.uses(100, 108)
                       .and(200, 216)
                       .toShow("税込み価格が取得できること（複数）",
                               (expect, price, includeTax) -> expect.that(sample.includeTax(price))
                                                                    .is(includeTax));
                 });
    }
    
    public static class Sample {
        public int includeTax(final Integer price) {
            return new BigDecimal(price * 1.08).intValue();
        }
    }
}
