package com.everynote.perister.entrypoint;


import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.everynote.perister.domain.ProcessNoteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class LambdaFunction implements Consumer<SQSEvent> {

    private static final Logger log = LoggerFactory.getLogger(LambdaFunction.class);
    private final ProcessNoteRequest processNoteRequest;

    public LambdaFunction(ProcessNoteRequest processNoteRequest) {
        this.processNoteRequest = processNoteRequest;
    }

    @Override
    public void accept(SQSEvent sqsEvent) {
        try {
            sqsEvent.getRecords()
                    .stream()
                    .map(SQSEvent.SQSMessage::getBody)
                    .forEach(processNoteRequest::process);
        } catch (Exception exception) {
            log.error("Failed to process records", exception);
        }
    }
}
