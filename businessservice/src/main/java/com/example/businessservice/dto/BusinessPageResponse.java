package com.example.businessservice.dto;

import lombok.Data;

import java.util.List;

@Data
public class BusinessPageResponse {
    private List<BusinessResponse> businesses;
    private Integer pageNumber;
    private Integer pageSize;
    private Long totalElements;
    private Integer totalPages;
    private boolean lastPage;
}
