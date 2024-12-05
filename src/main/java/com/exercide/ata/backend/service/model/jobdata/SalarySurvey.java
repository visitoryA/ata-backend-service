package com.exercide.ata.backend.service.model.jobdata;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SalarySurvey {
    private String timestamp;
    private String employerName;
    private String location;
    private String jobTitle;
    private String yearAtEmployer;
    private String yearOfExperience;
    private String salary;
    private String signingBonus;
    private String annualBonus;
    private String annualStock;
    private String gender;
    private String additionalCmt;

}
