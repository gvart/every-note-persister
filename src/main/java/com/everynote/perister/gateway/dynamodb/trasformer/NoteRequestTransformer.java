package com.everynote.perister.gateway.dynamodb.trasformer;

import com.everynote.perister.domain.model.PersistNoteRequest;
import com.everynote.perister.gateway.dynamodb.model.Note;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Component
public class NoteRequestTransformer implements RequestTransformer<PersistNoteRequest, Note> {

    @Override
    public Note transform(PersistNoteRequest input) {
        var note = new Note();

        note.setNoteBody(input.body());

        Optional.ofNullable(note.getId()).ifPresentOrElse(
                note::setId,
                () -> {
                    note.setId(generateId());
                    note.setCreatedAt(getCurrentTime());
                });

        return note;
    }

    private long getCurrentTime() {
        return Instant.now(Clock.systemUTC()).getEpochSecond();
    }

    private String generateId() {
        return UUID.randomUUID().toString();
    }
}
