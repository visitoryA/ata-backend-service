package com.exercide.ata.backend.service.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CommonUtilTest {

    @Test
    void convertCurrency() {
        assertEquals(11, CommonUtil.convertCurrency("11.03"));
        assertEquals(2005, CommonUtil.convertCurrency("2,005.5"));
        assertEquals(35456, CommonUtil.convertCurrency("35456"));
        assertEquals(11000, CommonUtil.convertCurrency("11k"));
        assertEquals(11, CommonUtil.convertCurrency("$11.03"));
        assertEquals(2005, CommonUtil.convertCurrency("$2,005.5"));
        assertEquals(354000, CommonUtil.convertCurrency("$354k"));
        assertEquals(3500000, CommonUtil.convertCurrency("$3.5M"));

        assertNull(CommonUtil.convertCurrency(null));
        assertNull(CommonUtil.convertCurrency(""));
        assertNull(CommonUtil.convertCurrency(" "));
    }

    @Test
    void convertStrToIntWorksProperly() {
        assertEquals(22, CommonUtil.convertStrToInt("11.03", 2));
        assertEquals(2005, CommonUtil.convertStrToInt("2,005.5", 1));
        assertEquals(35456, CommonUtil.convertStrToInt("35456", null));

        assertNull(CommonUtil.convertStrToInt(null, 0));
        assertNull(CommonUtil.convertStrToInt("", 1));
        assertNull(CommonUtil.convertStrToInt(" ", 2));
    }

    @Test
    void appendStrBuilderWorksProperly() {
        StringBuilder stringBuilder = new StringBuilder();
        CommonUtil.appendStrBuilder(stringBuilder, "test");
        String actualResult = stringBuilder.toString();
        assertEquals("test", actualResult);

        CommonUtil.appendStrBuilder(stringBuilder, "test_1");
        actualResult = stringBuilder.toString();
        assertEquals("test, test_1", actualResult);

        CommonUtil.appendStrBuilder(stringBuilder, "test");
        actualResult = stringBuilder.toString();
        assertEquals("test, test_1", actualResult);
    }
}
