package com.hospital.hospital.service;

import com.hospital.hospital.dto.RoleDetailsDTO;
import com.hospital.hospital.dto.RoleDetailsMappingDTO;
import com.hospital.hospital.dto.RoleMappingResponseDTO;
import com.hospital.hospital.entity.*;
import com.hospital.hospital.repo.RoleDetailsRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleDetailsService {

    private final RoleDetailsRepository repo;

    private RoleDetailsDTO toDTO(TbRoleDetails rd) {

        RoleDetailsDTO dto = new RoleDetailsDTO();

        dto.setPkRoleDetailsId(rd.getPkRoleDetailsId());
        dto.setSeqNo(rd.getSeqNo());
        dto.setIsAllowed(rd.getIsAllowed());
        dto.setShowInMenu(rd.getShowInMenu());
        dto.setReadOnly(rd.getReadOnly());
        dto.setArchiveFlag(rd.getArchiveFlag());
        dto.setCreatedModifiedDate(rd.getCreatedModifiedDate());
//        dto.setClientId(rd.getClient().getPkClientId());

        // ROLE INFO
        if (rd.getRole() != null) {
            dto.setRoleId(rd.getRole().getPkRoleId());
            dto.setRoleName(rd.getRole().getName());     // ⭐ ADD THIS
        }

        // FORM INFO
        if (rd.getForm() != null) {
            dto.setFormId(rd.getForm().getPkFormId());
            dto.setFormName(rd.getForm().getName());     // ⭐ ADD THIS
            dto.setFormLink(rd.getForm().getLink());     // ⭐ ADD THIS
        }

        return dto;
    }


    public List<RoleDetailsDTO> getAllRoleDetails() {
        return repo.findAll().stream().map(this::toDTO).collect(Collectors.toList());
    }

    public List<RoleDetailsDTO> getByRoleId(Long roleId) {
        return repo.findByRole_PkRoleId(roleId).stream().map(this::toDTO).collect(Collectors.toList());
    }

    public RoleDetailsDTO addRoleDetails(RoleDetailsDTO dto) {

//        if (repo.existsByRole_PkRoleIdAndForm_PkFormIdAndClient_PkClientId(
//                dto.getRoleId(), dto.getFormId(), dto.getClientId())) {
//            throw new RuntimeException("Mapping already exists!");
//        }

        TbRoleDetails rd = new TbRoleDetails();

        rd.setSeqNo(dto.getSeqNo());
        rd.setIsAllowed(dto.getIsAllowed());
        rd.setShowInMenu(dto.getShowInMenu());
        rd.setCreatedModifiedDate(LocalDateTime.now());
        rd.setReadOnly("N");
        rd.setArchiveFlag("F");

        TbRoleMaster role = new TbRoleMaster();
        role.setPkRoleId(dto.getRoleId());
        rd.setRole(role);

        TbFormMaster form = new TbFormMaster();
        form.setPkFormId(dto.getFormId());
        rd.setForm(form);

        TbClientMaster client = new TbClientMaster();
        client.setPkClientId(dto.getClientId());
//        rd.setClient(client);

        return toDTO(repo.save(rd));
    }

    public RoleDetailsDTO updateRoleDetails(Long id, RoleDetailsDTO dto) {

        TbRoleDetails rd = repo.findById(id).orElseThrow(() -> new RuntimeException("Not found"));

        rd.setSeqNo(dto.getSeqNo());
        rd.setIsAllowed(dto.getIsAllowed());
        rd.setShowInMenu(dto.getShowInMenu());
        rd.setCreatedModifiedDate(LocalDateTime.now());

        TbRoleMaster role = new TbRoleMaster();
        role.setPkRoleId(dto.getRoleId());
        rd.setRole(role);

        TbFormMaster form = new TbFormMaster();
        form.setPkFormId(dto.getFormId());
        rd.setForm(form);

        TbClientMaster client = new TbClientMaster();
        client.setPkClientId(dto.getClientId());
//        rd.setClient(client);

        return toDTO(repo.save(rd));
    }

    // 🔥 ROLE–FORM MAPPING API
    public RoleMappingResponseDTO getRoleFormMapping(Long roleId) {

        List<TbRoleDetails> list = repo.findMappingByRoleId(roleId);

        if (list.isEmpty()) {
            throw new RuntimeException("No mapping found!");
        }

        RoleMappingResponseDTO response = new RoleMappingResponseDTO();
        response.setRoleId(roleId);
        response.setRoleName(list.get(0).getRole().getName());

        List<RoleDetailsMappingDTO> forms = list.stream().map(rd -> {
            RoleDetailsMappingDTO dto = new RoleDetailsMappingDTO();
            dto.setFormId(rd.getForm().getPkFormId());
            dto.setFormName(rd.getForm().getName());
            dto.setSeqNo(rd.getSeqNo());
            dto.setIsAllowed(rd.getIsAllowed());
            dto.setShowInMenu(rd.getShowInMenu());
            return dto;
        }).collect(Collectors.toList());

        response.setForms(forms);

        return response;
    }
}
