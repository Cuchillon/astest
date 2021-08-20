package com.ferick.helpers;

import com.ferick.alexander.Datagen;
import com.ferick.environment.TestContext;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class BaseMethods {

    private static final String BASE_URL_SYSTEM_PATH = "common.baseUrl";
    private static final String BASE_URL_CONFIG_PATH = "%s.baseUrl";
    private static final Path RESOURCE_PATH = Paths.get("src/test/resources");
    private static final Path TESTDATA_PATH = Paths.get("data");

    private final TestContext context;

    public BaseMethods(TestContext context) {
        this.context = context;
    }

    public RequestSpecification getBaseSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(getBaseUrl())
                .setAccept(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public String getBodyFromTemplate(String templatePath, Map<String, String> substituteValues) {
        var bodyTemplate = readStringFromFile(TESTDATA_PATH.resolve(templatePath));
        return new Datagen().get(bodyTemplate, substituteValues);
    }

    private String getBaseUrl() {
        return context.properties().loadPropertyOrDefault(BASE_URL_SYSTEM_PATH,
                context.getRootConfigNames().stream()
                        .map(rootName -> String.format(BASE_URL_CONFIG_PATH, rootName))
                        .map(context.properties()::loadStringProperty)
                        .findFirst().orElse("http://localhost"));
    }

    private String readStringFromFile(Path path) {
        try {
            return Files.readString(RESOURCE_PATH.resolve(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file " + path, e);
        }
    }
}
