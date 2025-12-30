package com.hospital.hospital.dto;

import lombok.Data;

@Data
public class ClientLoginResponse {

    private String orgName;
    private Long roleId;
    private Long clientId;
}
