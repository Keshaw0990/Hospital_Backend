package com.hospital.hospital.controller;

import com.hospital.hospital.dto.DepartmentDTO;
import com.hospital.hospital.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService service;

    // ADD
    @PostMapping("/add")
    public ResponseEntity<DepartmentDTO> addDepartment(@RequestBody DepartmentDTO dto) {
        return ResponseEntity.ok(service.addDepartment(dto));
    }

    // UPDATE
    @PutMapping("/update/{id}")
    public ResponseEntity<DepartmentDTO> updateDepartment(
            @PathVariable Long id,
            @RequestBody DepartmentDTO dto) {
        return ResponseEntity.ok(service.updateDepartment(id, dto));
    }

    // DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDepartment(@PathVariable Long id) {
        return ResponseEntity.ok(service.deleteDepartment(id));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DepartmentDTO> getDepartmentById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getDepartmentById(id));
    }

    // GET ALL
    @GetMapping("/all")
    public ResponseEntity<List<DepartmentDTO>> getAll() {
        return ResponseEntity.ok(service.getAllDepartments());
    }

    // GET BY CLIENT
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<DepartmentDTO>> getByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(service.getDepartmentsByClient(clientId));
    }
}
