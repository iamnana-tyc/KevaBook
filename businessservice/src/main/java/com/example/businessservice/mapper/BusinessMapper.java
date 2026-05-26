package com.example.businessservice.mapper;

import com.example.businessservice.dto.*;
import com.example.businessservice.model.Address;
import com.example.businessservice.model.Business;
import org.springframework.stereotype.Component;

@Component
public class BusinessMapper {

    public Business mapToBusiness(BusinessRequest request) {

        Business business = new Business();

        business.setBusinessName(request.getBusinessName());
        business.setDescription(request.getDescription());
        business.setBusinessImageUrl(request.getBusinessImageUrl());
        business.setBusinessWebsite(request.getBusinessWebsite());

        if (request.getAddress() != null) {

            Address address = new Address();

            address.setCity(request.getAddress().getCity());
            address.setCountry(request.getAddress().getCountry());
            address.setRegion(request.getAddress().getRegion());
            address.setZipcode(request.getAddress().getZipcode());

            business.setAddress(address);
        }

        business.setUserId(request.getUserId());

        return business;
    }

    public BusinessResponse mapToBusinessResponse(Business business) {

        BusinessResponse response = new BusinessResponse();

        response.setBusinessId(business.getBusinessId());
        response.setBusinessName(business.getBusinessName());
        response.setDescription(business.getDescription());
        response.setBusinessImageUrl(business.getBusinessImageUrl());
        response.setBusinessWebsite(business.getBusinessWebsite());

        if (business.getAddress() != null) {

            AddressResponse addressResponse = new AddressResponse();

            addressResponse.setCity(business.getAddress().getCity());
            addressResponse.setCountry(business.getAddress().getCountry());
            addressResponse.setRegion(business.getAddress().getRegion());
            addressResponse.setZipcode(business.getAddress().getZipcode());

            response.setAddress(addressResponse);
        }

        response.setUserId(business.getUserId());

        return response;
    }

    public void mapUpdateBusiness(Business business, BusinessRequest request){
        if (request.getBusinessName() != null)
            business.setBusinessName(request.getBusinessName());

        if (request.getDescription() != null)
            business.setDescription(request.getDescription());

        if (request.getBusinessImageUrl() != null)
            business.setBusinessImageUrl(request.getBusinessImageUrl());

        if (request.getBusinessWebsite() != null)
            business.setBusinessWebsite(request.getBusinessWebsite());

        if (request.getAddress() != null){
            Address address = business.getAddress();

            // if business has no address yet
            if (address == null) {
                address = new Address();
                address.setBusiness(business);
                business.setAddress(address);
            }

            if (request.getAddress().getCity() != null) {
                address.setCity(request.getAddress().getCity());
            }

            if (request.getAddress().getCountry() != null) {
                address.setCountry(request.getAddress().getCountry());
            }

            if (request.getAddress().getRegion() != null) {
                address.setRegion(request.getAddress().getRegion());
            }

            if (request.getAddress().getZipcode() != null) {
                address.setZipcode(request.getAddress().getZipcode());
            }
        }
    }
}