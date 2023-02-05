package com.everynote.perister.config;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.everynote.perister.domain.model.PersistNoteRequest;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;


@Configuration
@RegisterReflectionForBinding({
        PersistNoteRequest.class,
        SQSEvent.class,
        SQSEvent.SQSMessage.class,
        SQSEvent.MessageAttribute.class})
public class NativeConfig {
}
