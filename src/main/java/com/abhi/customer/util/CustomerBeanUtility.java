package com.abhi.customer.util;

import org.springframework.beans.BeanUtils;

import com.abhi.customer.domain.Customer;
import com.abhi.customer.dto.CustomerDto;

public class CustomerBeanUtility {
	
	private CustomerBeanUtility() {
	}

	public static Customer convertToDomain(CustomerDto customerDto) {
		if (null == customerDto) {
			return null;
		}
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerDto, customer);
		return customer;
	}

	public static CustomerDto convertToDto(Customer customer) {
		if (null == customer) {
			return null;
		}
		CustomerDto customerDto = new CustomerDto();
		BeanUtils.copyProperties(customer, customerDto);
		return customerDto;
	}

}
