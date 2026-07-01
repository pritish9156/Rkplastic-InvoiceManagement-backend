package service;

import java.util.List;

import dto.CustomerRequest;
import model.Customer;

public interface CustomerService {

    Customer createCustomer(CustomerRequest request);

    Customer getCustomer(Integer id);

    List<Customer> searchCustomers(String keyword);
    
    List<Customer> getAllCustomers();
    
    Customer updateCustomer(
            Integer id,
            CustomerRequest request);

    void deleteCustomer(
            Integer id);
}