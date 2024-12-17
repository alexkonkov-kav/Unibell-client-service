package com.Unibell.client_service.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "phone_number")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PhoneNumber {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private Long id;

        @Column(name = "number", unique = true, nullable = false)
        private String number;

        @JsonBackReference
        @ManyToOne
        @JoinColumn(name = "client_id")
        private Client client;

        public PhoneNumber(String number) {
                this.number = number;
        }
}
