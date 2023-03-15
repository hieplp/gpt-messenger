package com.hieplp.messenger.handler.impl;

import com.hieplp.messenger.client.GPTClient;
import com.hieplp.messenger.config.OpenAIConfig;
import com.hieplp.messenger.handler.GPTHandler;
import com.hieplp.messenger.model.Message;
import com.hieplp.messenger.payload.request.ChatGPTRequest;
import com.hieplp.messenger.payload.response.ChatGPTResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
@RequiredArgsConstructor
@Slf4j
public class GPTHandlerImpl implements GPTHandler {
    private final GPTClient gptClient;
    private final OpenAIConfig config;

    @Override
    public ChatGPTResponse ask(String question) {
        log.info("Ask GPT with question: {}", question);

        Message message = Message.builder()
                .role("user")
                .content(question)
                .build();

        ChatGPTRequest chatGPTRequest = ChatGPTRequest.builder()
                .model(config.getGptModel())
                .messages(Collections.singletonList(message))
                .build();

        return gptClient.chat(chatGPTRequest);
    }
}
