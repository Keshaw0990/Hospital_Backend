package com.hospital.hospital.controller;

import com.hospital.hospital.dto.FormMasterDTO;
import com.hospital.hospital.service.FormMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forms")
@RequiredArgsConstructor
public class FormMasterController {

    private final FormMasterService formService;

    @GetMapping("/all")
    public ResponseEntity<List<FormMasterDTO>> getAllForms() {
        return ResponseEntity.ok(formService.getAllForms());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormMasterDTO> getFormById(@PathVariable Long id) {
        return ResponseEntity.ok(formService.getFormById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<FormMasterDTO> addForm(@RequestBody FormMasterDTO dto) {
        return ResponseEntity.ok(formService.addForm(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FormMasterDTO> updateForm(
            @PathVariable Long id,
            @RequestBody FormMasterDTO dto) {
        return ResponseEntity.ok(formService.updateForm(id, dto));
    }
}
