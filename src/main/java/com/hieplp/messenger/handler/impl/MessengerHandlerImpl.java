package com.hieplp.messenger.handler.impl;

import com.hieplp.messenger.handler.MessengerHandler;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.Message;
import com.restfb.types.send.SendResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class MessengerHandlerImpl implements MessengerHandler {

    private final FacebookClient facebookClient;

    @Override
    public SendResponse sendMessage(IdMessageRecipient recipient, Message message) {
        log.info("Send message to recipient: {} with message: {}", recipient, message);
        return facebookClient.publish("me/messages", SendResponse.class,
                Parameter.with("recipient", recipient),
                Parameter.with("message", message));
    }
}