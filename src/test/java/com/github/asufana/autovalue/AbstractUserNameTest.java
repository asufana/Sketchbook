package com.github.asufana.autovalue;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.junit.*;

public class AbstractUserNameTest {
    
    @Test
    public void test() {
        final UserName userName = UserName.create("TestUser");
        assertThat(userName, is(notNullValue()));
    }
    
}
