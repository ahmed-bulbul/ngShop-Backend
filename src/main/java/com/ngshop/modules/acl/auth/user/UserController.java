package com.ngshop.modules.acl.auth.user;


import com.ngshop.modules.acl.auth.dto.request.SignupRequest;
import com.ngshop.modules.acl.auth.role.Role;
import com.ngshop.modules.acl.auth.role.RoleRepository;
import com.ngshop.util.PaginatorService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin("*")
public class UserController {

    public Map<String, String> clientParams;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    //creating user
    @RequestMapping(value = {"/users", "/users/index" }, method = POST)
    public User createUser(@Valid @RequestBody SignupRequest signUpRequest, HttpServletRequest request) throws Exception {

        // Create new user's account
        User user = new User(
                signUpRequest.getEmail(),
                signUpRequest.getUserTitle(),
                signUpRequest.getGroupUser(),
                signUpRequest.getGroupUsername(),
                signUpRequest.getName(),
                signUpRequest.getPhone(),
                signUpRequest.getStreet(),
                signUpRequest.getZip(),
                signUpRequest.getApartment(),
                signUpRequest.getCountry(),
                signUpRequest.getIsAdmin(),
                this.bCryptPasswordEncoder.encode(signUpRequest.getPassword())

        );


        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();


        if (strRoles == null) {
            System.out.println("ROLES IS "+roles);
            Role userRole = roleRepository.getRoleByRoleName("ROLE_USER");
            if (userRole==null){
                throw new NotFoundException("Role not found");
            }
            roles.add(userRole);
        }else{
            strRoles.forEach(role -> {
                if ("super_admin".equals(role)) {
                    Role superAdminRole = roleRepository.getRoleByRoleName("ROLE_SUPER_ADMIN");
                    if (superAdminRole == null) {
                        try {
                            throw new NotFoundException("Role not found");
                        } catch (NotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    roles.add(superAdminRole);
                } else if ("admin".equals(role)) {
                    Role adminRole = roleRepository.getRoleByRoleName("ROLE_ADMIN");
                    if (adminRole == null) {
                        try {
                            throw new NotFoundException("Role not found");
                        } catch (NotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    roles.add(adminRole);
                } else {
                    Role userRole = roleRepository.getRoleByRoleName("ROLE_USER");
                    if (userRole == null) {
                        try {
                            throw new NotFoundException("Role not found");
                        } catch (NotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                    roles.add(userRole);
                }
            });
        }
        //for dev purpose verification and enabled by default true
        //  user.setVerificationCode(null);
        user.setEnabled(true);

        user.setRoles(roles);
        user.setAccountExpired(false);
        user.setPasswordExpired(false);
        user.setAccountLocked(false);
        user.setUsername(signUpRequest.getEmail());
        return this.userService.createUser(user,request);
    }

    @RequestMapping(value = {"/users", "/users/index" }, method = GET)
    public List<User> users(){
        return this.userService.getUsers();
    }


    @GetMapping("/getGroupUser")
    public List<User> getGroupUser(){
        return this.userService.getGroupUser();
    }

    @GetMapping("/get")
    ResponseEntity<Map<String, Object>> getAllPaginatedUsers(HttpServletRequest request, @RequestParam Map<String,String> clientParams){
        this.clientParams = clientParams;
        PaginatorService pSrv = new PaginatorService(request);
        Page<User> page = this.userService.getAllPaginatedUsers(this.clientParams,pSrv.pageNum, pSrv.pageSize, pSrv.sortField, pSrv.sortDir);
        List<User> listData = page.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("objectList", listData);
        response.put("currentPage", page.getNumber());
        response.put("totalPages", page.getTotalPages());
        response.put("totalItems", page.getTotalElements());
        response.put("reverseSortDir", (pSrv.sortDir.equals("asc") ? "desc" : "asc"));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



}
