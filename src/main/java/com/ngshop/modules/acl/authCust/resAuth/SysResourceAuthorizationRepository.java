package com.ngshop.modules.acl.authCust.resAuth;


import com.ngshop.modules.acl.authCust.resDef.SysResourceDefinition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysResourceAuthorizationRepository extends JpaRepository<SysResourceAuthorization,Long>, JpaSpecificationExecutor<SysResourceAuthorization> {

    List<SysResourceAuthorization> findBySystemResource(SysResourceDefinition resourceDefinitionInst);
}
