package com.sanjeev.utils;

import com.sanjeev.models.Role;
import com.sanjeev.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Autowired
    public DataLoader(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadData();
    }

    private void loadData() {
        Role adminRole = new Role("ADMIN", "Manages everything on the system");
        Role customerRole = new Role("CUSTOMER", "Ability to purchase stuff the system");
        Role editorRole = new Role("EDITOR", "Manages categories, brands, products, articles and menu on the system");
        Role shipperRole = new Role("SHIPPER", "View products, view orderws and update order status on the system");
        Role salespersonRole = new Role("SALESPERSON", "Managers product price, customers, shipping, orders and sales report on the system");
        Role assistantRole = new Role("ASSISTANT", "Manages questions and reviews on the system");

        roleRepository.saveAll(List.of(adminRole,customerRole,editorRole,shipperRole,assistantRole,salespersonRole));

        System.out.println("Data has been loaded!");
    }
}