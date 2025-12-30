package com.hospital.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_department")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_department_id")
    private Long pkDepartmentId;

    @Column(name = "col_name", nullable = false)
    private String name;

    // Correct mapping — many departments belong to one client
    @ManyToOne
    @JoinColumn(name = "col_client_id", referencedColumnName = "pk_client_id")
    private TbClientMaster client;
}
