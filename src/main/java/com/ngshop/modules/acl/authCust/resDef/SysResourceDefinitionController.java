package com.ngshop.modules.acl.authCust.resDef;


import com.ngshop.util.PaginatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sysResDef")
@CrossOrigin("*")
public class SysResourceDefinitionController {


    @Autowired
    private SysResourceDefinitionService service;
    public Map<String, String> clientParams;


    /** SysResDef create api*/
    @PostMapping(value = "/create")
    public SysResourceDefinition create(@RequestBody SysResourceDefinition sysResourceDefinition){
        return this.service.create(sysResourceDefinition);
    }


    /**Get All Paginated data api */
    @GetMapping(value = "/get")
    ResponseEntity<Map<String, Object>> getAllPaginatedSysDef(HttpServletRequest request, @RequestParam Map<String,String> clientParams){
        this.clientParams = clientParams;
        PaginatorService pSrv = new PaginatorService(request);
        Page<SysResourceDefinition> page = this.service.getAllPaginatedSysDef(this.clientParams,pSrv.pageNum, pSrv.pageSize, pSrv.sortField, pSrv.sortDir);
        List<SysResourceDefinition> listData = page.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("objectList", listData);
        response.put("currentPage", page.getNumber());
        response.put("totalPages", page.getTotalPages());
        response.put("totalItems", page.getTotalElements());
        response.put("reverseSortDir", (pSrv.sortDir.equals("asc") ? "desc" : "asc"));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
