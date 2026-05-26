package com.example.businessservice.service;

import com.example.businessservice.client.UserServiceClient;
import com.example.businessservice.dto.BusinessPageResponse;
import com.example.businessservice.dto.BusinessRequest;
import com.example.businessservice.dto.BusinessResponse;
import com.example.businessservice.dto.UserResponse;
import com.example.businessservice.exception.APIException;
import com.example.businessservice.exception.ResourceNotFoundException;
import com.example.businessservice.mapper.BusinessMapper;
import com.example.businessservice.model.Business;
import com.example.businessservice.repository.BusinessRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {
    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;
    private final UserServiceClient userServiceClient;

    @Transactional
    @Override
    public BusinessResponse createBusiness(BusinessRequest request) {
        if (request == null)
            return null;

        try{
            userServiceClient.getUserDetails(request.getUserId());
        } catch (HttpClientErrorException ex){
            throw new ResourceNotFoundException("User", "userId", request.getUserId());
        }

        Business business = businessMapper.mapToBusiness(request);
        try{
            businessRepository.save(business);
        } catch (DataIntegrityViolationException ex){
            throw new APIException("Business with the name " + request.getBusinessName() + " already exists");
        }

        return businessMapper.mapToBusinessResponse(business);
    }

    @Override
    public BusinessPageResponse getAllBusinesses(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortAndOrderBy = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageRequest = PageRequest.of(pageNumber,pageSize,sortAndOrderBy);
        Page<Business> businessPage = businessRepository.findAll(pageRequest);
        List<BusinessResponse> businesses = businessPage.getContent()
                .stream()
                .map(businessMapper::mapToBusinessResponse)
                .toList();

        BusinessPageResponse response = new BusinessPageResponse();
        response.setBusinesses(businesses);
        response.setPageNumber(businessPage.getNumber());
        response.setPageSize(businessPage.getTotalPages());
        response.setTotalElements(businessPage.getTotalElements());
        response.setTotalPages(businessPage.getTotalPages());
        response.setLastPage(businessPage.isLast());

        return response;
    }

    @Override
    public BusinessResponse getBusinessById(Long businessId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(()-> new ResourceNotFoundException("Business", "businessId", businessId));

        return businessMapper.mapToBusinessResponse(business);
    }

    @Transactional
    @Override
    public BusinessResponse partialUpdateBusiness(Long businessId, BusinessRequest request) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(()-> new ResourceNotFoundException("Business", "businessId", businessId));

        businessMapper.mapUpdateBusiness(business, request);
        businessRepository.save(business);

        return businessMapper.mapToBusinessResponse(business);
    }


    @Transactional
    @Override
    public void deleteBusiness(Long businessId) {
        Business business = businessRepository.findById(businessId)
                .orElseThrow(()-> new ResourceNotFoundException("Business", "businessId", businessId));

        businessRepository.delete(business);
    }

}
