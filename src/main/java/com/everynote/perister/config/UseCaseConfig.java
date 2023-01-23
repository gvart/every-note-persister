package com.everynote.perister.config;


import com.everynote.perister.domain.ProcessNoteRequest;
import com.everynote.perister.domain.gateway.ReadSqsMessageBody;
import com.everynote.perister.domain.gateway.SaveNoteRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public ProcessNoteRequest processNoteRequest(
            ReadSqsMessageBody readSqsMessageBody,
            SaveNoteRequest saveNoteRequest
    ) {
        return new ProcessNoteRequest(readSqsMessageBody, saveNoteRequest);
    }
}
