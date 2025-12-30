package com.hospital.hospital.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RoleMasterDTO {

    private Long pkRoleId;
    private String name;
    private Integer parentRoleId;
    private Byte status;
    private String description;
    private LocalDateTime createdModifiedDate;
    private String readOnly;
    private String archiveFlag;
    private Long clientId;     // FK
    private String clientName; // optional
}
