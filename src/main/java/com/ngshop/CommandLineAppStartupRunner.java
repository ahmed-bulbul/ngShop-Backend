package com.ngshop;

import com.ngshop.modules.acl.auth.role.Role;
import com.ngshop.modules.acl.auth.role.RoleRepository;
import com.ngshop.modules.product.Product;
import com.ngshop.modules.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAppStartupRunner  implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ProductRepository productRepository;

    public void createProduct(){
        Product product = new Product();
        product.setBrand("Product 1");
        product.setDescription("Product 1 description");
        product.setName("Product 1");
        product.setPrice(100.00);
        this.productRepository.save(product);
        System.out.println("Product created Successfully");

    }

    /**Create Basic roles*/
    public void createAppBasicRoles(){

        Role chkRoleExists = this.roleRepository.getRoleByRoleName("ROLE_SUPER_ADMIN");

        if(chkRoleExists==null){
            Role roleInst1 = new Role("ROLE_SUPER_ADMIN","");
            this.roleRepository.save(roleInst1);
        }

        chkRoleExists = this.roleRepository.getRoleByRoleName("ROLE_ADMIN");
        if(chkRoleExists==null){
            Role roleInst2 = new Role("ROLE_ADMIN","");
            this.roleRepository.save(roleInst2);
        }

        chkRoleExists = this.roleRepository.getRoleByRoleName("ROLE_USER");
        if(chkRoleExists==null){
            Role roleInst3= new Role("ROLE_USER","");
            this.roleRepository.save(roleInst3);
        }
    }
    @Override
    public void run(String... args) throws Exception {
        System.out.println("------>CommandLineAppStartupRunner<----------");
        this.createAppBasicRoles();
       // this.createProduct();
    }
}
