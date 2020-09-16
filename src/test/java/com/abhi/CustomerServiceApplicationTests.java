package com.abhi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.abhi.customer.controller.CustomerController;
import com.abhi.customer.util.CustomerBeanUtility;

@SpringBootTest
@ActiveProfiles("test")
class CustomerServiceApplicationTests {
	
	@Autowired
	private CustomerController customerController;

	@Test
	void contextLoads() {
		assertThat(customerController).isNotNull();
	}
	
	@Test
	void testCustomBeanUtil() {
		assertNull(CustomerBeanUtility.convertToDomain(null));
		assertNull(CustomerBeanUtility.convertToDto(null));
	}

}
