package com.example.businessservice.dto;

import lombok.Data;

@Data
public class BusinessRequest {
    private String businessName;
    private String description;
    private String businessImageUrl;
    private String businessWebsite;
    private AddressDTO address;
}
