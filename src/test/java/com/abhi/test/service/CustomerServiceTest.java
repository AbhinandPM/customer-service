package com.abhi.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.abhi.customer.domain.Customer;
import com.abhi.customer.dto.CustomerDto;
import com.abhi.customer.repo.CustomerRepository;
import com.abhi.customer.service.CustomerServiceImpl;
import com.abhi.customer.util.CustomerBeanUtility;
import com.abhi.customer.util.InvalidInputException;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CustomerServiceTest {

	@Mock
	private CustomerRepository customerRepo;

	@InjectMocks
	private CustomerServiceImpl customerService;

	@Test
	void shouldCreateCustomer() throws InvalidInputException {
		CustomerDto customer = new CustomerDto();
		customer.setId(1L);
		customer.setFirstname("john");
		customer.setLastname("Abraham");
		customer.setUsername("john");
		customer.setAddress("abc");
		customer.setContactNo(986754474L);

		when(customerRepo.findByUsername(ArgumentMatchers.any())).thenReturn(null);

		when(customerRepo.save(ArgumentMatchers.any())).thenReturn(CustomerBeanUtility.convertToDomain(customer));

		CustomerDto result = customerService.saveCustomer(customer);
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(1L);
	}

	@Test
	void shoulReturn400WhenCreateCustomerWithDuplicateUsername() {
		CustomerDto customer = new CustomerDto();
		customer.setId(1L);
		customer.setFirstname("john");
		customer.setLastname("Abraham");
		customer.setUsername("john");
		customer.setAddress("abc");
		customer.setContactNo(986754474L);

		List<Customer> custLst = new ArrayList<>();
		custLst.add(CustomerBeanUtility.convertToDomain(customer));
		when(customerRepo.findByUsername(ArgumentMatchers.any())).thenReturn(custLst);
		assertThrows(InvalidInputException.class, () -> {
			customerService.saveCustomer(customer);
		});

	}

	@Test
	void shoulReturn400WhenCreateCustomerAsNull() {

		assertThrows(InvalidInputException.class, () -> {
			customerService.saveCustomer(null);
		});

	}

	@Test
	void shouldReturnCustomerDetails() {
		CustomerDto customer = new CustomerDto();
		customer.setId(1L);
		customer.setFirstname("John");
		customer.setLastname("Abraham");
		customer.setUsername("john");
		customer.setAddress("abc");
		customer.setContactNo(986754474L);

		List<Customer> custLst = new ArrayList<>();
		custLst.add(CustomerBeanUtility.convertToDomain(customer));
		when(customerRepo.findByUsername(ArgumentMatchers.any())).thenReturn(custLst);

		CustomerDto result = customerService.getCustomerDetailsByUsername("john");
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getFirstname()).isEqualTo(customer.getFirstname());
	}

	@Test
	void shoulReturnNullIfuserDoesnotExist() {
		when(customerRepo.findByUsername(ArgumentMatchers.any())).thenReturn(null);
		assertNull(customerService.getCustomerDetailsByUsername("Arun"));
	}
}
