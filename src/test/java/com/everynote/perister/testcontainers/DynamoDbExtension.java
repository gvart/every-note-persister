package com.everynote.perister.testcontainers;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

public class DynamoDbExtension implements BeforeAllCallback, AfterAllCallback {

    private static DockerImageName localstackImage = DockerImageName.parse("localstack/localstack:latest");

    private static LocalStackContainer CONTAINER = new LocalStackContainer(localstackImage)
            .withServices(LocalStackContainer.Service.DYNAMODB)
            .withReuse(true);

    @Override
    public void beforeAll(ExtensionContext context) {
        CONTAINER.start();
        System.setProperty("spring.cloud.aws.dynamodb.region", CONTAINER.getRegion());
        System.setProperty("spring.cloud.aws.dynamodb.endpoint", CONTAINER.getEndpointOverride(LocalStackContainer.Service.DYNAMODB).toString());
    }

    @Override
    public void afterAll(ExtensionContext context) {
        CONTAINER.stop();
    }
}
