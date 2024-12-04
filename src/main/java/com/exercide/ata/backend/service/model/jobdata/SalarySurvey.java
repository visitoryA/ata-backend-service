package com.exercide.ata.backend.service.model.jobdata;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static com.exercide.ata.backend.service.constant.DeclaredFields.SalarySurveyFields.*;

@Getter
@Setter
@Builder
public class SalarySurvey {
    @JsonProperty(TIMESTAMP)
    private String timestamp;
    @JsonProperty(EMPLOYER)
    private String employerName;
    @JsonProperty(LOCATION)
    private String location;
    @JsonProperty(JOB_TITLE)
    private String jobTitle;
    @JsonProperty(YEAR_EMP)
    private String yearAtEmployer;
    @JsonProperty(YEAR_EXP)
    private String yearOfExperience;
    @JsonProperty(SALARY)
    private String salary;
    @JsonProperty(SIGN_BONUS)
    private String signingBonus;
    @JsonProperty(ANNUAL_BONUS)
    private String annualBonus;
    @JsonProperty(ANNUAL_STOCK)
    private String annualStock;
    @JsonProperty(GENDER)
    private String gender;
    @JsonProperty(ADD_CMT)
    private String additionalCmt;

    @Override
    public String toString() {
        return "SalarySurvey{timestamp='" + timestamp + "', employerName='" + employerName + "', location=" + location + "', jobTitle='" + jobTitle
                + "', yearAtEmployer='" + yearAtEmployer + "', yearOfExperience='" + yearOfExperience + "', salary='" + salary
                + "', signingBonus='" + signingBonus + "', annualBonus='" + annualBonus + "', annualStock='" + annualStock
                + "', gender='" + gender + "', additionalCmt='" + additionalCmt + '}';
    }
}
