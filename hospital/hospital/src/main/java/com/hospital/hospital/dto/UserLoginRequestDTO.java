package com.hospital.hospital.dto;

import lombok.Data;

@Data
public class UserLoginRequestDTO {
    private String emailId;
    private String password;
}
