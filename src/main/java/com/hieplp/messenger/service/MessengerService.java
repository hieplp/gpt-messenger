package com.hieplp.messenger.service;

public interface MessengerService {
    String verify(String verifyToken, String mode, String challenge);

    void receiveMessage(String payload, String signature);
}
