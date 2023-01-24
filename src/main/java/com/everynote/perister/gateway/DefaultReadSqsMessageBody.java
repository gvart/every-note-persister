package com.everynote.perister.gateway;

import com.everynote.perister.domain.gateway.ReadSqsMessageBody;
import com.everynote.perister.domain.model.PersistNoteRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DefaultReadSqsMessageBody implements ReadSqsMessageBody {

    private final Logger log = LoggerFactory.getLogger(DefaultReadSqsMessageBody.class);

    private final ObjectMapper mapper;

    public DefaultReadSqsMessageBody(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public PersistNoteRequest read(String body) {
        try {
            return mapper.readValue(body, PersistNoteRequest.class);
        } catch (JsonProcessingException exception) {
            log.error("Failed to read value: {}", body, exception);
            throw new RuntimeException("Failed to process json.");
        }
    }
}
