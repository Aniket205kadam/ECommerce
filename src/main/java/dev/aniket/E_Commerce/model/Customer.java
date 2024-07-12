package dev.aniket.E_Commerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Size(min = 2, max = 100)
    private String firstname;

    @NotNull
    @Size(min = 2, max = 100)
    private String lastname;

    @NotNull
    @Column(name = "username", unique = true)
    private String username;

    @Email
    @Column(name = "email_id", unique = true)
    private String email;

    @NotNull
    @Size(min = 8, max = 16)
    private String password;

    @OneToMany(mappedBy = "customer")
    private List<Address> address;

    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
}
