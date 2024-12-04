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
        assertEquals(11, CommonUtil.convertCurrency("$11.03"));
        assertEquals(2005, CommonUtil.convertCurrency("$2,005.5"));
        assertEquals(354000, CommonUtil.convertCurrency("$354k"));
        assertEquals(3000000, CommonUtil.convertCurrency("$3M"));

        assertNull(CommonUtil.convertCurrency(null));
        assertNull(CommonUtil.convertCurrency(""));
        assertNull(CommonUtil.convertCurrency(" "));
    }

    @Test
    void convertStrToIntWorksProperly() {
        assertEquals(11, CommonUtil.convertStrToInt("11.03"));
        assertEquals(2005, CommonUtil.convertStrToInt("2,005.5"));
        assertEquals(35456, CommonUtil.convertStrToInt("35456"));

        assertNull(CommonUtil.convertStrToInt(null));
        assertNull(CommonUtil.convertStrToInt(""));
        assertNull(CommonUtil.convertStrToInt(" "));
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
    }
}
