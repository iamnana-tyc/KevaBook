package com.example.businessservice.controller;

import com.example.businessservice.config.AppConstants;
import com.example.businessservice.dto.BusinessPageResponse;
import com.example.businessservice.dto.BusinessRequest;
import com.example.businessservice.dto.BusinessResponse;
import com.example.businessservice.service.BusinessService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/businesses")
@RequiredArgsConstructor
public class BusinessController {
    private final BusinessService businessService;

    @PostMapping
    public ResponseEntity<BusinessResponse> createBusiness(
            @Valid @RequestBody BusinessRequest request
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(businessService.createBusiness(request));
    }

    @GetMapping
    public ResponseEntity<BusinessPageResponse> getAllBusinesses(
            @RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam(name = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam(name = "sortBy",defaultValue = AppConstants.SORT_USERS_BY, required = false) String sortBy,
            @RequestParam(name = "sortOrder", defaultValue = AppConstants.SORT_DIRECTION, required = false) String sortOrder){
        return ResponseEntity.ok(businessService.getAllBusinesses(pageNumber, pageSize, sortBy, sortOrder));
    }

    @GetMapping("/{businessId}")
    public ResponseEntity<BusinessResponse> getBusinessById(@PathVariable Long businessId){
        return ResponseEntity.ok(businessService.getBusinessById(businessId));
    }


    @PatchMapping("/{businessId}")
    public ResponseEntity<BusinessResponse> partialUpdateBusiness(@PathVariable Long businessId,
                                                                  @RequestBody BusinessRequest request){
        return ResponseEntity.ok(businessService.partialUpdateBusiness(businessId, request));
    }


    @DeleteMapping("/{businessId}")
    public ResponseEntity<Void> deleteBusinessById(@PathVariable Long businessId){
        businessService.deleteBusiness(businessId);

        return ResponseEntity.noContent().build();
    }
}
