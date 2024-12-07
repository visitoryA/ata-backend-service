package com.exercide.ata.backend.service.model.jobdata;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class JobDataRequest {
    private String jobTitle;
    private String employerName;
    private String location;
    private String minSalary;
    private String maxSalary;
    private String gender;
    private List<String> fields;
    private List<String> sortFields;
    private String sortType;
    private String page;
    private String limit;
    private int pageInt;
    private int limitInt;
}
