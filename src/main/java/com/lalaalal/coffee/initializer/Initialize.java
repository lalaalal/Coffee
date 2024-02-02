package com.lalaalal.coffee.initializer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Initialize {
    public Class<? extends Initializer> with();
    public Time time() default Time.Post;
    
    public enum Time {
        Pre, Post
    }
}
