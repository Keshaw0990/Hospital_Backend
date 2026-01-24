package com.hospital.hospital.dto;

import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ClientMasterDTO {

    private Long pkClientId;
    private String address;
    private String emailId;
    private String mobileNo;
    private Byte status;
    private String orgName;
    private LocalDateTime createdModifiedDate;
    private String readOnly;
    private String archiveFlag;
    private LocalDate expiryDate;
    private Integer clientCount;
    private String logo;
    private Long roleId;
    private String roleName;
    private String password; // kept as you asked
    private Integer patientCount;
}
