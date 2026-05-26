package com.brayan.salesflow.dto;

import com.brayan.salesflow.entity.CustomerStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @NotBlank(message = "companyName is required")
    @Size(max = 120)
    private String companyName;

    @Size(max = 20)
    private String ruc;

    @Size(max = 80)
    private String industry;

    @Email
    @Size(max = 120)
    private String email;

    @Size(max = 30)
    private String phone;

    @Size(max = 200)
    private String address;

    private CustomerStatus status;

}
