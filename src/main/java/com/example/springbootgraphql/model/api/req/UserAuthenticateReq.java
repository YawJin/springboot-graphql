package com.example.springbootgraphql.model.api.req;

import lombok.Data;

@Data
public class UserAuthenticateReq {
    private String email;
    private String password;
}
