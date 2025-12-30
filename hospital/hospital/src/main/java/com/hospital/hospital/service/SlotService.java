package com.hospital.hospital.service;

import com.hospital.hospital.dto.SlotDTO;
import com.hospital.hospital.entity.TbDoctor;
import com.hospital.hospital.entity.TbSlot;
import com.hospital.hospital.repo.BookingRepository;
import com.hospital.hospital.repo.DoctorRepository;
import com.hospital.hospital.repo.SlotRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SlotService {

    private final SlotRepository slotRepo;
    private final DoctorRepository doctorRepo;

    private final BookingRepository bookingRepo;


    // Convert Entity → DTO
    private SlotDTO toDTO(TbSlot slot) {
        SlotDTO dto = new SlotDTO();

        dto.setPkSlotId(slot.getPkSlotId());
        dto.setSlotName(slot.getSlotName());
        dto.setStartTime(slot.getStartTime());
        dto.setEndTime(slot.getEndTime());
        dto.setCapacity(slot.getCapacity());
        dto.setSeqNo(slot.getSeqNo());
        dto.setStatus(slot.getStatus());

        if (slot.getDoctor() != null) {
            dto.setDoctorId(slot.getDoctor().getPkDoctorId());
            dto.setDoctorName(slot.getDoctor().getFullName());
        }

        return dto;
    }

    // Add Slot
    public SlotDTO addSlot(SlotDTO dto) {

        TbDoctor doctor = doctorRepo.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        TbSlot slot = TbSlot.builder()
                .slotName(dto.getSlotName())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .capacity(dto.getCapacity())
                .seqNo(dto.getSeqNo())
                .status(dto.getStatus())
                .doctor(doctor)
                .doctorName(doctor.getFullName()) // snapshot
                .build();

        return toDTO(slotRepo.save(slot));
    }

    // Update Slot
    public SlotDTO updateSlot(Long id, SlotDTO dto) {

        TbSlot slot = slotRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Slot not found"));

        slot.setSlotName(dto.getSlotName());
        slot.setStartTime(dto.getStartTime());
        slot.setEndTime(dto.getEndTime());
        slot.setCapacity(dto.getCapacity());
        slot.setSeqNo(dto.getSeqNo());
        slot.setStatus(dto.getStatus());

        TbDoctor doctor = doctorRepo.findById(dto.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        slot.setDoctor(doctor);
        slot.setDoctorName(doctor.getFullName());

        return toDTO(slotRepo.save(slot));
    }

    // Get All
    public List<SlotDTO> getAll() {
        return slotRepo.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Delete Slot
    public void deleteSlot(Long id) {
        slotRepo.deleteById(id);
    }


    public List<SlotDTO> getAvailableSlotsByDoctorAndDate(Long doctorId, LocalDate date) {

        return slotRepo.findByDoctor_PkDoctorIdAndStatusTrue(doctorId)
                .stream()
                .filter(slot -> {
                    long bookedCount =
                            bookingRepo.countBySlot_PkSlotIdAndBookingDate(
                                    slot.getPkSlotId(), date
                            );

                    // ✅ SKIP SLOT IF FULL FOR THAT DATE
                    return bookedCount < slot.getCapacity();
                })
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

}
