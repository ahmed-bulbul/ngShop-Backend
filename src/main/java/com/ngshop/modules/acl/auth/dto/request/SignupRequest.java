package com.ngshop.modules.acl.auth.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignupRequest {

    private String username;
    @Email
    @NotBlank(message = "email not be blank")
    private String email;
    private String userTitle;
    private Boolean groupUser;
    private String groupUsername;
    private Set<String> role;
    @Size(message = "password size must be 4 to 14", min = 4,max = 14)
    @NotBlank
    private String password;

    private String name;
    private String  apartment;
    private String country;
    private String phone;
    private String street;
    private String zip;
    private Boolean isAdmin;


}
