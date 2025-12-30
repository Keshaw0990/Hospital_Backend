package com.hospital.hospital.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RoleDetailsDTO {

    private Long pkRoleDetailsId;

    private Long roleId;
    private String roleName;  // ADD

    private Long formId;
    private String formName;  // ADD
    private String formLink;  // ADD THIS

    private Integer seqNo;
    private Byte isAllowed;

    private LocalDateTime createdModifiedDate;
    private String readOnly;
    private String archiveFlag;
    private Long clientId;
    private Byte showInMenu;
}

