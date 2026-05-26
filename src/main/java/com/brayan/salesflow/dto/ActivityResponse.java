package com.brayan.salesflow.dto;

import com.brayan.salesflow.entity.ActivityType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityResponse {

    private Long id;
    private ActivityType type;
    private String subject;
    private String description;
    private LocalDateTime activityDate;
    private Boolean completed;

    private Long customerId;
    private String customerName;

    private Long opportunityId;
    private String opportunityTitle;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}