package com.Unibell.client_service;

import com.Unibell.client_service.dto.ClientRequestDTO;
import com.Unibell.client_service.entity.Client;
import com.Unibell.client_service.entity.Email;
import com.Unibell.client_service.entity.PhoneNumber;
import com.Unibell.client_service.repository.ClientRepository;
import com.Unibell.client_service.service.ClientService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ClientServiceApplicationTests {

	@Mock
	private ClientRepository clientRepository;

	@InjectMocks
	private ClientService clientService;

	@Test
	public void testCreateClient() {

		ClientRequestDTO clientRequestDTO = new ClientRequestDTO("Иванов Иван",
				Set.of("+123456789", "+223456789"), Set.of("ivanov@xyz.com", "ivanov@xyz.ru"));

		Client expectedClient = new Client();
		expectedClient.setName(clientRequestDTO.name());
		Set<PhoneNumber> expectedPhoneNumbers = new HashSet<>();
		for (String phoneNumber : clientRequestDTO.phoneNumbers()) {
			PhoneNumber newPhoneNumber = new PhoneNumber(phoneNumber);
			newPhoneNumber.setClient(expectedClient);
			expectedPhoneNumbers.add(newPhoneNumber);
		}
		expectedClient.setPhoneNumbers(expectedPhoneNumbers);

		Set<Email> expectedEmails = new HashSet<>();
		for (String email : clientRequestDTO.emails()) {
			Email newEmail = new Email(email);
			newEmail.setClient(expectedClient);
			expectedEmails.add(newEmail);
		}
		expectedClient.setEmails(expectedEmails);

		Mockito.when(clientRepository.save(Mockito.any(Client.class))).thenReturn(expectedClient);

		Client createdClient = clientService.createClient(clientRequestDTO);

		assertThat(createdClient.getName()).isEqualTo(expectedClient.getName());
		assertThat(createdClient.getPhoneNumbers()).hasSameSizeAs(expectedClient.getPhoneNumbers());
		assertThat(createdClient.getPhoneNumbers()).containsExactlyInAnyOrderElementsOf(expectedClient.getPhoneNumbers());
		assertThat(createdClient.getEmails()).hasSameSizeAs(expectedClient.getEmails());
		assertThat(createdClient.getEmails()).containsExactlyInAnyOrderElementsOf(expectedClient.getEmails());
	}
}
