package com.github.asufana.functional;

import fj.*;
import fj.data.*;

public class FunctionalJavaTest {
    
    public void calc1() {
        final P1<Validation<RuntimeException, Integer>> f = Try.f(() -> 1 / 0);
    }
}
