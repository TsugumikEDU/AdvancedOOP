package com.blazejdrozd.zpo_lab_5;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MyPattern {
    String regex();
    String message();
}