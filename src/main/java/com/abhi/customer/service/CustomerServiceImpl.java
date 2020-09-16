package com.abhi.customer.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhi.customer.domain.Customer;
import com.abhi.customer.dto.CustomerDto;
import com.abhi.customer.repo.CustomerRepository;
import com.abhi.customer.util.CustomerBeanUtility;
import com.abhi.customer.util.InvalidInputException;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerRepository customerRepo;

	@Override
	public CustomerDto saveCustomer(CustomerDto customerDto) throws InvalidInputException {
		if(null == customerDto) {
			throw new InvalidInputException("Customer Details are Required");
		}
		customerDto.setStatus(1L);
		customerDto.setCreatedDate(LocalDateTime.now());
		List<Customer> customerLst = customerRepo.findByUsername(customerDto.getUsername());
		if(null != customerLst && !customerLst.isEmpty()) {
			throw new InvalidInputException("Username already exists");
		}
		Customer customer = CustomerBeanUtility.convertToDomain(customerDto);
		customer =  customerRepo.save(customer);
		return CustomerBeanUtility.convertToDto(customer);
	}

	@Override
	public CustomerDto getCustomerDetailsByUsername(String username) {
		List<Customer> customerLst = customerRepo.findByUsername(username);
		if (null != customerLst && !customerLst.isEmpty()) {
			Customer customer = customerLst.get(0);
			return CustomerBeanUtility.convertToDto(customer);
		}
		return null;
	}

}
