package com.github.asufana.autovalue;

import com.google.auto.value.*;

@AutoValue
public abstract class UserName {
    
    public abstract String value();
    
    public static UserName create(final String value) {
        return new AutoValue_UserName(value);
    }
}
