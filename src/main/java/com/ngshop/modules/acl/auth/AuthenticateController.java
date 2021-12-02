package com.ngshop.modules.acl.auth;


import com.ngshop.config.JwtUtils;
import com.ngshop.exception.InvalidCredentialsException;
import com.ngshop.modules.acl.auth.dto.jwt.JwtRequest;
import com.ngshop.modules.acl.auth.dto.jwt.JwtResponse;
import com.ngshop.modules.acl.auth.role.Role;
import com.ngshop.modules.acl.auth.springUser.UserDetailsServiceImpl;
import com.ngshop.modules.acl.auth.user.User;
import com.ngshop.modules.acl.auth.user.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class AuthenticateController {

    @Autowired
    private AuthenticationManager authenticationManager;


    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @Autowired
    private JwtUtils jwtUtils;



    @Autowired
    private UserService userService;

    @Autowired
    HttpSession session; //autowiring session

    //generate token
    @PostMapping("/users/login")
    public ResponseEntity<?> generateToken(@Valid @RequestBody JwtRequest jwtRequest) throws Exception {

        try {
            authenticate(jwtRequest.getEmail(),jwtRequest.getPassword());

        }catch (NotFoundException e){
            throw new Exception("User not found");
        }

        //authenticate
        UserDetails userDetails=this.userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String username = userDetails.getUsername();
        String token = this.jwtUtils.generateToken(userDetails);
        boolean validate = this.jwtUtils.validateToken(token,userDetails);
        System.out.println("Token is validate "+validate);
        return ResponseEntity.ok(new JwtResponse(username,token));
    }

    private void authenticate(String username,String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));

        }catch (DisabledException e){
            throw new Exception("User Disabled"+ e.getMessage());
        }catch (BadCredentialsException e){
            throw new InvalidCredentialsException("Invalid Credentials");
        }
    }


    //return the details of current user
    @GetMapping("/currentUser")
    public User getCurrentUser(Principal principal){
        String name;
        String loginCode= (String) session.getAttribute("loginCode");
        if (loginCode!=null)
            name=loginCode;
        else
            name=principal.getName();
        return ((User)this.userDetailsService.loadUserByUsername(name));
    }


    @GetMapping(value = "/roles")
    public List<Role> getRoles(){
        return userService.getRoles();
    }

}

