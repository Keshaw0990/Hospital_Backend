package com.hospital.hospital.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClientMasterDTOAdd {

    private String address;
    private String emailId;
    private String mobileNo;
    private String password;
    private String orgName;
    private Byte status;
    private LocalDate expiryDate;
    private Integer clientCount;
    private String logo;
    private Long roleId;
    private String roleName;
    private Integer patientCount;
}

