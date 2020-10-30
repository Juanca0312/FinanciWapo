package com.econowapo.financiapp.controller;

import com.econowapo.financiapp.model.Customer;
import com.econowapo.financiapp.resource.CustomerResource;
import com.econowapo.financiapp.resource.SaveCustomerResource;
import com.econowapo.financiapp.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class CustomerController {
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CustomerService customerService;

    //get all
    @GetMapping("/users/{userId}/customers")
    public Page<CustomerResource> getAllCustomersByUserId(
            @PathVariable(name = "userId") Long userId,
            Pageable pageable) {
        Page<Customer> customerPage = customerService.getAllCustomersByUserId(userId, pageable);
        List<CustomerResource> resources = customerPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    //get all
    @GetMapping("/customers")
    public Page<CustomerResource> getAllCustomers(Pageable pageable ) {
        Page<Customer> customerPage = customerService.getAllCustomers( pageable);
        List<CustomerResource> resources = customerPage.getContent().stream().map(this::convertToResource).collect(Collectors.toList());
        return new PageImpl<>(resources, pageable, resources.size());
    }

    //get by customer id
    @GetMapping("/users/{userId}/customers/{customerId}")
    public CustomerResource getCustomerByIdAndUserId(@PathVariable(name = "userId") Long userId,
                                                     @PathVariable(name = "customerId") Long customerId) {
        return convertToResource(customerService.getCustomerByIdAndUserId(userId, customerId));
    }

    //create customer
    @PostMapping("/users/{userId}/customers")
    public CustomerResource createCustomer(@PathVariable(name = "userId") Long userId,
                                           @Valid @RequestBody SaveCustomerResource resource) {
        return convertToResource(customerService.createCustomer(userId, convertToEntity(resource)));
    }

    //Edit customer
    @PutMapping("/users/{userId}/customers/{customerId}")
    public CustomerResource updateCustomer(@PathVariable(name = "userId") Long userId,
                                           @PathVariable(name = "customerId") Long customerId,
                                           @Valid @RequestBody SaveCustomerResource resource) {
        return convertToResource(customerService.updateCustomer(userId, customerId, convertToEntity(resource)));
    }

    //DeleteCustomer
    @DeleteMapping("/users/{userId}/customers/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable(name = "userId") Long userId,
                                            @PathVariable(name = "customerId") Long customerId) {
        return customerService.deleteCustomer(userId, customerId);
    }


    private Customer convertToEntity(SaveCustomerResource resource) {
        return mapper.map(resource, Customer.class);
    }

    private CustomerResource convertToResource(Customer entity) {
        return mapper.map(entity, CustomerResource.class);
    }

}
