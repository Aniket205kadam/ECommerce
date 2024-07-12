package dev.aniket.E_Commerce.controller;

import dev.aniket.E_Commerce.model.Address;
import dev.aniket.E_Commerce.model.Customer;
import dev.aniket.E_Commerce.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCusById(@PathVariable Integer id) {
        return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createCustomer(@Valid @RequestBody Customer customer) {
        customerService.addCustomer(customer);
        return new ResponseEntity<>("Customer created Successfully!", HttpStatus.OK);
    }

    @PostMapping("/{id}/address")
    public ResponseEntity<String> addedAddressCus(@Valid @RequestBody Address address,
                                                  @PathVariable Integer id) {
        customerService.addAddressToExistingCus(address, id);

        return null;
    }
}
