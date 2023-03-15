package com.hieplp.messenger.payload.request;

import com.hieplp.messenger.model.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatGPTRequest implements Serializable {
    private String model;
    private String prompt;
    private List<Message> messages;
}