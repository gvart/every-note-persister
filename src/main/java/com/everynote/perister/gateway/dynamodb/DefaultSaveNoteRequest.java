package com.everynote.perister.gateway.dynamodb;

import com.everynote.perister.domain.gateway.SaveNoteRequest;
import com.everynote.perister.domain.model.PersistNoteRequest;
import com.everynote.perister.gateway.dynamodb.model.Note;
import com.everynote.perister.gateway.dynamodb.trasformer.RequestTransformer;
import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultSaveNoteRequest implements SaveNoteRequest {

  private final Logger log = LoggerFactory.getLogger(DefaultSaveNoteRequest.class);

  private final DynamoDbTemplate template;
  private final RequestTransformer<PersistNoteRequest, Note> transformer;

  public DefaultSaveNoteRequest(
      DynamoDbTemplate template, RequestTransformer<PersistNoteRequest, Note> transformer) {
    this.template = template;
    this.transformer = transformer;
  }

  @Override
  public void save(PersistNoteRequest request) {
    log.info("Persisting {}", request);

    var dbEntry = transformer.transform(request);
    template.save(dbEntry);
  }
}
