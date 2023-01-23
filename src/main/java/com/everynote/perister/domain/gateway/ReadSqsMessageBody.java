package com.everynote.perister.domain.gateway;

public interface ReadSqsMessageBody {
    <T> T read(String body);
}
