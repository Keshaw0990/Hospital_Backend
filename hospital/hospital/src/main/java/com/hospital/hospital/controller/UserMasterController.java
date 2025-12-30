package com.hospital.hospital.controller;

import com.hospital.hospital.dto.UserMasterDTO;
import com.hospital.hospital.dto.UserLoginRequestDTO;
import com.hospital.hospital.entity.TbUserMaster;
import com.hospital.hospital.service.UserMasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserMasterController {

    private final UserMasterService userService;

    @GetMapping("/all")
    public ResponseEntity<List<UserMasterDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserMasterDTO> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<UserMasterDTO> addUser(@RequestBody TbUserMaster user) {
        return ResponseEntity.ok(userService.addUser(user));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserMasterDTO> updateUser(@PathVariable Long id,
                                                    @RequestBody TbUserMaster user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    // ⭐ UPDATED LOGIN METHOD (NO LOGIC CHANGED)

    @PostMapping("/auth/login")
    public ResponseEntity<UserMasterDTO> login(@RequestBody UserLoginRequestDTO request) {
        System.out.println("login request");
        return ResponseEntity.ok(
                userService.login(request.getEmailId(), request.getPassword())
        );
    }

}
