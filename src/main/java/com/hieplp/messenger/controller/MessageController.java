package com.hieplp.messenger.controller;

import com.hieplp.messenger.service.MessengerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private static final String CALLBACK_URL = "callback";

    private final MessengerService messengerService;

    @GetMapping(CALLBACK_URL)
    public ResponseEntity<String> verify(@RequestParam("hub.mode") final String hubMode,
                                         @RequestParam("hub.challenge") final String hubChallenge,
                                         @RequestParam("hub.verify_token") final String hubVerifyToken) {
        try {
            log.info("Verify token with hubMode: {}, hubChallenge: {}, hubVerifyToken: {}", hubMode, hubChallenge, hubVerifyToken);
            String challenge = messengerService.verify(hubVerifyToken, hubMode, hubChallenge);
            return ResponseEntity.ok(challenge);
        } catch (Exception e) {
            log.error("Error when verify token: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping(CALLBACK_URL)
    public ResponseEntity<String> receiveMessage(@RequestBody final String payload,
                                                 @RequestHeader("X-Hub-Signature") final String signature) {
        log.info("payload: {}, signature: {}", payload, signature);
        messengerService.receiveMessage(payload, signature);
        return ResponseEntity.ok("EVENT_RECEIVED");
    }
}
