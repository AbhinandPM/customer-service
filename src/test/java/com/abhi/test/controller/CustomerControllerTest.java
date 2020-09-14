package com.abhi.test.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.abhi.customer.controller.CustomerController;
import com.abhi.customer.dto.CustomerDto;
import com.abhi.customer.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CustomerService customerService;	
	
	private static ObjectMapper mapper = new ObjectMapper();

	@Test
	void testSearchCustomer() throws Exception {

		RequestBuilder request = MockMvcRequestBuilders.get("/customer/username/john")
				.accept(MediaType.APPLICATION_JSON);

		CustomerDto customer = new CustomerDto();
		customer.setFirstname("john");
		customer.setId(1L);
		customer.setUsername("john");
		customer.setFirstname("John");
		customer.setLastname("Abraham");

		when(customerService.getCustomerDetailsByUsername("john")).thenReturn(customer);

		mockMvc.perform(request)
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstname", Matchers.equalTo(customer.getFirstname())))
        		.andExpect(jsonPath("$.lastname", Matchers.equalTo(customer.getLastname())))
        		.andExpect(jsonPath("$.id", Matchers.equalTo(1)))
				.andReturn();

	}
	
	@Test
    void tesRegisterCustomer() throws Exception {
        CustomerDto customer = new CustomerDto();
        
        customer.setFirstname("John");
        customer.setLastname("Doe");
        customer.setUsername("john");
        customer.setAddress("Abc");
        customer.setContactNo(9384483234L);
        String json = mapper.writeValueAsString(customer);
        customer.setId(1L);
        
        when(customerService.saveCustomer(ArgumentMatchers.any()))
        	.thenReturn(customer);
        
		mockMvc.perform(post("/customer").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isOk())
        		.andExpect(jsonPath("$.firstname", Matchers.equalTo(customer.getFirstname())))
        		.andExpect(jsonPath("$.lastname", Matchers.equalTo(customer.getLastname())))
        		.andExpect(jsonPath("$.id", Matchers.equalTo(1)))
        		;
    }

}
