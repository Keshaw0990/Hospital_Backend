package com.hospital.hospital.controller;

import com.hospital.hospital.dto.*;
import com.hospital.hospital.service.ClientMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientMasterController {

    private final ClientMasterService service;

    @GetMapping("/all")
    public ResponseEntity<List<ClientMasterDTO>> getAll() {
        return ResponseEntity.ok(service.getAllClients());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientMasterDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.getClientById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<ClientMasterDTOAdd> add(@RequestBody ClientMasterDTOAdd dto) {
        return ResponseEntity.ok(service.addClient(dto));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClientMasterDTOAdd> update(@PathVariable Long id,
                                                     @RequestBody ClientMasterDTOAdd dto) {
        return ResponseEntity.ok(service.updateClient(id, dto));
    }

    @PostMapping("/login")
    public ResponseEntity<ClientLoginResponse> login(@RequestBody ClientLoginRequest req) {
        return ResponseEntity.ok(service.login(req));
    }

    @PatchMapping("/update-password/{id}")
    public ResponseEntity<String> updatePassword(@PathVariable Long id,
                                                 @RequestBody ClientPasswordUpdateRequest req) {

        service.updatePassword(id, req);
        return ResponseEntity.ok("Password updated successfully");
    }
}
