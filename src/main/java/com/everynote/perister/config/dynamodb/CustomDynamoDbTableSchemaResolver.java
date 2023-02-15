package com.everynote.perister.config.dynamodb;

import io.awspring.cloud.dynamodb.DynamoDbTableNameResolver;
import io.awspring.cloud.dynamodb.DynamoDbTableSchemaResolver;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 */
@Component
public class CustomDynamoDbTableSchemaResolver implements DynamoDbTableSchemaResolver {

    private final Map<String, TableSchema<?>> tableSchemaCache = new ConcurrentHashMap<>();


    @Override
    public <T> TableSchema resolve(Class<T> clazz, String tableName) {
        return tableSchemaCache.get(tableName);
    }

    public void prefillCache(DynamoDbTableNameResolver resolver, List<TableSchema<?>> tableSchemas) {
        tableSchemas.forEach(it -> {
            var key = resolver.resolve(it.itemType().rawClass());
            tableSchemaCache.put(key, it);
        });
    }
}
