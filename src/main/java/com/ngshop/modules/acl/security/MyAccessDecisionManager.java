package com.ngshop.modules.acl.security;

import com.ngshop.modules.acl.authCust.SystemResAuthCheckService;
import com.ngshop.util.UserUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class MyAccessDecisionManager implements AccessDecisionManager {
    @Autowired
    private SystemResAuthCheckService authCheckService;

    @SneakyThrows
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException, InsufficientAuthenticationException {

        System.out.println("I am here... DecisionMngr............");
        System.out.println(configAttributes);
        System.out.println(configAttributes);

        //for url based permission
        String reqUrl = String.valueOf(configAttributes.toArray()[0]);
        String username = UserUtil.getLoginUser();

        String replacedReqUrl = reqUrl.replaceAll("[\\[\\]\"]", "");
      //  ArrayList<String> items = new ArrayList(Arrays.asList(replacedReqUrl.split("\\s*,\\s*")));

      /*  String[] reqVal = new String[100];
        int count = 0;
        for(String str : items)
        {
            reqVal[count]=str;
            count++;
        }

        String curReqUrl = reqVal[0];
        String curHttpReq=reqVal[1];;*/

        //System.out.println(" GETTING REQ AND URI + "+curHttpReq +" "+curReqUrl);


        if (replacedReqUrl.equals("EMPTY")){
            throw new AccessDeniedException("not allow");
        }

        if(authCheckService.hasAuthorization(username,replacedReqUrl)){
            return;
        }else{
            throw new AccessDeniedException("not allow");
        }

    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
