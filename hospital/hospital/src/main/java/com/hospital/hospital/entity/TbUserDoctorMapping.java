package com.hospital.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_user_doctor_mapping")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbUserDoctorMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_mapping_id")
    private Long pkMappingId;

    @ManyToOne
    @JoinColumn(name = "col_user_id", nullable = false)
    private TbUserMaster user;

    @ManyToOne
    @JoinColumn(name = "col_doctor_id", nullable = false)
    private TbDoctor doctor;

    private Boolean status;

}
