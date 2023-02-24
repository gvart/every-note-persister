package com.everynote.perister.entrypoint.model;

import java.util.List;

public record SqsEvent(List<SqsMessage> records) {}
