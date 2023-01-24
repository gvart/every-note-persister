package com.everynote.perister.domain;

import com.everynote.perister.domain.gateway.ReadSqsMessageBody;
import com.everynote.perister.domain.gateway.SaveNoteRequest;
import com.everynote.perister.domain.model.PersistNoteRequest;

public class ProcessNoteRequest {

    private final ReadSqsMessageBody readSqsMessageBody;
    private final SaveNoteRequest saveNoteRequest;

    public ProcessNoteRequest(ReadSqsMessageBody readSqsMessageBody, SaveNoteRequest saveNoteRequest) {
        this.readSqsMessageBody = readSqsMessageBody;
        this.saveNoteRequest = saveNoteRequest;
    }

    public void process(String messageBody) {
        var result = readSqsMessageBody.read(messageBody);
        saveNoteRequest.save(result);
    }
}
