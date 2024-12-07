package com.exercide.ata.backend.service.controller;

import com.exercide.ata.backend.service.constant.DeclaredFields;
import com.exercide.ata.backend.service.constant.DeclaredFields.JobDataRequestFields;
import com.exercide.ata.backend.service.model.jobdata.JobDataResponse;
import com.exercide.ata.backend.service.service.JobDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.exercide.ata.backend.service.constant.CommonConstant.JSON_CONTENT_TYPE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class JobDataControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private JobDataController controller;

    @Mock
    private JobDataService service;

    private static final String URI = "/v1/job_data";

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(controller, "defaultPage", 1);
        ReflectionTestUtils.setField(controller, "defaultLimit", 5);
    }

    @Test
    void getJobDataSuccessWithoutParam() throws Exception {
        doReturn(JobDataResponse.builder().build()).when(service).getJobData(any());
        mockMvc.perform(MockMvcRequestBuilders.get(URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void getJobListSuccessWithParam() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URI)
                        .param(JobDataRequestFields.JOB_TITLE, "QA")
                        .param(JobDataRequestFields.EMP_NAME, "company")
                        .param(JobDataRequestFields.GENDER, "M")
                        .param(JobDataRequestFields.MIN_SAL, "300,000.0")
                        .param(JobDataRequestFields.MAX_SAL, "500000")
                        .param(JobDataRequestFields.LOCATION, "TH")
                        .param(DeclaredFields.FIELDS, "job_title")
                        .param(DeclaredFields.SORT, "job_title")
                        .param(DeclaredFields.SORT_TYPE, "desc")
                        .param(DeclaredFields.PAGE, "1")
                        .param(DeclaredFields.LIMIT, "5"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void getJobListWithMissingField() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URI)
                        .param(DeclaredFields.SORT_TYPE, "desc"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.status_code").value("AT4001"))
                .andExpect(jsonPath("$.message").value("Bad Request"))
                .andExpect(jsonPath("$.description").value("sort is missing"));
    }

    @Test
    void getJobListWithInvalidFields() throws Exception {
        String expDesc = "salary_gte, salary_lte, gender, fields, sort, sort_type, page, limit is invalid";
        mockMvc.perform(MockMvcRequestBuilders.get(URI)
                        .param(JobDataRequestFields.GENDER, "Male")
                        .param(JobDataRequestFields.MIN_SAL, "300,000.0.0")
                        .param(JobDataRequestFields.MAX_SAL, "5000,00")
                        .param(DeclaredFields.FIELDS, "job_id")
                        .param(DeclaredFields.SORT, "job_id")
                        .param(DeclaredFields.SORT_TYPE, "job_id")
                        .param(DeclaredFields.PAGE, "0")
                        .param(DeclaredFields.LIMIT, "-1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.status_code").value("AT4002"))
                .andExpect(jsonPath("$.message").value("Bad Request"))
                .andExpect(jsonPath("$.description").value(expDesc));
    }

    @Test
    void getJobListWithSomeInvalidFields() throws Exception {
        String expDesc = "salary_gte, salary_lte, page, limit is invalid";
        mockMvc.perform(MockMvcRequestBuilders.get(URI)
                        .param(JobDataRequestFields.MIN_SAL, "300")
                        .param(JobDataRequestFields.MAX_SAL, "100")
                        .param(DeclaredFields.PAGE, "-10")
                        .param(DeclaredFields.LIMIT, "0"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(JSON_CONTENT_TYPE))
                .andExpect(jsonPath("$.status_code").value("AT4002"))
                .andExpect(jsonPath("$.message").value("Bad Request"))
                .andExpect(jsonPath("$.description").value(expDesc));
    }
}
