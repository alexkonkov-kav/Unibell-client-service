package com.Unibell.client_service.repository;

import com.Unibell.client_service.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository <Client, Long> {
    List<Client> findByNameContainingIgnoreCase(String name);
}
