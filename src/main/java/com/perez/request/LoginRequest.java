package com.perez.request;

import lombok.Data;

@Data
public class LoginRequest {   //Request
    private String email;
    private String password;

}
