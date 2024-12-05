package com.exercide.ata.backend.service.util;

import com.exercide.ata.backend.service.constant.DeclaredFields.JobDataFields;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.exercide.ata.backend.service.constant.CommonConstant.REGEX.NUM_FORMAT;
import static com.exercide.ata.backend.service.constant.CommonConstant.REGEX.SORT_TYPE;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ValidatorUtilTest {
    @Test
    void validateCurrency() {
        assertTrue(ValidatorUtil.validateCurrency(null));
        assertTrue(ValidatorUtil.validateCurrency("$122.0"));
        assertTrue(ValidatorUtil.validateCurrency(" 121,234.00"));
        assertTrue(ValidatorUtil.validateCurrency("123,456,789"));
        assertTrue(ValidatorUtil.validateCurrency("$1000"));
        assertTrue(ValidatorUtil.validateCurrency("45678.99"));
        assertTrue(ValidatorUtil.validateCurrency("$456k"));
        assertTrue(ValidatorUtil.validateCurrency("456M"));

        assertFalse(ValidatorUtil.validateCurrency(""));
        assertFalse(ValidatorUtil.validateCurrency(" "));
        assertFalse(ValidatorUtil.validateCurrency("1212,000"));
        assertFalse(ValidatorUtil.validateCurrency("$112.0.0"));
        assertFalse(ValidatorUtil.validateCurrency("12,1$34.00"));
        assertFalse(ValidatorUtil.validateCurrency("$456K"));
        assertFalse(ValidatorUtil.validateCurrency("$456m"));
    }
    @Test
    void validateWithRegexWithNumber() {
        assertTrue(ValidatorUtil.validateWithRegex(NUM_FORMAT, "122.0"));
        assertTrue(ValidatorUtil.validateWithRegex(NUM_FORMAT, "121,234.00"));
        assertTrue(ValidatorUtil.validateWithRegex(NUM_FORMAT, "123,456,789"));
        assertTrue(ValidatorUtil.validateWithRegex(NUM_FORMAT, "1000"));
        assertTrue(ValidatorUtil.validateWithRegex(NUM_FORMAT, "45678.99"));

        assertFalse(ValidatorUtil.validateWithRegex(NUM_FORMAT, "112.0.0"));
        assertFalse(ValidatorUtil.validateWithRegex(NUM_FORMAT, "12,1234.00"));
    }

    @Test
    void validateWithRegexWithSortType() {
        assertTrue(ValidatorUtil.validateWithRegex(SORT_TYPE, "AsC"));
        assertTrue(ValidatorUtil.validateWithRegex(SORT_TYPE, "DESC"));

        assertFalse(ValidatorUtil.validateWithRegex(SORT_TYPE, "asc|desc"));
        assertFalse(ValidatorUtil.validateWithRegex(SORT_TYPE, "unmatch"));
    }

    @Test
    void invalidFieldNameWorksProperly() {
        Field[] declaredFields = JobDataFields.class.getDeclaredFields();
        List<String> invalidFields = List.of(JobDataFields.JOB_TITLE, "invalid_field");
        List<String> validFields = List.of(JobDataFields.JOB_TITLE, JobDataFields.SALARY);

        assertTrue(ValidatorUtil.invalidFieldName(invalidFields, declaredFields));
        assertFalse(ValidatorUtil.invalidFieldName(validFields, declaredFields));
    }
}
