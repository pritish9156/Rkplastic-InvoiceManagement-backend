package service;

import java.util.List;

import DAO.CustomerDAO;
import dto.CustomerRequest;
import model.Customer;

public class CustomerServiceImpl
        implements CustomerService {

    private final CustomerDAO customerDAO =
            new CustomerDAO();

    @Override
    public Customer createCustomer(
            CustomerRequest request) {

        Customer customer =
                new Customer();

        customer.setName(
                request.getName());

        customer.setAddress(
                request.getAddress());

        customer.setGstin(
                request.getGstin());

        customer.setPhone(
                request.getPhone());

        return customerDAO.save(
                customer);
    }

    @Override
    public Customer getCustomer(
            Integer id) {

        return customerDAO.findById(id);
    }

    @Override
    public List<Customer>
    searchCustomers(
            String keyword) {

        return customerDAO.search(
                keyword);
    }
    
    @Override
    public List<Customer> getAllCustomers() {

        return customerDAO.findAll();

    }

	@Override
	public Customer updateCustomer(Integer id, CustomerRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteCustomer(Integer id) {
		
		customerDAO.delete(id);
		
	}
}