package com.ngshop.modules.acl.authCust.resDef;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.Map;

@Service
public class SysResourceDefinitionService {
    @Autowired
    private SysResourceDefinitionRepository repository;


    public SysResourceDefinition create(SysResourceDefinition sysResourceDefinition) {
        return this.repository.save(sysResourceDefinition);
    }


    public Page<SysResourceDefinition> getAllPaginatedSysDef(Map<String, String> clientParams, int pageNum, int pageSize, String sortField, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        return this.repository.findAll((Specification<SysResourceDefinition>) (root, cq, cb) -> {
            Predicate p = cb.conjunction();
            if(!clientParams.isEmpty()){
                if(clientParams.containsKey("id")){
                    if (StringUtils.hasLength(clientParams.get("id"))) {
                        p = cb.and(p, cb.equal(root.get("id"), clientParams.get("id")));
                    }
                }
                if(clientParams.containsKey("entityName")){
                    if (StringUtils.hasLength(clientParams.get("entityName"))) {
                        p = cb.and(p, cb.like(root.get("entityName"), "%"+clientParams.get("entityName")+"%"));
                    }
                }
                return p;
            }
            return null;
        }, pageable);
    }
}
