package com.everynote.perister.config.dynamodb;

import io.awspring.cloud.dynamodb.DefaultDynamoDbTableSchemaResolver;
import io.awspring.cloud.dynamodb.DynamoDbTableNameResolver;
import io.awspring.cloud.dynamodb.DynamoDbTableSchemaResolver;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

/**
 * This custom implementation is necessary due to limitations of {@link
 * DefaultDynamoDbTableSchemaResolver} which doesn't allow to register manually a TableSchema, could
 * be fixed when a feature got merged and released in version 3.1.
 *
 * <p>Issue to track: https://github.com/awspring/spring-cloud-aws/issues/674
 */
public class CustomDynamoDbTableSchemaResolver implements DynamoDbTableSchemaResolver {

  private final Map<String, TableSchema<?>> tableSchemaCache = new ConcurrentHashMap<>();

  @Override
  public <T> TableSchema resolve(Class<T> clazz, String tableName) {
    return tableSchemaCache.get(tableName);
  }

  public void prefillCache(DynamoDbTableNameResolver resolver, List<TableSchema<?>> tableSchemas) {
    tableSchemas.forEach(
        it -> {
          var key = resolver.resolve(it.itemType().rawClass());
          tableSchemaCache.put(key, it);
        });
  }
}
