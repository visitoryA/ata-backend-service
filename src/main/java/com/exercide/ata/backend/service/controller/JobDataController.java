package com.exercide.ata.backend.service.controller;

import com.exercide.ata.backend.service.constant.CommonConstant;
import com.exercide.ata.backend.service.constant.DeclaredFields;
import com.exercide.ata.backend.service.constant.DeclaredFields.JobDataRequestFields;
import com.exercide.ata.backend.service.constant.GenderEnum;
import com.exercide.ata.backend.service.constant.ResponseMessageEnum;
import com.exercide.ata.backend.service.exception.BusinessException;
import com.exercide.ata.backend.service.model.jobdata.JobDataRequest;
import com.exercide.ata.backend.service.model.jobdata.JobDataResponse;
import com.exercide.ata.backend.service.service.JobDataService;
import com.exercide.ata.backend.service.util.CommonUtil;
import com.exercide.ata.backend.service.util.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Locale;

import static com.exercide.ata.backend.service.constant.CommonConstant.JSON_CONTENT_TYPE;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/v1")
public class JobDataController {
    private final JobDataService jobDataService;

    @Value("${api-config.default-page}")
    private int defaultPage;

    @Value("${api-config.default-limit}")
    private int defaultLimit;

    @GetMapping(value = "/job_data", produces = JSON_CONTENT_TYPE)
    public ResponseEntity<JobDataResponse> getJobList(
            @RequestParam(value = JobDataRequestFields.JOB_TITLE, required = false) String jobTitle,
            @RequestParam(value = JobDataRequestFields.EMP_NAME, required = false) String employerName,
            @RequestParam(value = JobDataRequestFields.LOCATION, required = false) String localtion,
            @RequestParam(value = JobDataRequestFields.MIN_SAL, required = false) String minSalary,
            @RequestParam(value = JobDataRequestFields.MAX_SAL, required = false) String maxSalary,
            @RequestParam(value = JobDataRequestFields.GENDER, required = false) String gender,
            @RequestParam(value = DeclaredFields.FIELDS, required = false) List<String> fields,
            @RequestParam(value = DeclaredFields.SORT, required = false) List<String> sortField,
            @RequestParam(value = DeclaredFields.SORT_TYPE, required = false) String sortType,
            @RequestParam(value = DeclaredFields.PAGE, required = false) String page,
            @RequestParam(value = DeclaredFields.LIMIT, required = false) String limit
    ) throws BusinessException {
        JobDataRequest request = JobDataRequest.builder()
                .jobTitle(jobTitle)
                .employerName(employerName)
                .location(localtion)
                .minSalary(minSalary)
                .maxSalary(maxSalary)
                .gender(gender)
                .fields(fields)
                .sortFields(sortField).sortType(sortType)
                .page(page).limit(limit)
                .build();

        missingRequest(request);
        validateRequest(request);
        return ResponseEntity.ok(jobDataService.getJobData(request));
    }

    private void missingRequest(JobDataRequest request) throws BusinessException {
        StringBuilder missingFields = new StringBuilder();

        if (request.getSortFields() == null && request.getSortType() != null) {
            CommonUtil.appendStrBuilder(missingFields, DeclaredFields.SORT);
        }

        if (!missingFields.isEmpty()) {
            throw new BusinessException(ResponseMessageEnum.MISSING_REQ, missingFields.toString());
        }
    }

    private void validateRequest(JobDataRequest request) throws BusinessException {
        StringBuilder invalidFields = new StringBuilder();

        int minSalaryReq = 0;
        int maxSalaryReq = 0;
        // validate minSalary, maxSalary
        if (Strings.isNotBlank(request.getMinSalary())) {
            if (ValidatorUtil.validateCurrency(request.getMinSalary())) {
                minSalaryReq = CommonUtil.convertCurrency(request.getMinSalary());
            } else {
                CommonUtil.appendStrBuilder(invalidFields, JobDataRequestFields.MIN_SAL);
            }
        }

        if (Strings.isNotBlank(request.getMaxSalary())) {
            if (ValidatorUtil.validateCurrency(request.getMaxSalary())) {
                maxSalaryReq = CommonUtil.convertCurrency(request.getMaxSalary());
                if (maxSalaryReq < minSalaryReq) {
                    CommonUtil.appendStrBuilder(invalidFields, JobDataRequestFields.MIN_SAL);
                    CommonUtil.appendStrBuilder(invalidFields, JobDataRequestFields.MAX_SAL);
                }
            } else {
                CommonUtil.appendStrBuilder(invalidFields, JobDataRequestFields.MAX_SAL);
            }
        }

        GenderEnum.validate(request, invalidFields);

        Field[] validateFieldName = DeclaredFields.JobDataFields.class.getDeclaredFields();

        if (!ObjectUtils.isEmpty(request.getFields()) &&
                ValidatorUtil.invalidFieldName(request.getFields(), validateFieldName)) {
            CommonUtil.appendStrBuilder(invalidFields, DeclaredFields.FIELDS);
        }

        if (!ObjectUtils.isEmpty(request.getSortFields()) &&
                ValidatorUtil.invalidFieldName(request.getSortFields(), validateFieldName)) {
            CommonUtil.appendStrBuilder(invalidFields, DeclaredFields.SORT);
        }

        if (request.getSortType() != null &&
                !ValidatorUtil.validateWithRegex(CommonConstant.REGEX.SORT_TYPE, request.getSortType().toUpperCase(Locale.ROOT))) {
            CommonUtil.appendStrBuilder(invalidFields, DeclaredFields.SORT_TYPE);
        }

        if (request.getPage() != null) {
            if (ValidatorUtil.validateWithRegex(CommonConstant.REGEX.NUM_FORMAT, request.getPage())) {
                int pageInt = CommonUtil.convertStrToInt(request.getPage(), null);
                if (pageInt > 0) {
                    request.setPageInt(pageInt);
                } else {
                    CommonUtil.appendStrBuilder(invalidFields, DeclaredFields.PAGE);
                }
            } else {
                CommonUtil.appendStrBuilder(invalidFields, DeclaredFields.PAGE);
            }
        } else {
            request.setPageInt(defaultPage);
        }

        if (request.getLimit() != null) {
            if (ValidatorUtil.validateWithRegex(CommonConstant.REGEX.NUM_FORMAT, request.getLimit())) {
                int limitInt = CommonUtil.convertStrToInt(request.getLimit(), null);
                if (limitInt > 0) {
                    request.setPageInt(limitInt);
                } else {
                    CommonUtil.appendStrBuilder(invalidFields, DeclaredFields.LIMIT);
                }
            } else {
                CommonUtil.appendStrBuilder(invalidFields, DeclaredFields.LIMIT);
            }
        } else {
            request.setLimitInt(defaultLimit);
        }

        if (!invalidFields.isEmpty()) {
            throw new BusinessException(ResponseMessageEnum.INVALID_REQ, invalidFields.toString());
        }
    }
}
