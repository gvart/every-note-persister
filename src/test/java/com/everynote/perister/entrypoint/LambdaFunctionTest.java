package com.everynote.perister.entrypoint;

import com.everynote.perister.Bootstrap;
import com.everynote.perister.gateway.dynamodb.model.Note;
import com.everynote.perister.testcontainers.DynamoDbExtension;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.function.adapter.test.aws.AWSCustomRuntime;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.concurrent.Callable;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@ExtendWith(DynamoDbExtension.class)
@SpringBootTest(classes = {Bootstrap.class, AWSCustomRuntime.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.cloud.function.definition=lambdaFunction",
                "_HANDLER=lambdaFunction"
        })
class LambdaFunctionTest {

    @Autowired
    AWSCustomRuntime customRuntime;

    @Autowired
    DynamoDbEnhancedClient dynamoDbClient;

    @Autowired
    TableSchema<Note> noteTableSchema;

    @Value("classpath:event.json")
    Path event;

    private DynamoDbTable<Note> table;


    @BeforeEach
    public void setup() {
        table = dynamoDbClient.table("note", noteTableSchema);
        table.createTable();
    }

    @AfterEach
    public void cleanup() {
        table.deleteTable();
    }

    @Test
    void Test() throws IOException {
        //Given
        var sqsEvent = Files.readString(event);

        //When message send to custom-runtime, and it gets consumed by handler
        customRuntime.exchange(sqsEvent);

        //Then item should be saved in database
        await().atMost(Duration.ofSeconds(5)).until(itemSavedInDatabase());

    }

    private Callable<Boolean> itemSavedInDatabase() {
        return () -> {
            var items = table.scan().items().stream().toList();
            if(items.size() == 1) {
                var savedItem = items.get(0);
                assertThat(savedItem.getId()).isNotNull();
                assertThat(savedItem.getCreatedAt()).isNotNull();
                assertThat(savedItem.getNoteBody()).isEqualTo("message body");

                return true;
            }
            //exact one item wasn't saved
            return false;
        };
    }
}