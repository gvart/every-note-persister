package com.everynote.perister.entrypoint;


import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.everynote.perister.domain.ProcessNoteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class LambdaFunction implements Consumer<String> {

    private static final Logger log = LoggerFactory.getLogger(LambdaFunction.class);
    private final ProcessNoteRequest processNoteRequest;

    public LambdaFunction(ProcessNoteRequest processNoteRequest) {
        this.processNoteRequest = processNoteRequest;
    }

    @Override
    public void accept(String sqsEvent) {
        try {
            System.out.println(sqsEvent);
        } catch (Exception exception) {
            log.error("Failed to process records", exception);
        }
    }
}
