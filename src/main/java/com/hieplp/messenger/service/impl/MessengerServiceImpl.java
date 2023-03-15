package com.hieplp.messenger.service.impl;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.hieplp.messenger.config.FacebookConfig;
import com.hieplp.messenger.exception.BadRequestException;
import com.hieplp.messenger.handler.GPTHandler;
import com.hieplp.messenger.handler.MessengerHandler;
import com.hieplp.messenger.payload.response.ChatGPTResponse;
import com.hieplp.messenger.service.MessengerService;
import com.hieplp.messenger.util.States;
import com.restfb.DefaultJsonMapper;
import com.restfb.JsonMapper;
import com.restfb.types.send.IdMessageRecipient;
import com.restfb.types.send.Message;
import com.restfb.types.send.SendResponse;
import com.restfb.types.webhook.WebhookObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessengerServiceImpl implements MessengerService {

    private final FacebookConfig facebookConfig;
    private final MessengerHandler messengerHandler;
    private final GPTHandler gptHandler;
    private Cache<String, String> cache;

    @PostConstruct
    private void postConstruct() {
        log.info("Messenger service is ready");
        cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterWrite(5, TimeUnit.MINUTES)
                .build();
    }

    @Override
    public String verify(String verifyToken, String mode, String challenge) {
        log.info("Verify token: {}, mode: {}, challenge: {}", verifyToken, mode, challenge);

        if (!facebookConfig.getVerifyToken().equals(verifyToken)) {
            throw new BadRequestException("Invalid verify token");
        }

        return challenge;
    }

    @Override
    public void receiveMessage(String payload, String signature) {
        log.info("Receive message: {} and signature: {}", payload, signature);

        JsonMapper mapper = new DefaultJsonMapper();
        WebhookObject webhookObj = mapper.toJavaObject(payload, WebhookObject.class);

        webhookObj.getEntryList().forEach(entry -> {
            if (States.isNull(entry.getMessaging())) {
                return;
            }

            entry.getMessaging().forEach(messaging -> {
                if (States.isNull(messaging.getMessage()) || States.isNull(messaging.getMessage().getText())) {
                    log.error("Ignore message: {}", messaging.getMessage());
                    return;
                }

                if (entry.getId().equals(messaging.getSender().getId())) {
                    log.error("Ignore message from page: {}", entry.getId());
                    return;
                }

                if (cache.getIfPresent(messaging.getMessage().getMid()) != null) {
                    log.error("Ignore duplicate message: {}", messaging.getMessage().getMid());
                    return;
                }
                cache.put(messaging.getMessage().getMid(), messaging.getMessage().getMid());

                final IdMessageRecipient recipient = new IdMessageRecipient(messaging.getSender().getId());

                // Send typing on
                messengerHandler.sendMessage(recipient, new Message("GPT is thinking, please wait a moment..."));

                // Call ChatGPT API here
                ChatGPTResponse gptResponse = gptHandler.ask(messaging.getMessage().getText());
                log.debug("GPT response: {}", gptResponse);

                // Send ChatGPT choices
                gptResponse.getChoices().forEach(choice -> {
                    final Message message = new Message(choice.getMessage().getContent());
                    SendResponse response = messengerHandler.sendMessage(recipient, message);
                    log.debug("Send response: {}", response);
                });
            });
        });
    }
}
