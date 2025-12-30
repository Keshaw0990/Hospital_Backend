package com.hospital.hospital.utils;

import com.hospital.hospital.dto.RoleFormDTO;
import com.hospital.hospital.entity.TbRoleDetails;
import org.springframework.stereotype.Component;


@Component
public class DTOService {

    public RoleFormDTO convertRoleDetailToRoleFormDTO(TbRoleDetails details) {
        RoleFormDTO dto = new RoleFormDTO();
        dto.setFormName(details.getForm().getName());
        dto.setFormId(Math.toIntExact(details.getForm().getPkFormId()));
        dto.setFormLink(details.getForm().getLink());
        dto.setCreatedModifiedDate(details.getCreatedModifiedDate());
        dto.setShowInMenu(details.getShowInMenu());
        dto.setSeqNo(details.getSeqNo());
        dto.setIsAllowed(details.getIsAllowed()); // ✅ Set isAllowed value
        return dto;
    }
}
