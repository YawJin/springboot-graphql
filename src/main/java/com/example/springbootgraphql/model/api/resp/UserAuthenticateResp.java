package com.example.springbootgraphql.model.api.resp;

import lombok.Data;

@Data
public class UserAuthenticateResp extends BaseResp {
    private String token;
}
