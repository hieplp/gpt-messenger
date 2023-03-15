package com.hieplp.messenger.client;

import com.hieplp.messenger.config.OpenAIConfig;
import com.hieplp.messenger.payload.request.ChatGPTRequest;
import com.hieplp.messenger.payload.response.ChatGPTResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "openai-service",
        url = "${openai.urls.base-url}",
        configuration = OpenAIConfig.class
)
public interface GPTClient {
    @PostMapping(value = "${openai.urls.chat-url}", headers = {"Content-Type=application/json"})
    ChatGPTResponse chat(@RequestBody ChatGPTRequest chatGPTRequest);
}
