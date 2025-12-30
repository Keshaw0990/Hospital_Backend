package com.hospital.hospital.controller;

import com.hospital.hospital.dto.RoleFormDTO;
import com.hospital.hospital.dto.RoleMasterDTO;
import com.hospital.hospital.service.RoleMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RoleMasterController {

    private final RoleMasterService service;

    @GetMapping("/all")
    public ResponseEntity<List<RoleMasterDTO>> getAllRoles() {
        return ResponseEntity.ok(service.getAllRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoleMasterDTO> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getRoleById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<RoleMasterDTO> addRole(@RequestBody RoleMasterDTO dto) {
        return ResponseEntity.ok(service.addRole(dto));
    }

    @GetMapping("/{roleId}/forms")
    public ResponseEntity<List<RoleFormDTO>> getFormsByRole(@PathVariable("roleId") Long pk_role_id) {
        System.out.println("APi Heat.....");
        List<RoleFormDTO> formList = service.getFormsByRoleId(pk_role_id);
        return ResponseEntity.ok(formList);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<RoleMasterDTO> updateRole(@PathVariable Long id,
                                                    @RequestBody RoleMasterDTO dto) {
        return ResponseEntity.ok(service.updateRole(id, dto));
    }
}
