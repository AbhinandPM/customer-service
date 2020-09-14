package com.abhi.customer.service;

import com.abhi.customer.dto.CustomerDto;
import com.abhi.customer.util.InvalidInputException;

public interface CustomerService {

	CustomerDto saveCustomer(CustomerDto customer) throws InvalidInputException;

	CustomerDto getCustomerDetailsByUsername(String username);
}
