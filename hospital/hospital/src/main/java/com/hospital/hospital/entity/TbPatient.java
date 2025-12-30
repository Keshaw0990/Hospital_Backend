package com.hospital.hospital.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "tb_patient")
@Data
public class TbPatient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_patient_id")
    private Long patientId;

    @Column(name = "col_full_name")
    private String fullName;

    @Column(name = "col_phone",unique = true)

    private String phone;

    @Column(name = "col_gender")
    private String gender;

    @Column(name = "col_dob")
    private LocalDate dob;

    @Column(name = "col_address")
    private String address;

    @Column(name = "col_status")
    private Boolean status;

    // ✅ NEW COLUMN
    @Column(name = "col_state_id")
    private Long stateId;
}
