package com.getnet.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.getnet.app.exception.ConflictException;
import com.getnet.app.exception.NotFoundException;
import com.getnet.app.model.Client;
import com.getnet.app.model.Offer;
import com.getnet.app.model.Voucher;
import com.getnet.app.repository.VoucherRepository;

@SpringBootTest
public class VoucherServiceTest {
	
	@Autowired
	VoucherService service;

	@MockBean
	VoucherRepository repository;

	@Test
	public void should_Create_New_Vouchers() throws Exception {
		
		Client clientJohni = new Client("johni@gmail.com", "johni pires", "123456");
		Client clientPedro = new Client("pedro@gmail.com", "johni pires", "123456");
		Client clientMaria = new Client("maria@gmail.com", "johni pires", "123456");
		
		List<Client> clients = new ArrayList<>();
		clients.add(clientJohni);
		clients.add(clientPedro);
		clients.add(clientMaria);

		Offer offer = new Offer("57baf801-c0e2-451a-b0eb-dd9441514dfe","Offer 01", 12d, LocalDate.now());
		
		service.create(clients, offer);
		
		verify(repository, atLeast(3)).save(any(Voucher.class));

	}
	
	@Test
	public void should_Redeem_Voucher() throws Exception {
		
		Offer offer = new Offer();
		offer.setName("Offer 01");
		offer.setPercentDiscount(15D);
		
		Voucher voucherReturn = new Voucher(
				"id", 
				"57baf801-c0e2-451a-b0eb-dd9441514dfe", 
				"Johni Pires", 
				"email", 
				null, 
				offer );
		
		when(repository.findByEmailAndCode(anyString(), anyString(), any(LocalDate.class))).thenReturn( Optional.of(voucherReturn));
		when(repository.save(any(Voucher.class))).thenReturn( voucherReturn);
		
		Voucher voucher = service.redeem("johni@gmail.com", "57baf801-c0e2-451a-b0eb-dd9441514dfe");
		
		assertEquals(voucherReturn.getCode(), voucher.getCode());
		assertEquals(voucherReturn.getClientName(), voucher.getClientName());
		assertEquals(voucherReturn.getOffer().getName(), voucher.getOffer().getName());
		assertEquals(voucherReturn.getEmail(), voucher.getEmail());
		assertEquals(voucherReturn.getOffer().getPercentDiscount(), voucher.getOffer().getPercentDiscount());
	}

	@Test
	public void should_ThrowException_When_Not_Found_Voucher() throws Exception {

		when(repository.findByEmailAndCode(anyString(), anyString(), any(LocalDate.class))).thenReturn( Optional.empty());
		
		assertThrows(NotFoundException.class, () -> service.redeem("johni@gmail.com", "57baf801-c0e2-451a-b0eb-dd9441514dfe"));
	}
	
	@Test
	public void should_ThrowException_When_Voucher_Already_Redeemed() throws Exception {
		
		Offer offer = new Offer();
		offer.setName("Offer 01");
		offer.setPercentDiscount(15D);
		
		Voucher voucherReturn = new Voucher(
				"id", 
				"57baf801-c0e2-451a-b0eb-dd9441514dfe", 
				"Johni Pires", 
				"email", 
				LocalDate.now(), 
				offer );
		
		when(repository.findByEmailAndCode(anyString(), anyString(), any(LocalDate.class))).thenReturn( Optional.of(voucherReturn));

		assertThrows(ConflictException.class, () -> service.redeem("johni@gmail.com", "57baf801-c0e2-451a-b0eb-dd9441514dfe"));
	}

	@Test
	public void should_Find_All_Vouchers_By_Email() throws Exception {
		
		Offer offer = new Offer();
		offer.setName("Offer 01");
		offer.setPercentDiscount(15D);
		
		Voucher voucherReturn = new Voucher(
				"id", 
				"57baf801-c0e2-451a-b0eb-dd9441514dfe", 
				"Johni Pires", 
				"email", 
				LocalDate.now(), 
				offer );
		
		List<Voucher> voucherList = new ArrayList<>();
		voucherList.add(voucherReturn);
		
		when(repository.findAllByEmail(anyString(), any(LocalDate.class))).thenReturn(Optional.of(voucherList));
		
		List<Voucher> vouchers = service.findAllByEmail("johni@gmail.com");
		
		assertEquals(vouchers.size(), voucherList.size());
		
	}
	
	@Test
	public void should_ThrowException_When_Voucher_Not_Find_By_Email() throws Exception {
		
		when(repository.findAllByEmail(anyString(), any(LocalDate.class))).thenReturn( Optional.empty());

		assertThrows(NotFoundException.class, () -> service.findAllByEmail("johni@gmail.com"));
	}
	
	@Test
	public void should_Find_All_Vouchers_By_Code() throws Exception {
		
		Offer offer = new Offer();
		offer.setName("Offer 01");
		offer.setPercentDiscount(15D);
		
		Voucher voucherReturn = new Voucher(
				"id", 
				"57baf801-c0e2-451a-b0eb-dd9441514dfe", 
				"Johni Pires", 
				"email", 
				LocalDate.now(), 
				offer );
		
		
		when(repository.findByCode(anyString())).thenReturn(Optional.of(voucherReturn));
		
		Voucher voucher = service.findByCode("57baf801-c0e2-451a-b0eb-dd9441514dfe");
		
		assertEquals(voucherReturn.getCode(), voucher.getCode());
		assertEquals(voucherReturn.getClientName(), voucher.getClientName());
		assertEquals(voucherReturn.getOffer().getName(), voucher.getOffer().getName());
		assertEquals(voucherReturn.getEmail(), voucher.getEmail());
		assertEquals(voucherReturn.getOffer().getPercentDiscount(), voucher.getOffer().getPercentDiscount());
		
	}
	
	@Test
	public void should_ThrowException_When_Voucher_Not_Find_By_Code() throws Exception {
		
		when(repository.findByCode(anyString())).thenReturn( Optional.empty());

		assertThrows(NotFoundException.class, () -> service.findByCode("57baf801-c0e2-451a-b0eb-dd9441514dfe"));
	}
	
	
	@Test
	public void should_Find_All_Vouchers() throws Exception {
		
		Offer offer = new Offer();
		offer.setName("Offer 01");
		offer.setPercentDiscount(15D);
		
		Voucher voucherReturn = new Voucher(
				"id", 
				"57baf801-c0e2-451a-b0eb-dd9441514dfe", 
				"Johni Pires", 
				"email", 
				LocalDate.now(), 
				offer );
		
		List<Voucher> voucherList = new ArrayList<>();
		voucherList.add(voucherReturn);
		
		when(repository.findAll()).thenReturn(voucherList);
		
		 List<Voucher> vouchers = service.findAll();
		
		 assertEquals(vouchers.size(), voucherList.size());
	}
	
	@Test
	public void should_ThrowException_When_Voucher_Not_Find() throws Exception {
		
		when(repository.findAll()).thenReturn( new ArrayList<>());

		assertThrows(NotFoundException.class, () -> service.findAll());
	}
	

}
