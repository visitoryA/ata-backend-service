package com.exercide.ata.backend.service.model.jobdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class JobData {
    private String jobTitle;
    private String employerName;
    private String gender;
    private String location;
    private String timestamp;
    private String yearAtEmployer;
    private String yearOfExperience;
    private String salary;
    private String signingBonus;
    private String annualBonus;
    private String annualStock;
    private String additionalCmt;
}
