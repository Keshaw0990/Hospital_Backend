package com.hospital.hospital.service;

import com.hospital.hospital.dto.DepartmentDTO;
import com.hospital.hospital.entity.TbClientMaster;
import com.hospital.hospital.entity.TbDepartment;
import com.hospital.hospital.repo.ClientMasterRepository;
import com.hospital.hospital.repo.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository deptRepo;
    private final ClientMasterRepository clientRepo;

    // Convert Entity → DTO
    private DepartmentDTO toDTO(TbDepartment d) {
        if (d.getClient() == null) {
            return null; // skip invalid row
        }

        DepartmentDTO dto = new DepartmentDTO();
        dto.setDepartmentId(d.getPkDepartmentId());
        dto.setName(d.getName());
        dto.setClientId(d.getClient().getPkClientId());
        dto.setClientName(d.getClient().getOrgName());
        return dto;
    }



    // Convert DTO → Entity for CREATE or UPDATE
    private TbDepartment toEntity(DepartmentDTO dto, TbClientMaster client) {
        return TbDepartment.builder()
                .pkDepartmentId(dto.getDepartmentId())
                .name(dto.getName())
                .client(client)
                .build();
    }

    // ADD
    public DepartmentDTO addDepartment(DepartmentDTO dto) {
        TbClientMaster client = clientRepo.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        TbDepartment department = toEntity(dto, client);
        TbDepartment saved = deptRepo.save(department);

        return toDTO(saved);
    }

    // UPDATE
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO dto) {
        TbDepartment existing = deptRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));

        // If clientId is changed or reassign department to another client
        TbClientMaster client = clientRepo.findById(dto.getClientId())
                .orElseThrow(() -> new RuntimeException("Client not found"));

        existing.setName(dto.getName());
        existing.setClient(client);

        TbDepartment updated = deptRepo.save(existing);
        return toDTO(updated);
    }

    // DELETE
    public String deleteDepartment(Long id) {
        TbDepartment dept = deptRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        deptRepo.delete(dept);
        return "Department deleted successfully";
    }

    // GET BY ID
    public DepartmentDTO getDepartmentById(Long id) {
        TbDepartment dept = deptRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
        return toDTO(dept);
    }

    // GET ALL
    public List<DepartmentDTO> getAllDepartments() {
        return deptRepo.findAll()
                .stream()
                .map(this::toDTO)
                .filter(java.util.Objects::nonNull)
                .collect(Collectors.toList());
    }


    // GET BY CLIENT
    public List<DepartmentDTO> getDepartmentsByClient(Long clientId) {
        return deptRepo.findByClient_PkClientId(clientId)
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
