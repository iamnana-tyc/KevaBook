package com.example.bookingservice.dto;

import com.example.bookingservice.model.BookingStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreatingBookingRequest {
    private Long bookingId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer durationTime;
    private BookingStatus status;
    private String userId;
    private String businessId;
}
