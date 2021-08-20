package com.ferick.helpers;

import com.ferick.environment.TestContext;

public class BaseMethods {

    private static final String BASE_URL_SYSTEM_PATH = "common.baseUrl";
    private static final String BASE_URL_CONFIG_PATH = "%s.baseUrl";

    private final TestContext context;

    public BaseMethods(TestContext context) {
        this.context = context;
    }

    public String getBaseUrl() {
        return context.properties().loadPropertyOrDefault(BASE_URL_SYSTEM_PATH,
                context.getRootConfigNames().stream()
                        .map(rootName -> String.format(BASE_URL_CONFIG_PATH, rootName))
                        .map(context.properties()::loadStringProperty)
                        .findFirst().orElse("http://localhost"));
    }
}
