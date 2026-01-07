package com.hospital.hospital.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(
        name = "tb_patient",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_patient_phone_client",
                        columnNames = { "col_phone", "col_client_id" }
                )
        }
)
@Data
public class TbPatient {

    // =========================
    // PRIMARY KEY
    // =========================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_patient_id")
    private Long patientId;

    // =========================
    // PATIENT DETAILS
    // =========================
    @Column(name = "col_full_name", nullable = false)
    private String fullName;

    // 🔒 UNIQUE PER CLIENT
    @Column(name = "col_phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "col_gender", length = 10)
    private String gender;

    @Column(name = "col_dob")
    private LocalDate dob;

    @Column(name = "col_address", length = 255)
    private String address;

    @Column(name = "col_status")
    private Boolean status = true;

    // =========================
    // CLIENT & STATE
    // =========================
    @Column(name = "col_state_id")
    private Long stateId;

    @Column(name = "col_client_id", nullable = false)
    private Long clientId;



}
