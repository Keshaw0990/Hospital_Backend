package com.hospital.hospital.service;

import com.hospital.hospital.dto.RoleFormDTO;
import com.hospital.hospital.dto.RoleMasterDTO;
import com.hospital.hospital.entity.TbClientMaster;
import com.hospital.hospital.entity.TbRoleDetails;
import com.hospital.hospital.entity.TbRoleMaster;
import com.hospital.hospital.repo.RoleDetailsRepository;
import com.hospital.hospital.repo.RoleMasterRepository;
import com.hospital.hospital.utils.DTOService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleMasterService {

    @Autowired
    private DTOService dtoService;
    private final RoleMasterRepository repo;

    private final RoleDetailsRepository roleDetailsRepository;

    private RoleMasterDTO toDTO(TbRoleMaster role) {
        RoleMasterDTO dto = new RoleMasterDTO();

        dto.setPkRoleId(role.getPkRoleId());
        dto.setName(role.getName());
        dto.setParentRoleId(role.getParentRoleId());
        dto.setStatus(role.getStatus());
        dto.setDescription(role.getDescription());
        dto.setCreatedModifiedDate(role.getCreatedModifiedDate());
        dto.setReadOnly(role.getReadOnly());
        dto.setArchiveFlag(role.getArchiveFlag());

//        if (role.getClient() != null) {
//            dto.setClientId(role.getClient().getPkClientId());
//            dto.setClientName(role.getClient().getOrgName());
//        }


        return dto;
    }

    // GET ALL ROLE
    public List<RoleMasterDTO> getAllRoles() {
        return repo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    // GET BY ID
    public RoleMasterDTO getRoleById(Long id) {
        TbRoleMaster role = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        return toDTO(role);
    }

    // ADD ROLE
    public RoleMasterDTO addRole(RoleMasterDTO dto) {
        TbRoleMaster role = new TbRoleMaster();

        role.setName(dto.getName());
        role.setParentRoleId(dto.getParentRoleId());
        role.setStatus(dto.getStatus());
        role.setDescription(dto.getDescription());
        role.setCreatedModifiedDate(LocalDateTime.now());
        role.setReadOnly("N");
        role.setArchiveFlag("F");

//        if (dto.getClientId() != null) {
//            TbClientMaster client = new TbClientMaster();
//            client.setPkClientId(dto.getClientId());
//            role.setClient(client);
//        }



        TbRoleMaster saved = repo.save(role);
        return toDTO(saved);
    }

    // UPDATE ROLE
    public RoleMasterDTO updateRole(Long id, RoleMasterDTO dto) {
        TbRoleMaster role = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        role.setName(dto.getName());
        role.setParentRoleId(dto.getParentRoleId());
        role.setStatus(dto.getStatus());
        role.setDescription(dto.getDescription());
        role.setCreatedModifiedDate(LocalDateTime.now());

//        if (dto.getClientId() != null) {
//            TbClientMaster client = new TbClientMaster();
//            client.setPkClientId(dto.getClientId());
//            role.setClient(client);
//        }

        TbRoleMaster updated = repo.save(role);
        return toDTO(updated);
    }

    public List<RoleFormDTO> getFormsByRoleId(Long pk_role_id) {

        List<TbRoleDetails> roleDetailsList = roleDetailsRepository.findByRole_PkRoleId(pk_role_id);
        if (roleDetailsList.isEmpty()) {
            return Collections.emptyList();
        }

        return roleDetailsList.stream()
                .map(dtoService::convertRoleDetailToRoleFormDTO)
                .collect(Collectors.toList());
    }

}
