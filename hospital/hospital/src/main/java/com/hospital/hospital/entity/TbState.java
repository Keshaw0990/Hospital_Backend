package com.hospital.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_state")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TbState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_state_id")
    private Long stateId;

    @Column(name = "col_state_name")
    private String stateName;

    @Column(name = "col_status")
    private Boolean status;
}
