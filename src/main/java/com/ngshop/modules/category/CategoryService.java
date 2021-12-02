package com.ngshop.modules.category;

import com.ngshop.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Map;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Page<Category> getAllPaginated(Map<String, String> clientParams, int pageNum, int pageSize, String sortField, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNum - 1, pageSize, sort);

        Page<Category> categories = this.repository.findAll((Specification<Category>) (root, cq, cb) -> {
            //cq=query and cb=builder

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

        return categories;

    }

    public Category create(Category category) {
        return this.repository.save(category);
    }

    public List<Category> getCategories() {
        return this.repository.findAll();
    }

    public Category getCategory(Long id) throws CustomException {
        Category catInst = repository.findById(id).orElseThrow(()
                -> new CustomException("Category not found for this id :: " + id));
        return this.repository.findById(id).get();
    }

    public Category updateCategory(Long id,Category entity) throws CustomException {
        Category catInst = repository.findById(id).orElseThrow(()
                -> new CustomException("Category not found for this id :: " + id));
        catInst.setName(entity.getName());
        catInst.setIcon(entity.getIcon());
        catInst.setColor(entity.getColor());
        return this.repository.save(catInst);
    }

    public ResponseEntity<?> delete(Long id) throws CustomException {
        Category catInst = repository.findById(id).orElseThrow(()
                -> new CustomException("Category not found for this id :: " + id));
        repository.delete(catInst);
        return ResponseEntity.ok(new CustomException("Successfully Deleted Data"));
    }
}
