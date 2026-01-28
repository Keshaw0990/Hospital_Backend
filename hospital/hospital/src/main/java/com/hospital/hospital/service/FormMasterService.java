package com.hospital.hospital.service;

import com.hospital.hospital.dto.FormMasterDTO;
import com.hospital.hospital.entity.TbClientMaster;
import com.hospital.hospital.entity.TbFormMaster;
import com.hospital.hospital.repo.FormMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormMasterService {

    private final FormMasterRepository formRepo;

    /* -----------------------------
       Convert Entity → DTO
    ------------------------------ */
    private FormMasterDTO toDTO(TbFormMaster f) {

        FormMasterDTO dto = new FormMasterDTO();

        dto.setPkFormId(f.getPkFormId());
        dto.setName(f.getName());
        dto.setParentId(f.getParentId());
        dto.setLink(f.getLink());
//        dto.setClientId(f.getClient().getPkClientId());
        dto.setArchiveFlag(f.getArchiveFlag());
        dto.setReadOnly(f.getReadOnly());

        return dto;
    }

    /* -----------------------------
       GET ALL
    ------------------------------ */
    public List<FormMasterDTO> getAllForms() {
        return formRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    /* -----------------------------
       GET BY ID
    ------------------------------ */
    public FormMasterDTO getFormById(Long id) {
        TbFormMaster form = formRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Form not found"));
        return toDTO(form);
    }

    /* -----------------------------
       ADD FORM
    ------------------------------ */
    public FormMasterDTO addForm(FormMasterDTO dto) {

        TbFormMaster form = new TbFormMaster();

        form.setName(dto.getName());
        form.setParentId(dto.getParentId());
        form.setLink(dto.getLink());
        form.setCreatedModifiedDate(LocalDateTime.now());

        // NEW REQUIRED FIELDS
        form.setArchiveFlag(dto.getArchiveFlag());
        form.setReadOnly(dto.getReadOnly());

        TbClientMaster client = new TbClientMaster();
        client.setPkClientId(dto.getClientId());
//        form.setClient(client);

        TbFormMaster saved = formRepo.save(form);
        return toDTO(saved);
    }

    /* -----------------------------
       UPDATE FORM
    ------------------------------ */
    public FormMasterDTO updateForm(Long id, FormMasterDTO dto) {

        TbFormMaster form = formRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Form not found"));

        form.setName(dto.getName());
        form.setParentId(dto.getParentId());
        form.setLink(dto.getLink());
        form.setCreatedModifiedDate(LocalDateTime.now());

        // NEW REQUIRED FIELDS
        form.setArchiveFlag(dto.getArchiveFlag());
        form.setReadOnly(dto.getReadOnly());

        TbClientMaster client = new TbClientMaster();
        client.setPkClientId(dto.getClientId());
//        form.setClient(client);

        TbFormMaster updated = formRepo.save(form);
        return toDTO(updated);
    }
}
