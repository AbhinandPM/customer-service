package com.abhi.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abhi.customer.dto.CustomerDto;
import com.abhi.customer.service.CustomerService;
import com.abhi.customer.util.InvalidInputException;

@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/customer")
	public CustomerDto registerCustomer(@RequestBody CustomerDto customer) throws InvalidInputException {
		return customerService.saveCustomer(customer);
	}

	@GetMapping("/customer/username/{username}")
	public CustomerDto getCustomerByUsername(@PathVariable String username) {
		return customerService.getCustomerDetailsByUsername(username);
	}

}
