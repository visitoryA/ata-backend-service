package com.exercide.ata.backend.service.util;

import com.exercide.ata.backend.service.constant.CommonConstant;
import org.apache.logging.log4j.util.Strings;

import static com.exercide.ata.backend.service.constant.CommonConstant.REGEX.NUM_FORMAT;


public class CommonUtil {
    public static Integer convertCurrency(String value) {
        // TODO: handle case $2.4k, 3.5M
        if (Strings.isBlank(value)) {
            return null;
        } else if (value.endsWith("k")) {
            value = value.replace("k", ",000");
        } else if (value.endsWith("M")) {
            value = value.replace("M", ",000,000");
        }
        boolean isMatch = ValidatorUtil.validateWithRegex(NUM_FORMAT, value);
        if (isMatch) {
            return convertStrToInt(value);
        } else {
            return convertStrToInt(value.substring(1));
        }
    }

    public static Integer convertStrToInt(String value) {
        if (Strings.isBlank(value)) {
            return null;
        }
        // TODO: handle case 2.4k, 3.5M
        String[] strDecimal = value.split("\\.");
        String replacedVal = strDecimal[0].replace(CommonConstant.COMMA, CommonConstant.EMPTY);
        return Integer.parseInt(replacedVal);
    }

    public static void appendStrBuilder(StringBuilder strBuilder, String value) {
        if (!strBuilder.isEmpty()) {
            strBuilder.append(CommonConstant.COMMA + " ").append(value);
        } else {
            strBuilder.append(value);
        }
    }
}
