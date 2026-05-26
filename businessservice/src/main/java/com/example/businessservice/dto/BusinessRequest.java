package com.example.businessservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BusinessRequest {
    @NotBlank(message = "Business name is required")
    private String businessName;
    private String description;
    private String businessImageUrl;
    private String businessWebsite;
    private AddressDTO address;

    @NotBlank(message = "userId is required")
    private String userId;

}
