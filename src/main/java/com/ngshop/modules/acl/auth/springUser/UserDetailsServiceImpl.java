package com.ngshop.modules.acl.auth.springUser;


import com.ngshop.modules.acl.auth.user.User;
import com.ngshop.modules.acl.auth.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user;
        user = this.userRepository.findByUsername(username);

        if(user==null){
            user=this.userRepository.findByEmail(username);
        }

        if(user==null){
            throw new UsernameNotFoundException("No user found !!");
        }
        return new UserDetailsImpl(user);
    }
}