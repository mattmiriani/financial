package com.matt.financecontrol.validations;

import java.text.Normalizer;

import static java.util.Objects.isNull;
import static java.util.Optional.ofNullable;

public class StringValidator {

    public static String removeAccentLower(String string) {
        return removeAccent(string).toLowerCase();
    }

    public static String removeAccent(String string) {
        return ofNullable(string)
                .map(str -> Normalizer.normalize(str.trim(), Normalizer.Form.NFD)
                        .replaceAll("[^\\p{ASCII}]", ""))
                .orElse("");
    }

    public static String checkValueEmpty(String value){
        if(isNull(value) || value.isBlank()){
            return null;
        }

        return value;
    }
}
