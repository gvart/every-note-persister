package com.everynote.perister.config;

import static software.amazon.awssdk.enhanced.dynamodb.mapper.StaticAttributeTags.primaryPartitionKey;

import com.everynote.perister.config.dynamodb.CustomDynamoDbTableSchemaResolver;
import com.everynote.perister.gateway.dynamodb.model.Note;
import io.awspring.cloud.dynamodb.DefaultDynamoDbTableNameResolver;
import io.awspring.cloud.dynamodb.DynamoDbTableNameResolver;
import io.awspring.cloud.dynamodb.DynamoDbTableSchemaResolver;
import java.util.List;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.BeanTableSchema;
import software.amazon.awssdk.enhanced.dynamodb.mapper.StaticTableSchema;

@Configuration
public class DynamoDbConfig {

  /**
   * Custom {@link DynamoDbTableSchemaResolver} implementation which loads all TableSchemas and
   * prefills the cache.
   */
  @Bean
  public DynamoDbTableSchemaResolver resolver(
      Optional<DynamoDbTableNameResolver> tableNameResolver, List<TableSchema<?>> tableSchemas) {
    var resolver = new CustomDynamoDbTableSchemaResolver();
    resolver.prefillCache(
        tableNameResolver.orElseGet(DefaultDynamoDbTableNameResolver::new), tableSchemas);

    return resolver;
  }

  /**
   * At the moment {@link BeanTableSchema} is not supported on GraalVM, and we can't use annotations
   * to define TableSchema on top of our entity, because of that a {@link StaticTableSchema} is
   * defined with all accessors and constraints for the Note class.
   *
   * <p>Issue to track: https://github.com/oracle/graal/issues/3386
   */
  @Bean
  public TableSchema<Note> noteSchema() {
    return StaticTableSchema.builder(Note.class)
        .newItemSupplier(Note::new)
        .addAttribute(
            String.class,
            it -> it.name("id").getter(Note::getId).setter(Note::setId).tags(primaryPartitionKey()))
        .addAttribute(
            String.class, it -> it.name("body").getter(Note::getNoteBody).setter(Note::setNoteBody))
        .addAttribute(
            Long.class,
            it -> it.name("created_at").getter(Note::getCreatedAt).setter(Note::setCreatedAt))
        .build();
  }
}
