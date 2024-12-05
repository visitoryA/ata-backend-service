package com.exercide.ata.backend.service.model.jobdata;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class JobDataResponse {
    private String statusCode;
    private String message;
    private JobDataList data;
}
