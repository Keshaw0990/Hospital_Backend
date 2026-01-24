package com.hospital.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_medicine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medicine_id")
    private Long medicineId;

    @Column(name = "medicine_name", nullable = false)
    private String medicineName;

    // MANY medicines belong to ONE doctor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_doctor_id", nullable = false)
    private TbDoctor doctor;

    @Column(name = "col_unit")   // ✅ REQUIRED
    private String unit;
}
