package com.hospital.hospital.controller;

import com.hospital.hospital.dto.RoleDetailsDTO;
import com.hospital.hospital.service.RoleDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/role-details")
@RequiredArgsConstructor
public class RoleDetailsController {

    private final RoleDetailsService service;

    @GetMapping("/all")
    public ResponseEntity<List<RoleDetailsDTO>> getAll() {
        return ResponseEntity.ok(service.getAllRoleDetails());
    }

    @GetMapping("/role/{roleId}")
    public ResponseEntity<List<RoleDetailsDTO>> getByRoleId(@PathVariable Long roleId) {
        return ResponseEntity.ok(service.getByRoleId(roleId));
    }

    @PostMapping("/add")
    public ResponseEntity<RoleDetailsDTO> add(@RequestBody RoleDetailsDTO dto) {
        return ResponseEntity.ok(service.addRoleDetails(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoleDetailsDTO> update(@PathVariable Long id,
                                                 @RequestBody RoleDetailsDTO dto) {
        return ResponseEntity.ok(service.updateRoleDetails(id, dto));
    }
}

