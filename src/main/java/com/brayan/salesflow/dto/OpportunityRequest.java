package com.brayan.salesflow.dto;

import com.brayan.salesflow.entity.OpportunityStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityRequest {

    @NotBlank(message = "El título es obligatorio")
    @Size(max = 120, message = "El título no debe superar los 120 caracteres")
    private String title;

    @Size(max = 500, message = "La descripción no debe superar los 500 caracteres")
    private String description;

    @NotNull(message = "El monto estimado es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El monto estimado debe ser mayor o igual a 0")
    private BigDecimal estimatedAmount;

    private OpportunityStatus status;

    private LocalDate expectedCloseDate;

    @NotNull(message = "El cliente es obligatorio")
    private Long customerId;
}