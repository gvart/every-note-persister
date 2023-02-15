package com.everynote.perister.config;

import com.everynote.perister.config.dynamodb.CustomDynamoDbTableSchemaResolver;
import com.everynote.perister.gateway.dynamodb.model.Note;
import io.awspring.cloud.dynamodb.DefaultDynamoDbTableNameResolver;
import io.awspring.cloud.dynamodb.DynamoDbTableNameResolver;
import io.awspring.cloud.dynamodb.DynamoDbTableSchemaResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema;

import java.util.List;
import java.util.Optional;

import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primaryPartitionKey;

@Configuration
public class DynamoDbConfig {

    @Bean
    public DynamoDbTableSchemaResolver resolver(Optional<DynamoDbTableNameResolver> tableNameResolver, List<TableSchema<?>> tableSchemas) {
        var resolver = new CustomDynamoDbTableSchemaResolver();
        resolver.prefillCache(
                tableNameResolver.orElseGet(DefaultDynamoDbTableNameResolver::new),
                tableSchemas
        );

        return resolver;
    }

    @Bean
    public TableSchema<Note> noteSchema() {
        return StaticTableSchema
                .builder(Note.class)
                .newItemSupplier(Note::new)
                .addAttribute(String.class, it -> it.name("id")
                        .getter(Note::getId)
                        .setter(Note::setId)
                        .tags(primaryPartitionKey())
                )
                .build();
    }

}
