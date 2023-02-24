package com.everynote.perister.config;

import com.everynote.perister.domain.model.PersistNoteRequest;
import com.everynote.perister.entrypoint.model.SqsEvent;
import com.everynote.perister.entrypoint.model.SqsMessage;
import com.everynote.perister.gateway.dynamodb.model.Note;
import org.springframework.aot.hint.annotation.RegisterReflectionForBinding;
import org.springframework.context.annotation.Configuration;

/**
 * Reflection hints which lists all classes which will be indirectly accessed via reflection (i.e.
 * ObjectMapper#readValue)
 */
@Configuration
@RegisterReflectionForBinding({
  PersistNoteRequest.class,
  SqsMessage.class,
  SqsEvent.class,
  Note.class
})
public class NativeConfig {}
