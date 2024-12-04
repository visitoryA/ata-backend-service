package com.exercide.ata.backend.service.mapper;

import com.exercide.ata.backend.service.constant.DeclaredFields;
import com.exercide.ata.backend.service.model.jobdata.JobData;
import com.exercide.ata.backend.service.model.jobdata.SalarySurvey;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class JobDataMapper {
    public List<JobData> map(List<SalarySurvey> data, List<String> fields) {
        List<JobData> jobLists = new ArrayList<>();
        boolean hasFilteredField = !ObjectUtils.isEmpty(fields);

        data.forEach(it ->
                jobLists.add(hasFilteredField ? mapDataWithFilteredField(it, fields) : mapDataWithAllField(it))
        );
        return jobLists;
    }

    private JobData mapDataWithFilteredField(SalarySurvey job, List<String> fields) {
        JobData dataResp = JobData.builder()
                .jobTitle(job.getJobTitle())
                .build();
        for (String fieldName : fields) {
            setObjectValue(dataResp, job, fieldName);
        }
        return dataResp;
    }

    private JobData mapDataWithAllField(SalarySurvey job) {
        return JobData.builder()
                .jobTitle(job.getJobTitle())
                .employerName(job.getEmployerName())
                .gender(job.getGender())
                .location(job.getLocation())
                .timestamp(job.getTimestamp())
                .yearAtEmployer(job.getYearAtEmployer())
                .yearOfExperience(job.getYearOfExperience())
                .salary(job.getSalary())
                .signingBonus(job.getSigningBonus())
                .annualBonus(job.getAnnualBonus())
                .annualStock(job.getAnnualStock())
                .additionalCmt(job.getAdditionalCmt()).build();
    }

    private void setObjectValue(JobData data, SalarySurvey rawData, String fieldName) {
        switch (fieldName) {
            case DeclaredFields.JobDataFields.EMP_NAME:
                data.setEmployerName(rawData.getEmployerName());
                break;
            case DeclaredFields.JobDataFields.GENDER:
                data.setGender(rawData.getGender());
                break;
            case DeclaredFields.JobDataFields.LOCATION:
                data.setLocation(rawData.getLocation());
                break;
            case DeclaredFields.JobDataFields.TIMESTAMP:
                data.setTimestamp(rawData.getTimestamp());
                break;
            case DeclaredFields.JobDataFields.YEAR_EMP:
                data.setYearAtEmployer(rawData.getYearAtEmployer());
                break;
            case DeclaredFields.JobDataFields.YEAR_EXP:
                data.setYearOfExperience(rawData.getYearOfExperience());
                break;
            case DeclaredFields.JobDataFields.SALARY:
                data.setSalary(rawData.getSalary());
                break;
            case DeclaredFields.JobDataFields.SIGN_BONUS:
                data.setSigningBonus(rawData.getSigningBonus());
                break;
            case DeclaredFields.JobDataFields.ANNUAL_BONUS:
                data.setAnnualBonus(rawData.getAnnualBonus());
                break;
            case DeclaredFields.JobDataFields.ANNUAL_STOCK:
                data.setAnnualStock(rawData.getAnnualStock());
                break;
            case DeclaredFields.JobDataFields.ADD_CMT:
                data.setAdditionalCmt(rawData.getAdditionalCmt());
                break;
        }
    }
}
