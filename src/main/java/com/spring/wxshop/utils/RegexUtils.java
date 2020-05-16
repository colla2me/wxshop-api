package com.spring.wxshop.utils;

import java.util.regex.Pattern;

public class RegexUtils {

    public enum Regex {
        TEL("^(13[0-9]|14[5|7]|15[0|1|2|3|4|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$");

        final Pattern pattern;
        Regex(String regex) {
            this.pattern = Pattern.compile(regex);
        }

        public Pattern getPattern() {
            return pattern;
        }

        public boolean matches(String input) {
            return pattern.matcher(input).matches();
        }
    }

    public static boolean matches(Pattern pattern, String input) {
        return pattern.matcher(input).matches();
    }
}
