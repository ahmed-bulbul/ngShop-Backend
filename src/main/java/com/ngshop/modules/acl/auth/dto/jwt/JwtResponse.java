package com.ngshop.modules.acl.auth.dto.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtResponse {
    String user;
    String token;

    public JwtResponse(String user, String token) {
        this.user = user;
        this.token = token;
    }
}
