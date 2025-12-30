package com.hospital.hospital.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientLoginRequest {

    @NotBlank
    private String emailId;

    @NotBlank
    private String password;
}
