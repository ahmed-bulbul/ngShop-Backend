package com.ngshop.modules.acl.auth.dto.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtRequest {

    @NotBlank(message = "email must not be blank")
    String email;

    @Size(message = "password size must be 4 to 20", min = 4,max = 20)
    @NotBlank
    String password;
}
