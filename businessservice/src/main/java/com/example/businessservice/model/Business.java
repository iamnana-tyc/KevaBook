package com.example.businessservice.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "businesses")
public class Business {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long businessId;

    private String userId;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;
    private String businessLogoUrl;
    private String businessWebsite;
    private String businessEmail;
    private String phoneNumber;

    @OneToOne(mappedBy = "business", cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Address address;

    @Enumerated(EnumType.STRING)
    private BusinessStatus status;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public void setAddress(Address address) {
        this.address = address;

        if (address != null) {
            address.setBusiness(this);
        }
    }
}
