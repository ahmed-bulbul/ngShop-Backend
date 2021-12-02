package com.ngshop.modules.acl.security;

import com.ngshop.modules.acl.authCust.resDef.SysResourceDefinition;
import com.ngshop.modules.acl.authCust.resDef.SysResourceDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

public class MyFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private SysResourceDefinitionRepository resourceDefinitionRepository;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {

        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequest().getRequestURI();
        String httpReq = fi.getHttpRequest().getMethod();
        String lastIndexUriPath = url.substring(url.lastIndexOf('/') + 1);
        

       /* if (!Pattern.matches("[a-zA-Z]+", lastIndexUriPath) && Pattern.matches("[0-9]+",
                lastIndexUriPath) && (httpReq.equals("GET") || httpReq.equals("DELETE")) ) {
            url = url.replaceAll("[0-9]", "");
        }*/
        System.out.println("==========>Requesting URL ======>"+url+" ( || AND  || Request Method ===>"+httpReq+")");
        // TODO ignore url, please put it here for filtering and release
        if ("/api/v1/users/login".equals(url) || "/api/v1/currentUser".equals(url) || "/api/v1/user/register".equals(url)){
            return null;
        }

        /** No security */
        return null;

       /* ArrayList<String> attributes;
        attributes = this.getAttributesByReqURI(url,httpReq); //Here Goes Code
        System.out.println("I am dynamic url permission............chk-1");
        return SecurityConfig.createList(String.valueOf(attributes));*/
    }

    private ArrayList<String> getAttributesByReqURI(String url,String httpReq) {
        ArrayList<String> attributes = new ArrayList<>();
        System.out.println("=====Go For Checking Url on database==> "+url);
        SysResourceDefinition entityOptional = this.resourceDefinitionRepository.findByBackendUrl(url);
        if (entityOptional != null) {
            System.out.println("=======Checking Successful! Found url on database ==========");
            String configAttribute = entityOptional.getBackendUrl();
            attributes.add(String.valueOf(configAttribute));
        } else {
            System.out.println("=======Checking Successful! Not Found url on database. Now adding empty ==========");
            attributes.add("EMPTY");
        }
        attributes.add(httpReq);
        return attributes;
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }
}
