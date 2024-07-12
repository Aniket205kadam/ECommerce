package dev.aniket.E_Commerce.model;

import dev.aniket.E_Commerce.dto.CustomerDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Street address is required")
    @Size(max = 255, message = "Street address can't be longer than 255 character")
    private String street;

    @NotBlank(message = "City name is required")
    @Size(max = 100, message = "City name can't be longer than 100 character")
    private String city;

    @NotBlank(message = "State name is required")
    @Size(max = 100, message = "State name can't be longer than 100 character")
    private String state;

    @NotBlank(message = "Postal-code is required")
    @Size(max = 20, message = "Postal-code can't be longer than 20 character")
    private String postalCode;

    @NotBlank(message = "Country name is required")
    @Size(max = 100, message = "Country name can't be longer than 100 character")
    private String country;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerDto customer;
}
