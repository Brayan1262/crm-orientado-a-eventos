package com.brayan.salesflow.dto;

import com.brayan.salesflow.entity.ActivityType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActivityRequest {

    @NotNull(message = "El tipo de actividad es obligatorio")
    private ActivityType type;

    @NotBlank(message = "El asunto es obligatorio")
    @Size(max = 120, message = "El asunto no debe superar los 120 caracteres")
    private String subject;

    @Size(max = 500, message = "La descripción no debe superar los 500 caracteres")
    private String description;

    @NotNull(message = "La fecha de la actividad es obligatoria")
    private LocalDateTime activityDate;

    private Boolean completed;

    private Long customerId;

    private Long opportunityId;
}