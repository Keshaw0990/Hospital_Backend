package com.hospital.hospital.dto;

import lombok.Data;

@Data
public class RoleDetailsMappingDTO {
    private Long formId;
    private String formName;
    private Integer seqNo;
    private Byte isAllowed;
    private Byte showInMenu;
}
