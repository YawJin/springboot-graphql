package com.example.springbootgraphql.model.api.resp;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Data
public class BaseResp {
    private boolean success;
    @Setter(AccessLevel.NONE)
    private List<String> messages = new ArrayList<>();

    public void addMessage(String message) {
        this.messages.add(message);
    }
}
