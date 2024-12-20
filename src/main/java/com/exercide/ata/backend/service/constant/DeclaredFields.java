package com.exercide.ata.backend.service.constant;

public final class DeclaredFields {
    public static final String FIELDS = "fields";
    public static final String SORT = "sort";
    public static final String SORT_TYPE = "sort_type";
    public static final String PAGE = "page";
    public static final String LIMIT = "limit";

    public static final class JobDataRequestFields {
        public static final String JOB_TITLE = "job_title";
        public static final String EMP_NAME = "employer_name";
        public static final String LOCATION = "location";
        public static final String MIN_SAL = "salary_gte";
        public static final String MAX_SAL = "salary_lte";
        public static final String GENDER = "gender";
    }

    public static final class JobDataFields {
        public static final String JOB_TITLE = "job_title";
        public static final String EMP_NAME = "employer_name";
        public static final String GENDER = "gender";
        public static final String LOCATION = "location";
        public static final String SALARY = "salary";
        public static final String TIMESTAMP = "timestamp";
        public static final String YEAR_EMP = "year_at_employer";
        public static final String YEAR_EXP = "year_of_experience";
        public static final String SIGN_BONUS = "signing_bonus";
        public static final String ANNUAL_BONUS = "annual_bonus";
        public static final String ANNUAL_STOCK = "annual_stock";
        public static final String ADD_CMT = "additional_comment";
    }

    public static final class SalarySurveyCsvIndex {
        public static final int TIMESTAMP = 0;
        public static final int EMPLOYER = 1;
        public static final int LOCATION = 2;
        public static final int JOB_TITLE = 3;
        public static final int YEAR_EMP = 4;
        public static final int YEAR_EXP = 5;
        public static final int SALARY = 6;
        public static final int SIGN_BONUS = 7;
        public static final int ANNUAL_BONUS = 8;
        public static final int ANNUAL_STOCK = 9;
        public static final int GENDER = 10;
        public static final int ADD_CMT = 11;
    }
}
