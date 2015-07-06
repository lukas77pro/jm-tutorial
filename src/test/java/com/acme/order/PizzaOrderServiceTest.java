package com.acme.order;

import javax.swing.plaf.synth.SynthScrollBarUI;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.mockito.internal.debugging.MockitoDebuggerImpl;

public class PizzaOrderServiceTest {

	private PizzaOrderService pizzaOrderService;
	
	private MailSender mailSender;
	private OrderDatabase orderDatabase;
	private OrderFactory orderFactory;
	private DeliveryTimeService deliveryTimeService;
	private MessageTemplateService messageTemplate;

	@Before
	public void init() {
		mailSender = Mockito.mock(MailSender.class);
		orderDatabase = Mockito.mock(OrderDatabase.class);
		orderFactory = Mockito.mock(OrderFactory.class);
		deliveryTimeService = Mockito.mock(DeliveryTimeService.class);
		messageTemplate = Mockito.mock(MessageTemplateService.class);

		pizzaOrderService = new PizzaOrderService(mailSender, orderDatabase, orderFactory, deliveryTimeService,
				messageTemplate);

	}

	@Test
	public void cancelledOrderShouldBePersistantAndEmailShouldBeSend() {
		//given
		String pizzaOrderID = "fake_ID";
		PizzaOrder givenPizzaOrder = giveOrder();
		//stub
		OrderCanceledTemplate template = Mockito.mock(OrderCanceledTemplate.class);
		Mockito.when(orderDatabase.get(Mockito.anyString())).thenReturn(givenPizzaOrder);
		Mockito.when(messageTemplate.getCancelTemplate()).thenReturn(template);
		//when
		pizzaOrderService.cancelOrder(pizzaOrderID);
		//then
		Assert.assertTrue(givenPizzaOrder.isCancelled());
		ArgumentCaptor<String> sentEmail = ArgumentCaptor.forClass(String.class);
		Mockito.verify(mailSender).send(Mockito.any(Template.class), sentEmail.capture());
		
		
		ArgumentCaptor<PizzaOrder> savedPizzaOrder = ArgumentCaptor.forClass(PizzaOrder.class);
		Mockito.verify(orderDatabase).save(savedPizzaOrder.capture());
		
		Assert.assertTrue(savedPizzaOrder.getValue().equals(givenPizzaOrder));
	}
	
	private PizzaOrder giveOrder(){
		Customer customer = givenCustomer();
		PizzaType pizzaType = Mockito.mock(PizzaType.class);		
		PizzaOrder givenOrder = new PizzaOrder(customer, pizzaType);
		return givenOrder;
	}
	
	
	private Customer givenCustomer() {
		String customerMail = "fake_email";
		Customer customer = new Customer();
		return customer;
	}
	
	@Test (expected=Exception.class)
	public void createdOrderShouldBeRollebackAndEmailShouldBeSend(){
		//given
		PizzaOrder givenPizzaOrder = giveOrder();
		Customer customer = givenCustomer();
		PizzaType type = Mockito.mock(PizzaType.class);
		//stub
		OrderCanceledTemplate template = Mockito.mock(OrderCanceledTemplate.class);
		Mockito.when(orderDatabase.get(Mockito.anyString())).thenReturn(givenPizzaOrder);
		Mockito.when(messageTemplate.getCancelTemplate()).thenReturn(template);
		
		//when
		pizzaOrderService.createOrder(customer, type);
		
		//then
		Assert.assertTrue(givenPizzaOrder.isCreated());
		
		ArgumentCaptor<String> sentEmail = ArgumentCaptor.forClass(String.class);
		Mockito.verify(mailSender).send(Mockito.any(Template.class), sentEmail.capture());		
		
		ArgumentCaptor<PizzaOrder> savedPizzaOrder = ArgumentCaptor.forClass(PizzaOrder.class);
		Mockito.verify(orderDatabase).save(savedPizzaOrder.capture());
		
		Assert.assertTrue(savedPizzaOrder.getValue().equals(givenPizzaOrder));
		//when 
	}
	
}
