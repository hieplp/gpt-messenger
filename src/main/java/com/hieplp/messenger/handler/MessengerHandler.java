package com.hieplp.messenger.handler;

import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.Message;
import com.restfb.types.send.SendResponse;

public interface MessengerHandler {
    SendResponse sendMessage(IdMessageRecipient recipient, Message message);
}
