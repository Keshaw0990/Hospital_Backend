package com.hospital.hospital.dto;

import lombok.Data;

@Data
public class FormMasterDTO {

    private Long pkFormId;
    private String name;
    private Integer parentId;
    private String link;

    private Long clientId;  // ⭐ REQUIRED FIELD

    private String archiveFlag;   // ⭐ ADD THIS
    private String readOnly;      // ⭐ ADD THIS
}
