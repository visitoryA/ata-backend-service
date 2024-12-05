package com.exercide.ata.backend.service.util;

import org.apache.logging.log4j.util.Strings;

import java.lang.reflect.Field;
import java.util.List;
import java.util.regex.Pattern;

import static com.exercide.ata.backend.service.constant.CommonConstant.REGEX.CHAR_FORMAT;
import static com.exercide.ata.backend.service.constant.CommonConstant.REGEX.NUM_FORMAT;

public class ValidatorUtil {
    public static boolean validateCurrency(String value) {
        if (value == null) {
            return true;
        }
        if (Strings.isBlank(value)) {
            return false;
        } else if (value.endsWith("k")) {
            value = value.replace("k", ",000");
        } else if (value.endsWith("M")) {
            value = value.replace("M", ",000,000");
        }
        boolean isMatch = validateWithRegex(NUM_FORMAT, value);
        if (!isMatch) {
            isMatch = validateWithRegex(CHAR_FORMAT, String.valueOf(value.charAt(0))) &&
                    validateWithRegex(NUM_FORMAT, value.substring(1));
        }
        return isMatch;
    }

    public static boolean validateWithRegex(String pattern, String value) {
        Pattern regexPattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
        return regexPattern.matcher(value).matches();
    }

    public static boolean invalidFieldName(List<String> values, Field[] fields) {
        boolean isMatch = false;
        for (String val : values) {
            isMatch = false;
            for (Field field : fields) {
                isMatch = field.getName().equalsIgnoreCase(val);
                if (isMatch) {
                    break;
                }
            }
            if (!isMatch) {
                break;
            }
        }
        return !isMatch;
    }
}
