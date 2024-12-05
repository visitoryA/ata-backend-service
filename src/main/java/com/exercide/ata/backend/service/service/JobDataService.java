package com.exercide.ata.backend.service.service;

import com.exercide.ata.backend.service.constant.*;
import com.exercide.ata.backend.service.mapper.JobDataMapper;
import com.exercide.ata.backend.service.model.jobdata.*;
import com.exercide.ata.backend.service.util.CommonUtil;
import com.exercide.ata.backend.service.util.ValidatorUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class JobDataService {
    private DataSet dataSet;
    private JobDataMapper jobDataMapper;

    public JobDataResponse getJobData(JobDataRequest request) {
        List<SalarySurvey> filteredSurvey = filterData(dataSet.getDataSet().stream(), request);
        int totalJob = filteredSurvey.size();
        int page = request.getPageInt();
        int firstIndex = (page - 1) * request.getLimitInt();
        int lastIndex = page * request.getLimitInt();

        // sorted list
        if (!ObjectUtils.isEmpty(request.getSortFields())) {
            ArrayList<SalarySurvey> sortedSurvey = new ArrayList<>(filteredSurvey);
            sortData(sortedSurvey, request.getSortFields(), request.getSortType());
            filteredSurvey = sortedSurvey;
        }

        // filter column
        List<SalarySurvey> result = filteredSurvey.subList(firstIndex, Math.min(lastIndex, totalJob));
        List<JobData> jobData = jobDataMapper.map(result, request.getFields());

        JobDataList dataList = JobDataList.builder()
                .totalJob(totalJob)
                .page(request.getPageInt())
                .limit(request.getLimitInt())
                .jobData(jobData)
                .build();
        return JobDataResponse.builder()
                .statusCode(ResponseMessageEnum.SUCCESS.getCode())
                .message(ResponseMessageEnum.SUCCESS.getMessage())
                .data(dataList)
                .build();
    }

    private List<SalarySurvey> filterData(Stream<SalarySurvey> data, JobDataRequest request) {
        if (request.getJobTitle() != null) {
            data = filterByJobTitle(data, request.getJobTitle());
        }
        if (request.getEmployerName() != null) {
            data = filterByEmployer(data, request.getEmployerName());
        }
        if (request.getLocation() != null) {
            data = filterByLocation(data, request.getLocation());
        }
        if (request.getMinSalary() != null || request.getMaxSalary() != null) {
            data = filterBySalary(data, request.getMinSalary(), request.getMaxSalary());
        }
        if (request.getGender() != null) {
            data = filterByGender(data, request.getGender());
        }
        return data.toList();
    }

    private Stream<SalarySurvey> filterByJobTitle(Stream<SalarySurvey> data, String jobTitle) {
        String regex = createRegexByString(jobTitle);
        return data.filter(it -> ValidatorUtil.validateWithRegex(regex, it.getJobTitle()));
    }
    private Stream<SalarySurvey> filterByEmployer(Stream<SalarySurvey> data, String employer) {
        String regex = createRegexByString(employer);
        return data.filter(it -> ValidatorUtil.validateWithRegex(regex, it.getEmployerName()));
    }
    private Stream<SalarySurvey> filterByLocation(Stream<SalarySurvey> data, String location) {
        String regex = createRegexByString(location);
        return data.filter(it -> ValidatorUtil.validateWithRegex(regex, it.getLocation()));
    }
    private Stream<SalarySurvey> filterBySalary(Stream<SalarySurvey> data, String minSalaray, String maxSalary) {
        return data.filter(it -> {
            Integer dataSalary = CommonUtil.convertCurrency(it.getSalary());
            Integer minSalaryInt = CommonUtil.convertCurrency(minSalaray);
            Integer maxSalaryInt = CommonUtil.convertCurrency(maxSalary);
            if (dataSalary != null) {
                if (minSalaryInt != null) {
                    if (maxSalaryInt != null) {
                        return dataSalary >= minSalaryInt && dataSalary <= maxSalaryInt;
                    } else {
                        return dataSalary >= minSalaryInt;
                    }
                } else if (maxSalaryInt != null) {
                    return dataSalary <= maxSalaryInt;
                }
            }
            return false;
        });
    }
    private Stream<SalarySurvey> filterByGender(Stream<SalarySurvey> data, String gender) {
        String cmpVal = GenderEnum.valueOf(gender).getGender();
        return data.filter(it -> cmpVal.equalsIgnoreCase(it.getGender()));
    }

    private void sortData(List<SalarySurvey> data, List<String> fieldNames, String sortType) {
        Comparator<SalarySurvey> comparator = null;
        for (String field : fieldNames) {
            if (comparator == null) {
                comparator = getComparator(field);
            } else {
                comparator = comparator.thenComparing(getComparator(field));
            }
        }
        if (CommonConstant.SORT_TYPE.DESC.equalsIgnoreCase(sortType) && comparator != null) {
            comparator = comparator.reversed();
        }
        data.sort(comparator);
    }

    private Comparator<SalarySurvey> getComparator(String fieldName) {
        return switch (fieldName) {
            case DeclaredFields.JobDataFields.JOB_TITLE -> Comparator.comparing(SalarySurvey::getJobTitle);
            case DeclaredFields.JobDataFields.EMP_NAME -> Comparator.comparing(SalarySurvey::getEmployerName);
            case DeclaredFields.JobDataFields.GENDER -> Comparator.comparing(SalarySurvey::getGender);
            case DeclaredFields.JobDataFields.LOCATION -> Comparator.comparing(SalarySurvey::getLocation);
            case DeclaredFields.JobDataFields.SALARY -> Comparator.comparing(SalarySurvey::getSalary);
            case DeclaredFields.JobDataFields.YEAR_EMP -> Comparator.comparing(SalarySurvey::getYearAtEmployer);
            case DeclaredFields.JobDataFields.YEAR_EXP -> Comparator.comparing(SalarySurvey::getYearOfExperience);
            case DeclaredFields.JobDataFields.SIGN_BONUS -> Comparator.comparing(SalarySurvey::getSigningBonus);
            case DeclaredFields.JobDataFields.ANNUAL_BONUS -> Comparator.comparing(SalarySurvey::getAnnualBonus);
            case DeclaredFields.JobDataFields.ANNUAL_STOCK -> Comparator.comparing(SalarySurvey::getAnnualStock);
            case DeclaredFields.JobDataFields.ADD_CMT -> Comparator.comparing(SalarySurvey::getAdditionalCmt);

            default -> Comparator.comparing(SalarySurvey::getTimestamp);
        };
    }

    private String createRegexByString(String value) {
        String[] words = value.split(CommonConstant.SPACE);
        return "^.*(" + String.join(CommonConstant.PIPE, words) + ").*$";
    }
}
