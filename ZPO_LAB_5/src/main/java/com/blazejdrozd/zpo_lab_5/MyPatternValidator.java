package com.blazejdrozd.zpo_lab_5;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class MyPatternValidator implements Validator {
    private boolean valid;
    private String message;
    private Pattern pattern;

    public MyPatternValidator(Field field) {
        if (field.isAnnotationPresent(MyPattern.class)) {
            MyPattern annotation = field.getAnnotation(MyPattern.class);
            pattern = Pattern.compile(annotation.regex());
            message = annotation.message();
        }
    }

    @Override
    public void validate(String value) {
        valid = pattern.matcher(value).matches();
    }

    @Override
    public boolean isValid() {
        return valid;
    }

    @Override
    public String getMessage() {
        return message;
    }
}