package com.everynote.perister.config;

import io.awspring.cloud.dynamodb.DynamoDbTableSchemaResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema;

@Configuration
public class DynamoDbConfig {

    @Bean
    public DynamoDbTableSchemaResolver schema() {
        return new DynamoDbTableSchemaResolver() {
            @Override
            public <T> TableSchema<T> resolve(Class<T> clazz, String tableName) {
                return StaticTableSchema.builder(clazz).build();
            }
        };
    }
}
