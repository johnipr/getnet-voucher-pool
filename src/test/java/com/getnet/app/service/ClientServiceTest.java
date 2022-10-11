package com.getnet.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;

import com.getnet.app.exception.ConflictException;
import com.getnet.app.model.Client;
import com.getnet.app.repository.ClientRepository;

@SpringBootTest
public class ClientServiceTest {

	@Autowired
	ClientService clientService;

	@MockBean
	ClientRepository clientRepository;

	@Test
	public void should_Create_New_Client() throws Exception {
		Client clientReturn = new Client("johni@gmail.com", "johni pires", "123456");
		when(this.clientRepository.save(any(Client.class))).thenReturn(clientReturn);

		Client client = new Client("johni@gmail.com", "johni pires", "password");
		Client clientResponse = clientService.signup(client);

		assertEquals(clientResponse.getEmail(), clientReturn.getEmail());
		assertEquals(clientResponse.getName(), clientReturn.getName());
	}

	@Test
	public void should_ThrowException_When_Email_Is_Duplicated() throws Exception {

		when(this.clientRepository.save(any(Client.class))).thenThrow(new DuplicateKeyException(""));

		Client client = new Client("johni@gmail.com", "johni pires", "password");
		
		assertThrows(ConflictException.class, () -> clientService.signup(client));
	}

}
