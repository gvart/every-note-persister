package com.everynote.perister.gateway.dynamodb.trasformer;

public interface RequestTransformer<I, O> {

    O transform(I input);
}
