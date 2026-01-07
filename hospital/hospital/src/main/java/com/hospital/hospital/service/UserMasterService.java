package com.hospital.hospital.service;

import com.hospital.hospital.dto.UserMasterDTO;
import com.hospital.hospital.entity.TbClientMaster;
import com.hospital.hospital.entity.TbRoleMaster;
import com.hospital.hospital.entity.TbUserMaster;
import com.hospital.hospital.repo.ClientMasterRepository;
import com.hospital.hospital.repo.UserMasterRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserMasterService {

    private final UserMasterRepository userRepo;
    private final ClientMasterRepository clientRepo;

    // ============================================================
    // ENTITY → DTO
    // ============================================================
    private UserMasterDTO toDTO(TbUserMaster user) {
        UserMasterDTO dto = new UserMasterDTO();

        dto.setPkUserId(user.getPkUserId());
        dto.setName(user.getName());
        dto.setEmailId(user.getEmailId());
        dto.setMobileNo(user.getMobileNo());
        dto.setIsActive(user.getIsActive());
        dto.setPassword(user.getPassword());
        dto.setCreatedModifiedDate(user.getCreatedModifiedDate());
        dto.setReadOnly(user.getReadOnly());
        dto.setArchiveFlag(user.getArchiveFlag());

        // ROLE
        if (user.getRole() != null) {
            dto.setRoleId(user.getRole().getPkRoleId());
            dto.setRoleName(user.getRole().getName());
        }

        // CLIENT
        if (user.getClient() != null) {
            dto.setClientId(user.getClient().getPkClientId());
            dto.setOrgName(user.getClient().getOrgName());
        }

        return dto;
    }

    // ============================================================
    // GET ALL USERS
    // ============================================================
    public List<UserMasterDTO> getAllUsers() {
        return userRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // ============================================================
    // GET USER BY ID
    // ============================================================
    public UserMasterDTO getById(Long id) {
        TbUserMaster user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return toDTO(user);
    }

    // ============================================================
    // ADD USER  ✅ CLIENT COUNT VALIDATION ADDED
    // ============================================================
    public UserMasterDTO addUser(TbUserMaster user) {

        // ============================
        // ✅ CLIENT COUNT VALIDATION
        // ============================
        if (user.getClient() != null && user.getClient().getPkClientId() != null) {

            Long clientId = user.getClient().getPkClientId();

            TbClientMaster client = clientRepo.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("Client not found"));

            long existingUsers =
                    userRepo.countByClient_PkClientId(clientId);

            if (client.getClientCount() != null &&
                    existingUsers >= client.getClientCount()) {

                throw new RuntimeException(
                        "Hospital user limit is full. Cannot add more users."
                );
            }
        }

        // ============================
        // ⬇️ EXISTING LOGIC (UNCHANGED)
        // ============================

        // Attach only Client ID
        if (user.getClient() != null) {
            TbClientMaster client = new TbClientMaster();
            client.setPkClientId(user.getClient().getPkClientId());
            user.setClient(client);
        }

        // Attach only Role ID
        if (user.getRole() != null) {
            TbRoleMaster role = new TbRoleMaster();
            role.setPkRoleId(user.getRole().getPkRoleId());
            user.setRole(role);
        }

        // Internal fields
        user.setCreatedModifiedDate(LocalDateTime.now());
        user.setReadOnly("N");
        user.setArchiveFlag("F");

        TbUserMaster saved = userRepo.save(user);
        return toDTO(saved);
    }

    // ============================================================
    // UPDATE USER (UNCHANGED)
    // ============================================================
    public UserMasterDTO updateUser(Long id, TbUserMaster newUser) {

        TbUserMaster existing = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setName(newUser.getName());
        existing.setEmailId(newUser.getEmailId());
        existing.setMobileNo(newUser.getMobileNo());
        existing.setPassword(newUser.getPassword());
        existing.setIsActive(newUser.getIsActive());
        existing.setCreatedModifiedDate(LocalDateTime.now());

        if (newUser.getRole() != null) {
            TbRoleMaster role = new TbRoleMaster();
            role.setPkRoleId(newUser.getRole().getPkRoleId());
            existing.setRole(role);
        }

        if (newUser.getClient() != null) {
            TbClientMaster client = new TbClientMaster();
            client.setPkClientId(newUser.getClient().getPkClientId());
            existing.setClient(client);
        }

        TbUserMaster updated = userRepo.save(existing);
        return toDTO(updated);
    }

    // ============================================================
    // LOGIN (UNCHANGED)
    // ============================================================
    public UserMasterDTO login(String email, String password) {

        TbUserMaster user = userRepo.findByEmailIdAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!user.getIsActive()) {
            throw new RuntimeException("Account is inactive");
        }

        return toDTO(user);
    }
}
