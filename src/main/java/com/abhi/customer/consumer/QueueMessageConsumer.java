package com.abhi.customer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.abhi.customer.dto.CustomerDto;
import com.abhi.customer.service.CustomerService;
import com.abhi.customer.util.InvalidInputException;

@Component
public class QueueMessageConsumer {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Value("${CREATE_CUSTOMER_SUCCESS_QUEUE}")
	private String createCustomerSuccessQueue;
	
	@Value("${CREATE_CUSTOMER_FAILURE_QUEUE}")
	private String createCustomerFailureQueue;

	private final Logger logger = LoggerFactory.getLogger(QueueMessageConsumer.class);

	@JmsListener(destination = "${CREATE_USER_QUEUE}")
	public void listener(CustomerDto customer ) {
		logger.info("Message received {} ", customer);
		try {
			customer = customerService.saveCustomer(customer);
			jmsTemplate.convertAndSend(this.createCustomerSuccessQueue, customer);
		} catch (InvalidInputException e) {
			logger.error(e.getMessage(),e);
			jmsTemplate.convertAndSend(this.createCustomerFailureQueue, customer);
		}
	}
}
