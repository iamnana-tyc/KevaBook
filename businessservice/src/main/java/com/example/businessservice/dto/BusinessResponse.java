package com.example.businessservice.dto;

import lombok.Data;

@Data
public class BusinessResponse {
    private Long businessId;
    private String businessName;
    private String description;
    private String businessImageUrl;
    private String businessWebsite;
    private AddressResponse address;
    private String userId;

}
