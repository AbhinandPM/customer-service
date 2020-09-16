package com.abhi.test.consumer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.abhi.customer.consumer.QueueMessageConsumer;
import com.abhi.customer.dto.CustomerDto;
import com.abhi.customer.service.CustomerService;
import com.abhi.customer.util.InvalidInputException;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class QueueMessageConsumerTest {

	@Mock
	private CustomerService customerService;
	
	@Mock
	private JmsTemplate jmsTemplate;
	
	@InjectMocks
	private QueueMessageConsumer messageConsumer;
	
	@Test
	void testMessageConsume() throws InvalidInputException {
		when(customerService.saveCustomer(ArgumentMatchers.any())).thenReturn(new CustomerDto());
		
		assertDoesNotThrow(() -> {
			messageConsumer.listener(new CustomerDto());
		});
	}
}
