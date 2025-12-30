package com.hospital.hospital.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalTime;

@Entity
@Table(name = "tb_slot", indexes = {
        @Index(name = "idx_slot_doctor", columnList = "col_doctor_id"),
        @Index(name = "idx_slot_seq", columnList = "col_seq_no")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TbSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_slot_id")
    private Long pkSlotId;

    // link to doctor — use LAZY to avoid unnecessary eager loads in lists
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "col_doctor_id", nullable = false)
    private TbDoctor doctor;

    /**
     * If this is a day code (1..7) consider Integer.
     * If it's a label like "Morning Slot", keep String.
     */
    @Column(name = "col_slot_name", length = 80, nullable = false)
    private String slotName;

    // Optional denormalized copy of doctor name (snapshot). Use only if needed.
    @Column(name = "col_doctor_name", length = 120)
    private String doctorName;

    @Column(name = "col_start_time")
    private LocalTime startTime;

    @Column(name = "col_end_time")
    private LocalTime endTime;

    @Column(name = "col_capacity")
    private Integer capacity;

    @Column(name = "col_seq_no")
    private Integer seqNo;

    @Column(name = "col_status", nullable = false)
    private Boolean status = Boolean.TRUE;


}
