package com.example.businessservice.dto;

import lombok.Data;

@Data
public class AddressRequest {
    private String city;
    private String country;
    private String region;
    private String zipcode;
}
