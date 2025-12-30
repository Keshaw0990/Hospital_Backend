package com.hospital.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_doctor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbDoctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_doctor_id")
    private Long pkDoctorId;

    @Column(name = "col_full_name")
    private String fullName;

    @Column(name = "col_phone")
    private String phone;

    @Column(name = "col_specialty")
    private String specialty;

    @Column(name = "col_consultation_duration")
    private Integer consultationDuration;

    @ManyToOne
    @JoinColumn(name = "col_department_id")
    private TbDepartment department;
}
