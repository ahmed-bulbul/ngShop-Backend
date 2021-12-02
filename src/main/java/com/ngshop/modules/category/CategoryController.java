package com.ngshop.modules.category;


import com.ngshop.exception.CustomException;
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

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
@CrossOrigin(origins = "*")
public class CategoryController {

    // g variables
    public Map<String, String> clientParams;

    @Autowired
    private CategoryService service;

    @RequestMapping(value = {"/categories/paginate", "/categories/index/paginate" }, method = GET)
    public ResponseEntity<Map<String, Object>> getAllPaginated(HttpServletRequest request, @RequestParam Map<String,String> clientParams) {

        this.clientParams = clientParams;

        PaginatorService ps = new PaginatorService(request);
        Page<Category> page = this.service.getAllPaginated(this.clientParams, ps.pageNum, ps.pageSize, ps.sortField, ps.sortDir);
        List<Category> listData = page.getContent();

        Map<String, Object> response = new HashMap<>();
        response.put("objectList", listData);
        response.put("currentPage", page.getNumber());
        response.put("totalPages", page.getTotalPages());
        response.put("totalItems", page.getTotalElements());
        response.put("reverseSortDir", (ps.sortDir.equals("asc") ? "desc" : "asc"));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = {"/categories", "/categories/index" }, method = POST)
    public Category category(@RequestBody Category category){
        return this.service.create(category);
    }

    @RequestMapping(value = {"/categories", "/categories/index" }, method = GET)
    public List<Category> categories(){
        return this.service.getCategories();
    }

    @RequestMapping(value = {"/categories/{id}" }, method = GET)
    public Category getCategory(@PathVariable Long id) throws CustomException {
        return this.service.getCategory(id);
    }

    @RequestMapping(value = {"/categories/{id}" }, method = PUT)
    public Category updateCategory(@PathVariable Long id,@RequestBody Category entity) throws CustomException {
        return this.service.updateCategory(id,entity);
    }

    @RequestMapping(value = {"/categories/{id}" }, method = DELETE)
    public ResponseEntity<?> delete (@PathVariable Long id) throws CustomException {
        return this.service.delete(id);
    }



}
