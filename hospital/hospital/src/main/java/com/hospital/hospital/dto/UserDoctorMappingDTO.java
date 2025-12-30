package com.hospital.hospital.dto;

import lombok.Data;
import java.util.List;

@Data
public class UserDoctorMappingDTO {
    private Long userId;             // which user?
    private List<Long> doctorIds;    // selected doctors list
}
