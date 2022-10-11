package com.getnet.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.getnet.app.dto.OfferDtoRequest;
import com.getnet.app.model.Offer;
import com.getnet.app.security.config.JwtTokenUtil;
import com.getnet.app.security.service.UserService;
import com.getnet.app.service.OfferService;

@WebMvcTest(OfferController.class)
@AutoConfigureMockMvc(addFilters = false)
public class OfferControllerTest {

	@Autowired
	OfferController controller;

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	JwtTokenUtil jwtTokenUtil;
	
	@MockBean
	private UserService userService;

	@MockBean
	private OfferService service;

	@Test
	public void should_Create_New_Offer() throws Exception {

		Offer offer = new Offer("Offer 01", 12d, LocalDate.now());
		when(this.service.create(any(Offer.class))).thenReturn(offer);

		String json = mapper.writeValueAsString(new OfferDtoRequest("Offer 01", 12D, LocalDate.now()));
		
		String jsonResponse = mvc.perform(post("/offers")
								.content(json)
								.characterEncoding("utf-8")
								.contentType(MediaType.APPLICATION_JSON))
								.andExpect(status().isCreated()).andReturn().getResponse().getContentAsString();

		JSONObject response = new JSONObject(jsonResponse);
		
		assertEquals(offer.getName(), response.get("name"));
		assertEquals(offer.getPercentDiscount(), response.get("percentDiscount"));
		assertEquals(offer.getExpirationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), response.get("expirationDate"));
	}

}
