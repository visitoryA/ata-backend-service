package com.exercide.ata.backend.service.mapper;

import com.exercide.ata.backend.service.constant.DeclaredFields;
import com.exercide.ata.backend.service.constant.DeclaredFields.JobDataFields;
import com.exercide.ata.backend.service.model.jobdata.JobData;
import com.exercide.ata.backend.service.model.jobdata.SalarySurvey;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class JobDataMapperTest {
    @InjectMocks
    private JobDataMapper mapper;

    @Test
    void mapDataWithEmptyFieldName() {
        List<SalarySurvey> surveys = mockSalarySurvey();
        List<JobData> actualResult = mapper.map(surveys, Collections.emptyList());

        assertEquals(surveys.size(), actualResult.size());
        for (int i = 0; i < surveys.size(); i++) {
            SalarySurvey survey = surveys.get(i);
            JobData jobData = actualResult.get(i);
            assertEquals(survey.getJobTitle(), jobData.getJobTitle());
            assertEquals(survey.getEmployerName(), jobData.getEmployerName());
            assertEquals(survey.getGender(), jobData.getGender());
            assertEquals(survey.getLocation(), jobData.getLocation());
            assertEquals(survey.getSalary(), jobData.getSalary());
            assertEquals(survey.getTimestamp(), jobData.getTimestamp());
            assertEquals(survey.getYearAtEmployer(), jobData.getYearAtEmployer());
            assertEquals(survey.getYearOfExperience(), jobData.getYearOfExperience());
            assertEquals(survey.getSigningBonus(), jobData.getSigningBonus());
            assertEquals(survey.getAnnualBonus(), jobData.getAnnualBonus());
            assertEquals(survey.getAnnualStock(), jobData.getAnnualStock());
            assertEquals(survey.getAdditionalCmt(), jobData.getAdditionalCmt());
        }
    }

    @Test
    void mapDataWithAllFieldName() {
        List<String> fieldNames = List.of(JobDataFields.JOB_TITLE, JobDataFields.EMP_NAME, JobDataFields.GENDER,
                JobDataFields.LOCATION, JobDataFields.SALARY, JobDataFields.TIMESTAMP, JobDataFields.YEAR_EMP, JobDataFields.YEAR_EXP,
                JobDataFields.SIGN_BONUS, JobDataFields.ANNUAL_BONUS, JobDataFields.ANNUAL_STOCK, JobDataFields.ADD_CMT);
        List<SalarySurvey> jobLists = mockSalarySurvey();
        List<JobData> actualResult = mapper.map(jobLists, fieldNames);

        assertEquals(jobLists.size(), actualResult.size());
        for (int i = 0; i < jobLists.size(); i++) {
            SalarySurvey survey = jobLists.get(i);
            JobData jobData = actualResult.get(i);
            assertEquals(survey.getJobTitle(), jobData.getJobTitle());
            assertEquals(survey.getEmployerName(), jobData.getEmployerName());
            assertEquals(survey.getGender(), jobData.getGender());
            assertEquals(survey.getLocation(), jobData.getLocation());
            assertEquals(survey.getSalary(), jobData.getSalary());
            assertEquals(survey.getTimestamp(), jobData.getTimestamp());
            assertEquals(survey.getYearAtEmployer(), jobData.getYearAtEmployer());
            assertEquals(survey.getYearOfExperience(), jobData.getYearOfExperience());
            assertEquals(survey.getSigningBonus(), jobData.getSigningBonus());
            assertEquals(survey.getAnnualBonus(), jobData.getAnnualBonus());
            assertEquals(survey.getAnnualStock(), jobData.getAnnualStock());
            assertEquals(survey.getAdditionalCmt(), jobData.getAdditionalCmt());
        }
    }

    @Test
    void mapDataWithFieldNames() {
        List<String> fieldNames = List.of(DeclaredFields.JobDataFields.SALARY);
        List<SalarySurvey> jobLists = mockSalarySurvey();
        List<JobData> actualResult = mapper.map(jobLists, fieldNames);

        assertEquals(jobLists.size(), actualResult.size());
        for (int i = 0; i < jobLists.size(); i++) {
            SalarySurvey job = jobLists.get(i);
            JobData jobData = actualResult.get(i);

            assertEquals(job.getJobTitle(), jobData.getJobTitle());
            assertEquals(job.getSalary(), jobData.getSalary());
            assertNull(jobData.getEmployerName());
            assertNull(jobData.getGender());
            assertNull(jobData.getLocation());
            assertNull(jobData.getTimestamp());
            assertNull(jobData.getYearAtEmployer());
            assertNull(jobData.getYearOfExperience());
            assertNull(jobData.getSigningBonus());
            assertNull(jobData.getAnnualBonus());
            assertNull(jobData.getAnnualStock());
            assertNull(jobData.getAdditionalCmt());
        }
    }

    private List<SalarySurvey> mockSalarySurvey() {
        SalarySurvey survey1 = SalarySurvey.builder()
                .jobTitle("jobTitle").salary("30,000")
                .gender("Female").location("").timestamp("12/24/2013 11:00:12")
                .employerName("SOP").yearAtEmployer("5+").yearOfExperience("5+")
                .signingBonus("50,000").annualBonus("30,000").annualStock("2%").additionalCmt("").build();
        SalarySurvey survey2 = SalarySurvey.builder()
                .jobTitle("jobTitle2").salary("10000")
                .gender("Mail").location("Tokyo, JP").timestamp("1/27/2014 11:00:12")
                .employerName("").yearAtEmployer("0.5").yearOfExperience("3")
                .signingBonus("").annualBonus("20%").annualStock("1%").additionalCmt("Take note").build();
        return List.of(survey1, survey2);
    }
}
