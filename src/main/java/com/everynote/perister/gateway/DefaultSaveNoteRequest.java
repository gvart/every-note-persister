package com.everynote.perister.gateway;

import com.everynote.perister.domain.model.PersistNoteRequest;
import com.everynote.perister.domain.gateway.SaveNoteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultSaveNoteRequest implements SaveNoteRequest {

    private final Logger log = LoggerFactory.getLogger(DefaultSaveNoteRequest.class);

    @Override
    public void save(PersistNoteRequest request) {
        log.info("Persisting {}", request);
    }
}
