package com.exercide.ata.backend.service.constant;

import com.exercide.ata.backend.service.model.jobdata.JobDataRequest;
import com.exercide.ata.backend.service.util.CommonUtil;
import org.apache.logging.log4j.util.Strings;

import static com.exercide.ata.backend.service.constant.DeclaredFields.JobDataRequestFields.GENDER;

public enum GenderEnum {
    F("Female"),
    M("Male"),
    N("None");

    private final String gender;

    GenderEnum(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return this.gender;
    }

    public static void validate(JobDataRequest request, StringBuilder strBuilder) {
        if (Strings.isNotBlank(request.getGender())) {
            for (GenderEnum enumVal : values()) {
                if (enumVal.name().equalsIgnoreCase(request.getGender())) {
                    return;
                }
            }
            CommonUtil.appendStrBuilder(strBuilder, GENDER);
        }
    }
}
