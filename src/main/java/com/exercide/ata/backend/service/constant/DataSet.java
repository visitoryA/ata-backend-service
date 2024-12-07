package com.exercide.ata.backend.service.constant;

import com.exercide.ata.backend.service.constant.DeclaredFields.SalarySurveyCsvIndex;
import com.exercide.ata.backend.service.model.jobdata.SalarySurvey;
import com.exercide.ata.backend.service.util.ValidatorUtil;
import com.opencsv.CSVReader;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataSet {

    @Value("${data-set.csv-path}")
    private String csvFilePath;
    @Getter
    private static final List<SalarySurvey> dataSet = new ArrayList<>();

    @PostConstruct
    public void init() {
        if (dataSet.isEmpty()) {
            try {
                FileReader fileReader = new FileReader(csvFilePath);
                CSVReader csvReader = new CSVReader(fileReader);

                csvReader.skip(1); // skip header
                csvReader.iterator().forEachRemaining(line -> {
                    if (Strings.isNotBlank(line[SalarySurveyCsvIndex.JOB_TITLE]) &&
                            ValidatorUtil.validateCurrency(line[SalarySurveyCsvIndex.SALARY])) {
                        SalarySurvey survey = SalarySurvey.builder()
                                .timestamp(line[SalarySurveyCsvIndex.TIMESTAMP])
                                .employerName(line[SalarySurveyCsvIndex.EMPLOYER])
                                .location(line[SalarySurveyCsvIndex.LOCATION])
                                .jobTitle(line[SalarySurveyCsvIndex.JOB_TITLE])
                                .yearAtEmployer(line[SalarySurveyCsvIndex.YEAR_EMP])
                                .yearOfExperience(line[SalarySurveyCsvIndex.YEAR_EXP])
                                .salary(line[SalarySurveyCsvIndex.SALARY])
                                .signingBonus(line[SalarySurveyCsvIndex.SIGN_BONUS])
                                .annualBonus(line[SalarySurveyCsvIndex.ANNUAL_BONUS])
                                .annualStock(line[SalarySurveyCsvIndex.ANNUAL_STOCK])
                                .gender(line[SalarySurveyCsvIndex.GENDER])
                                .additionalCmt(line[SalarySurveyCsvIndex.ADD_CMT])
                                .build();
                        dataSet.add(survey);
                    }
                });
            } catch (IOException ex) {
                System.out.println("Cannot get static data from CSV file");
            }
        }
    }

}
