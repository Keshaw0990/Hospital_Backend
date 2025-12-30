package com.hospital.hospital.dto;

import lombok.Data;
import java.util.List;

@Data
public class RoleMappingResponseDTO {
    private Long roleId;
    private String roleName;
    private List<RoleDetailsMappingDTO> forms;
}
