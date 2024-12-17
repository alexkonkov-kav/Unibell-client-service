package com.Unibell.client_service.service;

import com.Unibell.client_service.dto.*;
import com.Unibell.client_service.entity.Client;
import com.Unibell.client_service.entity.Email;
import com.Unibell.client_service.entity.PhoneNumber;
import com.Unibell.client_service.exception.ClientNotFoundException;
import com.Unibell.client_service.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;

    public Client createClient(ClientRequestDTO clientRequestDTO) {
        Client client = new Client();
        client.setName(clientRequestDTO.name());

        if (clientRequestDTO.phoneNumbers() != null && !clientRequestDTO.phoneNumbers().isEmpty()) {
            for (String phoneNumber : clientRequestDTO.phoneNumbers()) {
                PhoneNumber newPhoneNumber = new PhoneNumber();
                newPhoneNumber.setNumber(phoneNumber);
                newPhoneNumber.setClient(client);
                client.getPhoneNumbers().add(newPhoneNumber);
            }
        }

        if (clientRequestDTO.emails() != null && !clientRequestDTO.emails().isEmpty()) {
            for (String email : clientRequestDTO.emails()) {
                Email newEmail = new Email();
                newEmail.setEmail(email);
                newEmail.setClient(client);
                client.getEmails().add(newEmail);
            }
        }
        return clientRepository.save(client);
    }

    public Client addPhoneNumberToClient(Long clientId, PhoneNumber phoneNumber) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Unable to find account with id: " + clientId));
        PhoneNumber number = new PhoneNumber(phoneNumber.getNumber());
        number.setClient(client);
        client.getPhoneNumbers().add(number);
        return clientRepository.save(client);
    }

    public Client addEmailToClient(Long clientId, Email email) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ClientNotFoundException("Unable to find account with id: " + clientId));
        Email newEmail = new Email(email.getEmail());
        newEmail.setClient(client);
        client.getEmails().add(newEmail);
        return clientRepository.save(client);
    }

    public List<ClientResponseDTO> searchClient(ClientRequestDTO clientRequestDTO) {
        List<Client> clients = clientRepository.findByNameContainingIgnoreCase(clientRequestDTO.name());

        return clients.stream()
                .map(client -> {
                    return new ClientResponseDTO(client.getId(), client.getName(),
                            client.getPhoneNumbers().stream().map(PhoneNumber::getNumber).collect(Collectors.toSet()),
                            client.getEmails().stream().map(Email::getEmail).collect(Collectors.toSet()));
                }).toList();
    }

    public Optional<Client> getClientById(Long clientId) {
        return clientRepository.findById(clientId);
    }

    public ContactClientDTO getContactsClientById(Long clientId) {
        Optional<Client> clientOp = clientRepository.findById(clientId);
        return clientOp.map(client -> new ContactClientDTO(
                client.getPhoneNumbers().stream().map(PhoneNumber::getNumber).collect(Collectors.toSet()),
                client.getEmails().stream().map(Email::getEmail).collect(Collectors.toSet()))).orElse(null);
    }

    public PhoneNumbersClientDTO getPhoneNumbersClientById(Long clientId) {
        Optional<Client> clientOp = clientRepository.findById(clientId);
        return clientOp.map(client -> new PhoneNumbersClientDTO(
                client.getPhoneNumbers().stream().map(PhoneNumber::getNumber).collect(Collectors.toSet())))
                .orElse(null);
    }

    public EmailsClientDTO getEmailsClientDTOClientById(Long clientId) {
        Optional<Client> clientOp = clientRepository.findById(clientId);
        return clientOp.map(client -> new EmailsClientDTO(
                        client.getEmails().stream().map(Email::getEmail).collect(Collectors.toSet())))
                .orElse(null);
    }
}
