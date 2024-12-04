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

    public static final class SalarySurveyFields {
        public static final String TIMESTAMP = "Timestamp";
        public static final String EMPLOYER = "Employer";
        public static final String LOCATION = "Location";
        public static final String JOB_TITLE = "Job Title";
        public static final String YEAR_EMP = "Years at Employer";
        public static final String YEAR_EXP = "Years of Experience";
        public static final String SALARY = "Salary";
        public static final String SIGN_BONUS = "Signing Bonus";
        public static final String ANNUAL_BONUS = "Annual Bonus";
        public static final String ANNUAL_STOCK = "Annual Stock Value/Bonus";
        public static final String GENDER = "Gender";
        public static final String ADD_CMT = "Additional Comments";
    }

}
