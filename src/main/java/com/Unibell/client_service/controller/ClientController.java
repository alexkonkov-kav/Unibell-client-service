package com.Unibell.client_service.controller;

import com.Unibell.client_service.dto.*;
import com.Unibell.client_service.entity.Client;
import com.Unibell.client_service.entity.Email;
import com.Unibell.client_service.entity.PhoneNumber;
import com.Unibell.client_service.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    //Добавление нового Client
    @PostMapping("/addclients")
    public ResponseEntity<ClientResponseDTO> createClient(@RequestBody ClientRequestDTO clientRequestDTO) {
        Client createdClient = clientService.createClient(clientRequestDTO);
        ClientResponseDTO clientResponseDTO = new ClientResponseDTO(createdClient.getId(), createdClient.getName(),
                createdClient.getPhoneNumbers().stream().map(PhoneNumber::getNumber).collect(Collectors.toSet()),
                createdClient.getEmails().stream().map(Email::getEmail).collect(Collectors.toSet()));
        return ResponseEntity.status(HttpStatus.CREATED).body(clientResponseDTO);
    }

    //Добавление нового PhoneNumber
    @PutMapping("/{clientId}/phonenumbers")
    public ResponseEntity<Client> addPhoneNumberToClient(@PathVariable Long clientId, @RequestBody PhoneNumber phoneNumber) {
        Client updatedClient = clientService.addPhoneNumberToClient(clientId, phoneNumber);
        return ResponseEntity.ok(updatedClient);
    }

    //Добавление нового Email
    @PutMapping("/{clientId}/emails")
    public ResponseEntity<Client> addEmailToClient(@PathVariable Long clientId, @RequestBody Email email) {
        Client updatedClient = clientService.addEmailToClient(clientId, email);
        return ResponseEntity.ok(updatedClient);
    }

    //Получение списка Client по имени. Можно было просто finAll сделать, но сделал по имени или части
    @GetMapping("/searchclients")
    public List<ClientResponseDTO> searchClient(@RequestBody ClientRequestDTO clientRequestDTO) {
        return clientService.searchClient(clientRequestDTO);
    }

    //Получение информации по id клиента
    @GetMapping("/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable Long clientId) {
        Optional<Client> clientOptional = clientService.getClientById(clientId);
        return clientOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Получение контактов по id клиента
    @GetMapping("/{clientId}/contacts")
    public ResponseEntity<ContactClientDTO> getContactsClientById(@PathVariable Long clientId) {
        ContactClientDTO contactClientDTO = clientService.getContactsClientById(clientId);
        return ResponseEntity.of(Optional.ofNullable(contactClientDTO));
    }

    //Получение PhoneNumber по id клиента
    @GetMapping("/{clientId}/phonenumbers")
    public ResponseEntity<PhoneNumbersClientDTO> getPhoneNumbersClientById(@PathVariable Long clientId) {
        PhoneNumbersClientDTO phoneNumbersClientDTO = clientService.getPhoneNumbersClientById(clientId);
        return ResponseEntity.of(Optional.ofNullable(phoneNumbersClientDTO));
    }

    //Получение Email по id клиента
    @GetMapping("/{clientId}/emails")
    public ResponseEntity<EmailsClientDTO> getEmailsClientById(@PathVariable Long clientId) {
        EmailsClientDTO emailsClientDTO = clientService.getEmailsClientDTOClientById(clientId);
        return ResponseEntity.of(Optional.ofNullable(emailsClientDTO));
    }
}
