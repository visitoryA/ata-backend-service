package com.exercide.ata.backend.service.model.jobdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class JobDataList {
    private int totalJob;
    private Integer page;
    private Integer limit;
    private List<JobData> jobData;
}
