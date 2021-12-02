package com.ngshop.modules.acl.authCust;

import com.ngshop.modules.acl.auth.user.User;
import com.ngshop.modules.acl.auth.user.UserService;
import com.ngshop.modules.acl.authCust.resAuth.SysResourceAuthorizationRepository;
import com.ngshop.modules.acl.authCust.resDef.SysResourceDefinition;
import com.ngshop.modules.acl.authCust.resDef.SysResourceDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class SystemResAuthCheckService {
    Logger log = Logger.getLogger(SystemResAuthCheckService.class.getName());

    @Autowired
    private SysResourceAuthorizationRepository authorizationRepository;
    @Autowired
    private SysResourceDefinitionRepository definitionRepository;
    @Autowired
    private UserService userService;

    public boolean hasAuthorization(String username, String curReqUrl) {

        log.info("@hasAuthorized() username is "+username + " And Url is "+curReqUrl);

        SysResourceDefinition definitionInst = definitionRepository.findByBackendUrl(curReqUrl);
        definitionInst.getEntityName();
        User user = this.userService.getUserByUsername(username);

        return true;
    }
}
