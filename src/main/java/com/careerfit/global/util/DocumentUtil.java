package com.careerfit.global.util;

import static java.lang.Long.parseLong;

public class DocumentUtil {

    public static final String PATH_SEPARATOR = "/";
    public static final String NAME_SEPARATOR = "_";
    public static final String APPLICATION_PREFIX = "applications";
    public static final String RESUME_PREFIX = "resumes";
    public static final String PORTFOLIO_PREFIX = "portfolios";

    public static Long extractApplicationId(String filePath) {
        return parseLong(filePath.split(PATH_SEPARATOR)[1]);
    }

    public static String extractDocumentTitle(String filePath) {
        String[] parts = filePath.split(PATH_SEPARATOR);
        String[] nameParts = parts[3].split(NAME_SEPARATOR, 3);
        return nameParts[1];
    }

    public static String extractOriginalFileName(String filePath) {
        String[] parts = filePath.split(PATH_SEPARATOR);
        String[] nameParts = parts[3].split(NAME_SEPARATOR, 3);
        return nameParts[2];
    }

}
