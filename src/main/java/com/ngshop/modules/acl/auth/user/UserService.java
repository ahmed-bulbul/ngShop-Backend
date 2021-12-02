package com.ngshop.modules.acl.auth.user;

import com.ngshop.exception.CustomException;
import com.ngshop.modules.acl.auth.role.Role;
import com.ngshop.modules.acl.auth.role.RoleRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;
    @Autowired
    private RoleRepository roleRepository;
    public User getUserByUsername(String username) {
        return this.repository.findByUsername(username);
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public User createUser(User user, HttpServletRequest request)
            throws UnsupportedEncodingException, CustomException {

        User local = this.repository.findByUsername(user.getUsername());
        if(local!=null){
            System.out.println("User is already exists");
            throw new CustomException("User already exists");
        }else{
            String siteUrl =getSiteURL(request)+"/user";
            String randomCode = RandomString.make(64);
            local = this.repository.save(user);
        }
        return local;
    }



    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }

    public List<User> getGroupUser() {
        return this.repository.findByGroupUserTrue();
    }

    public Page<User> getAllPaginatedUsers(Map<String, String> clientParams, int pageNum, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        Page<User> users = this.repository.findAll((Specification<User>) (root, cq, cb) -> {
            //cq=query and cb=builder

//            Join<User, HrCrEmp> joinHrCrEmp= root.join("hrCrEmp", JoinType.LEFT);
            Predicate p = cb.conjunction();

            if(!clientParams.isEmpty()){

                if(clientParams.containsKey("username")){
                    if (StringUtils.hasLength(clientParams.get("username"))) {
                        p = cb.and(p, cb.equal(root.get("username"), clientParams.get("username")));
                    }
                }
                return p;
            }
            return null;
        }, pageable);

        return users;
    }

    public List<User> getUsers() {
        return this.repository.findAll();
    }
}
