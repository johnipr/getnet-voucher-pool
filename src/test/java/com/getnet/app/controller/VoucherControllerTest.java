package com.getnet.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getnet.app.dto.VoucherDtoRequest;
import com.getnet.app.dto.VoucherDtoResponse;
import com.getnet.app.model.Offer;
import com.getnet.app.model.Voucher;
import com.getnet.app.security.config.JwtTokenUtil;
import com.getnet.app.security.service.UserService;
import com.getnet.app.service.VoucherService;

@WebMvcTest(VoucherController.class)
@AutoConfigureMockMvc(addFilters = false)
public class VoucherControllerTest {
	
	@Autowired
	VoucherController controller;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;
	
	@MockBean
	private VoucherService voucherService;
	
	@MockBean
	JwtTokenUtil jwtTokenUtil;
	
	@MockBean
	private UserService userService;
	
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
				LocalDate.now(), 
				offer );
		
		when(this.voucherService.redeem(anyString(), anyString())).thenReturn(voucherReturn);

		String json = mapper.writeValueAsString(new VoucherDtoRequest("johni@gmail.com", "57baf801-c0e2-451a-b0eb-dd9441514dfe"));
		
		String jsonResponse = mvc.perform(post("/vouchers/redeem")
								.content(json)
								.characterEncoding("utf-8")
								.contentType(MediaType.APPLICATION_JSON))
								.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();

		
		VoucherDtoResponse response = mapper.readValue(jsonResponse, VoucherDtoResponse.class);
		
		assertEquals(voucherReturn.getCode(), response.getCode());
		assertEquals(voucherReturn.getClientName(), response.getClientName());
		assertEquals(voucherReturn.getRedeemDate(), response.getRedeemDate());
		assertEquals(voucherReturn.getOffer().getPercentDiscount(), response.getPercentDiscount());
		assertEquals(voucherReturn.getOffer().getName(), response.getOfferName());
	}
	
	@Test
	public void should_Find_Voucher_By_Email() throws Exception {

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
		
		when(this.voucherService.findAllByEmail(anyString())).thenReturn(voucherList );

		
		String jsonResponse = mvc.perform(get("/vouchers/email/johni@gmail.com")
								.characterEncoding("utf-8")
								.contentType(MediaType.APPLICATION_JSON))
								.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		List<VoucherDtoResponse> voucherDtoResponseList = mapper.readValue(jsonResponse, new TypeReference<List<VoucherDtoResponse>>(){});
		
		assertEquals(voucherDtoResponseList.size(), 1);
		
	}
	
	@Test
	public void should_Find_All_Voucher() throws Exception {

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
		
		when(this.voucherService.findAll()).thenReturn(voucherList );

		
		String jsonResponse = mvc.perform(get("/vouchers")
								.characterEncoding("utf-8")
								.contentType(MediaType.APPLICATION_JSON))
								.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		List<VoucherDtoResponse> voucherDtoResponseList = mapper.readValue(jsonResponse, new TypeReference<List<VoucherDtoResponse>>(){});
		
		assertEquals(voucherDtoResponseList.size(), 1);
	}
	
	@Test
	public void should_Find_Voucher_By_Code() throws Exception {

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
		
		when(this.voucherService.findByCode("57baf801-c0e2-451a-b0eb-dd9441514dfe")).thenReturn(voucherReturn );

		
		String jsonResponse = mvc.perform(get("/vouchers/57baf801-c0e2-451a-b0eb-dd9441514dfe")
								.characterEncoding("utf-8")
								.contentType(MediaType.APPLICATION_JSON))
								.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		
		VoucherDtoResponse voucherDtoResponse = mapper.readValue(jsonResponse, VoucherDtoResponse.class);
		
		assertEquals(voucherDtoResponse.getClientName(), voucherReturn.getClientName());
		assertEquals(voucherDtoResponse.getCode(), voucherReturn.getCode());
		assertEquals(voucherDtoResponse.getOfferName(), voucherReturn.getOffer().getName());
		assertEquals(voucherDtoResponse.getPercentDiscount(), voucherReturn.getOffer().getPercentDiscount());
		assertEquals(voucherDtoResponse.getRedeemDate(), voucherReturn.getRedeemDate());
	}
	

}
