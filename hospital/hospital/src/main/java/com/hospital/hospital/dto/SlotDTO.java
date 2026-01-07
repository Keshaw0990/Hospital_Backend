package com.hospital.hospital.dto;

import lombok.Data;
import java.time.LocalTime;

@Data
public class SlotDTO {

    private Long pkSlotId;

    private Long doctorId;
    private String doctorName;

    private String slotName;

    private LocalTime startTime;
    private LocalTime endTime;

    private Integer capacity;
    private Integer seqNo;
    private Long clientId;
    private Boolean status;
}
