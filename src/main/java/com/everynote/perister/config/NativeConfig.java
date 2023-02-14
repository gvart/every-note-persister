package com.everynote.perister.config;

import com.everynote.perister.domain.model.PersistNoteRequest;
import com.everynote.perister.entrypoint.model.SqsEvent;
import com.everynote.perister.entrypoint.model.SqsMessage;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;


@Configuration
@RegisterReflectionForBinding({
        PersistNoteRequest.class,
        SqsMessage.class,
        SqsEvent.class
})
public class NativeConfig {
}
