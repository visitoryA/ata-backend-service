package com.exercide.ata.backend.service.constant;

import com.exercide.ata.backend.service.deserializer.SalarySurveyDeserializer;
import com.exercide.ata.backend.service.model.jobdata.SalarySurvey;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class DataSet {
    @Getter
    private static final List<SalarySurvey> dataSet = new ArrayList<>();

    @PostConstruct
    public void init() {
        if (dataSet.isEmpty()) {
            try {
                CsvMapper mapper = new CsvMapper();

                // Register your custom deserializer
                mapper.registerModule(new SimpleModule().addDeserializer(SalarySurvey.class, new SalarySurveyDeserializer()));

                CsvSchema schema = CsvSchema.emptySchema().withHeader();  // Assuming CSV has headers

                MappingIterator<SalarySurvey> iterator = mapper.readerFor(SalarySurvey.class)
                        .with(schema)
                        .readValues(new File("src/main/resources/dataset/salary_survey-3.csv"));
                iterator.forEachRemaining(dataSet::add);

                // Filter out null objects (those that were skipped due to empty fields)
                dataSet.removeIf(Objects::isNull);
            } catch (IOException ex) {
                System.out.println("Cannot get static data from CSV file");
            }
        }
    }

}
