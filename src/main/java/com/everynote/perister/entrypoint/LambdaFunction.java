package com.everynote.perister.entrypoint;


import com.everynote.perister.domain.ProcessNoteRequest;
import com.everynote.perister.entrypoint.model.SqsEvent;
import com.everynote.perister.entrypoint.model.SqsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class LambdaFunction implements Consumer<SqsEvent> {

    private static final Logger log = LoggerFactory.getLogger(LambdaFunction.class);
    private final ProcessNoteRequest processNoteRequest;

    public LambdaFunction(ProcessNoteRequest processNoteRequest) {
        this.processNoteRequest = processNoteRequest;
    }

    @Override
    public void accept(SqsEvent sqsEvent) {
        log.info("Received event: {}", sqsEvent);

        sqsEvent.records()
                .stream()
                .map(SqsMessage::body)
                .forEach(processNoteRequest::process);
    }
}
