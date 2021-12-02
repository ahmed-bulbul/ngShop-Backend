package com.ngshop.modules.acl.authCust.resDef;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SysResourceDefinitionRepository extends JpaRepository<SysResourceDefinition,Long>, JpaSpecificationExecutor<SysResourceDefinition> {
    SysResourceDefinition findByBackendUrl(String url);
}
