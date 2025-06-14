package com.blazejdrozd.restapi.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class DiacriticRemover {
    public static String removePolishDiacritics(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String result = pattern.matcher(normalized).replaceAll("");

        // ł i Ł trzeba obsłużyć ręcznie
        result = result.replace("ł", "l").replace("Ł", "L");

        return result;
    }
}