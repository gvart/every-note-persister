package com.everynote.perister.domain.gateway;

import com.everynote.perister.domain.model.PersistNoteRequest;

public interface SaveNoteRequest {
    void save(PersistNoteRequest request);
}
