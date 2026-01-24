package com.hospital.hospital.service;

import com.hospital.hospital.dto.*;
import com.hospital.hospital.entity.TbClientMaster;
import com.hospital.hospital.entity.TbRoleMaster;
import com.hospital.hospital.repo.ClientMasterRepository;
import com.hospital.hospital.repo.PatientRepository;
import com.hospital.hospital.repo.RoleMasterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientMasterService {

    private final ClientMasterRepository clientRepo;
    private final RoleMasterRepository roleRepo;
    private final PatientRepository patientRepo; // ✅ ADD THIS

    // ============================================================
    // ENTITY → DTO
    // ============================================================
    private ClientMasterDTO toDTO(TbClientMaster c) {

        ClientMasterDTO dto = new ClientMasterDTO();

        dto.setPkClientId(c.getPkClientId());
        dto.setOrgName(c.getOrgName());
        dto.setEmailId(c.getEmailId());
        dto.setMobileNo(c.getMobileNo());
        dto.setAddress(c.getAddress());
        dto.setStatus(c.getStatus());
        dto.setExpiryDate(c.getExpiryDate());
        dto.setClientCount(c.getClientCount());
        dto.setPatientCount(c.getPatientCount()); // ✅ JUST READ
        dto.setLogo(c.getLogo());
        dto.setCreatedModifiedDate(c.getCreatedModifiedDate());
        dto.setReadOnly(c.getReadOnly());
        dto.setArchiveFlag(c.getArchiveFlag());
        dto.setPassword(c.getPassword());

        if (c.getRole() != null) {
            dto.setRoleId(c.getRole().getPkRoleId());
            dto.setRoleName(c.getRole().getName());
        }

        return dto;
    }


    // ============================================================
    // GET ALL CLIENTS
    // ============================================================
    public List<ClientMasterDTO> getAllClients() {
        return clientRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ============================================================
    // GET CLIENT BY ID
    // ============================================================
    public ClientMasterDTO getClientById(Long id) {
        TbClientMaster client = clientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
        return toDTO(client);
    }

    // ============================================================
    // ADD CLIENT  ✅ (NO TRANSIENT ERRORS)
    // ============================================================
    public ClientMasterDTOAdd addClient(ClientMasterDTOAdd dto) {

        TbClientMaster client = new TbClientMaster();

        client.setOrgName(dto.getOrgName());
        client.setEmailId(dto.getEmailId());
        client.setMobileNo(dto.getMobileNo());
        client.setPassword(dto.getPassword());
        client.setAddress(dto.getAddress());
        client.setStatus(dto.getStatus());
        client.setExpiryDate(dto.getExpiryDate());
        client.setClientCount(dto.getClientCount());
        client.setLogo(dto.getLogo());

        client.setPatientCount(dto.getPatientCount());

        client.setCreatedModifiedDate(LocalDateTime.now());
        client.setReadOnly("N");
        client.setArchiveFlag("F");

        // ✅ FETCH ROLE (MANDATORY)
        TbRoleMaster role = roleRepo.findById(dto.getRoleId())
                .orElseThrow(() ->
                        new RuntimeException("Invalid roleId: " + dto.getRoleId())
                );

        client.setRole(role);

        clientRepo.save(client);
        return dto;
    }

    // ============================================================
    // UPDATE CLIENT
    // ============================================================
    public ClientMasterDTOAdd updateClient(Long id, ClientMasterDTOAdd dto) {

        TbClientMaster client = clientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));

        client.setOrgName(dto.getOrgName());
        client.setEmailId(dto.getEmailId());
        client.setMobileNo(dto.getMobileNo());
        client.setPassword(dto.getPassword());
        client.setAddress(dto.getAddress());
        client.setStatus(dto.getStatus());
        client.setExpiryDate(dto.getExpiryDate());
        client.setClientCount(dto.getClientCount());
        client.setLogo(dto.getLogo());
        client.setCreatedModifiedDate(LocalDateTime.now());
        client.setPatientCount(dto.getPatientCount());

        // 🔁 Update role if provided
        if (dto.getRoleId() != null) {
            TbRoleMaster role = roleRepo.findById(dto.getRoleId())
                    .orElseThrow(() ->
                            new RuntimeException("Invalid roleId: " + dto.getRoleId())
                    );
            client.setRole(role);
        }

        clientRepo.save(client);
        return dto;
    }

    // ============================================================
    // CLIENT LOGIN
    // ============================================================
    public ClientLoginResponse login(ClientLoginRequest req) {

        TbClientMaster client = clientRepo.findByEmailIdAndPassword(
                        req.getEmailId(), req.getPassword()
                )
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (client.getStatus() == 0) {
            throw new RuntimeException("Account is inactive");
        }

        ClientLoginResponse res = new ClientLoginResponse();
        res.setClientId(client.getPkClientId());
        res.setOrgName(client.getOrgName());
        res.setRoleId(
                client.getRole() != null ? client.getRole().getPkRoleId() : null
        );

        return res;
    }

    // ============================================================
    // UPDATE PASSWORD
    // ============================================================
    public void updatePassword(Long id, ClientPasswordUpdateRequest req) {

        TbClientMaster client = clientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        client.setPassword(req.getNewPassword());
        client.setCreatedModifiedDate(LocalDateTime.now());

        clientRepo.save(client);
    }
}
