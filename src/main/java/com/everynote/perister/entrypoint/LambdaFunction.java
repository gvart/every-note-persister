package com.everynote.perister.entrypoint;

import com.everynote.perister.domain.ProcessNoteRequest;
import com.everynote.perister.entrypoint.model.SqsEvent;
import com.everynote.perister.entrypoint.model.SqsMessage;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * An entrypoint to the application, which will be used as a handler during the runtime in AWS
 * Lambda environment.
 *
 * <p>Note: Since there's a single bean which implements a function interface (Consumer, Function,
 * Supplier) this function will be picked up automatically, otherwise we need to specify handler
 * function via `spring.cloud.function.definition`
 */
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

    sqsEvent.records().stream().map(SqsMessage::body).forEach(processNoteRequest::process);
  }
}
