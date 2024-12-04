package com.exercide.ata.backend.service.deserializer;

import com.exercide.ata.backend.service.model.jobdata.SalarySurvey;
import com.exercide.ata.backend.service.util.ValidatorUtil;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

import static com.exercide.ata.backend.service.constant.DeclaredFields.SalarySurveyFields.*;

public class SalarySurveyDeserializer extends JsonDeserializer<SalarySurvey> {
    @Override
    public SalarySurvey deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        // Parse the CSV node
        ObjectNode node = jsonParser.getCodec().readTree(jsonParser);

        // Check for empty fields (you can customize this based on your definition of "empty")
        String jobTitle = node.get(JOB_TITLE).asText();
        String salary = node.get(SALARY).asText();

        // Skip the object if any field is empty
        if (jobTitle.replaceAll("\\s","").isEmpty() ||
                !ValidatorUtil.validateCurrency(salary)) {
            return null;  // Returning null means this record will be skipped
        }

        // Otherwise, create and return the person object
        return SalarySurvey.builder()
                .timestamp(node.get(TIMESTAMP).asText())
                .employerName(node.get(EMPLOYER).asText())
                .location(node.get(LOCATION).asText())
                .jobTitle(jobTitle)
                .yearAtEmployer(node.get(YEAR_EMP).asText())
                .yearOfExperience(node.get(YEAR_EXP).asText())
                .salary(salary)
                .signingBonus(node.get(SIGN_BONUS).asText())
                .annualBonus(node.get(ANNUAL_BONUS).asText())
                .annualStock(node.get(ANNUAL_STOCK).asText())
                .gender(node.get(GENDER).asText())
                .additionalCmt(node.get(ADD_CMT).asText())
                .build();
    }
}
