package com.example.businessservice.service;

import com.example.businessservice.dto.BusinessPageResponse;
import com.example.businessservice.dto.BusinessRequest;
import com.example.businessservice.dto.BusinessResponse;

public interface BusinessService {
    BusinessResponse createBusiness(BusinessRequest request);

    BusinessPageResponse getAllBusinesses(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    BusinessResponse getBusinessById(Long businessId);

    BusinessResponse partialUpdateBusiness(Long businessId, BusinessRequest request);

    void deleteBusiness(Long businessId);
}
