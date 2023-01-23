package com.everynote.perister.entrypoint;


import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.everynote.perister.domain.ProcessNoteRequest;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class LambdaFunction implements Consumer<SQSEvent> {

    private final ProcessNoteRequest processNoteRequest;

    public LambdaFunction(ProcessNoteRequest processNoteRequest) {
        this.processNoteRequest = processNoteRequest;
    }

    @Override
    public void accept(SQSEvent sqsEvent) {
        sqsEvent.getRecords()
                .stream()
                .map(SQSEvent.SQSMessage::getBody)
                .forEach(processNoteRequest::process);
    }
}
