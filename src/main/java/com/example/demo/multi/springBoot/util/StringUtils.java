package com.example.demo.multi.springBoot.util;

import org.springframework.lang.Nullable;

public class StringUtils extends org.springframework.util.StringUtils {
    private StringUtils(){}

    public static boolean isBlank(@Nullable String str){
        return !hasText(str);
    }

    public static boolean isNotBlank(@Nullable String str){
        return hasText(str);
    }

    public static boolean isBlank(@Nullable CharSequence str){
        return !hasText(str);
    }

    public static boolean isNotBlank(@Nullable CharSequence str){
        return hasText(str);
    }
}
