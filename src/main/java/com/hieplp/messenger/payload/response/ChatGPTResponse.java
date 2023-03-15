package com.hieplp.messenger.payload.response;

import com.hieplp.messenger.model.Choice;
import com.hieplp.messenger.model.Usage;
import lombok.Data;

import java.util.List;

@Data
public class ChatGPTResponse {
    private String id;
    private String object;
    private String model;
    private List<Choice> choices;
    private Usage usage;
}
