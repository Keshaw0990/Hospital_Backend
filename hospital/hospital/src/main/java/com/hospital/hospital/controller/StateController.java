package com.hospital.hospital.controller;

import com.hospital.hospital.dto.StateDTO;
import com.hospital.hospital.entity.TbState;
import com.hospital.hospital.service.StateService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/state")
@RequiredArgsConstructor
public class StateController {

    private final StateService service;

    // ADD STATE
    @PostMapping("/add")
    public ResponseEntity<TbState> addState(@RequestBody StateDTO dto) {
        return ResponseEntity.ok(service.addState(dto));
    }

    // UPDATE STATE
    @PutMapping("/update/{id}")
    public ResponseEntity<TbState> updateState(
            @PathVariable Long id,
            @RequestBody StateDTO dto) {

        return ResponseEntity.ok(service.updateState(id, dto));
    }

    // GET ALL STATES
    @GetMapping("/all")
    public ResponseEntity<List<TbState>> getAllStates() {
        return ResponseEntity.ok(service.getAllStates());
    }
}
