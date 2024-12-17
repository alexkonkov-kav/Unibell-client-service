package com.Unibell.client_service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<PhoneNumber> phoneNumbers = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    private Set<Email> emails = new HashSet<>();

    public void addPhoneNumbers(PhoneNumber phoneNumber) {
        phoneNumber.setClient(this);
        phoneNumbers.add(phoneNumber);
    }

    public void addEmail(Email email) {
        email.setClient(this);
        emails.add(email);
    }
}
