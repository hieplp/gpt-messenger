package com.hieplp.messenger.handler;

import com.hieplp.messenger.payload.response.ChatGPTResponse;

public interface GPTHandler {
    ChatGPTResponse ask(String question);
}
