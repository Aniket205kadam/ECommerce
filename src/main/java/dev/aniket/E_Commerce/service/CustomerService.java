package dev.aniket.E_Commerce.service;

import dev.aniket.E_Commerce.dto.CustomerDto;
import dev.aniket.E_Commerce.model.Address;
import dev.aniket.E_Commerce.model.Customer;
import dev.aniket.E_Commerce.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Customer getCustomerById(Integer id) throws ResponseStatusException{
        Optional<Customer> customer = customerRepository.findById(id);

        if (customer.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not found the customer by the ID");
        }
        return customer.get();
    }

    public void addCustomer(Customer customer) {
        Customer afterCreateCus = customerRepository.save(customer);

        if (afterCreateCus == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR , "Customer not created!");
        }
        return;
    }

    public void addAddressToExistingCus(Address address, Integer cusId) {
        //if Customer is not exist than return the error
        Customer customer = getCustomerById(cusId);

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setUsername(customer.getUsername());
        customerDto.setEmail(customer.getEmail());

        address.setCustomer(customerDto);
// working this method move to the address service

    }
}
