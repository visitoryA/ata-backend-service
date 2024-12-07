package com.exercide.ata.backend.service.util;

import com.exercide.ata.backend.service.constant.CommonConstant;
import org.apache.logging.log4j.util.Strings;

import static com.exercide.ata.backend.service.constant.CommonConstant.REGEX.NUM_FORMAT;


public class CommonUtil {
    public static Integer convertCurrency(String value) {
        int multiply = 1;
        if (Strings.isBlank(value)) {
            return null;
        } else if (value.endsWith("k")) {
            value = value.replace("k", CommonConstant.EMPTY);
            multiply = 1000;
        } else if (value.endsWith("M")) {
            value = value.replace("M", CommonConstant.EMPTY);
            multiply = 1000000;
        }
        boolean isMatch = ValidatorUtil.validateWithRegex(NUM_FORMAT, value);
        if (isMatch) {
            return convertStrToInt(value, multiply);
        } else {
            return convertStrToInt(value.substring(1), multiply);
        }
    }

    public static Integer convertStrToInt(String value, Integer multiply) {
        if (Strings.isBlank(value)) {
            return null;
        }
        String replacedVal = value.replace(CommonConstant.COMMA, CommonConstant.EMPTY);
        Double val = Double.parseDouble(replacedVal) * (multiply == null ? 1 : multiply);
        return val.intValue();
    }

    public static void appendStrBuilder(StringBuilder strBuilder, String value) {
        var a = strBuilder.indexOf(value);
        if (strBuilder.indexOf(value) >= 0) {
            return;
        }
        if (!strBuilder.isEmpty()) {
            strBuilder.append(CommonConstant.COMMA + " ").append(value);
        } else {
            strBuilder.append(value);
        }
    }
}
