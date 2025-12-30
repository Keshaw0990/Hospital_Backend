package com.hospital.hospital.service;

import com.hospital.hospital.dto.StateDTO;
import com.hospital.hospital.entity.TbState;
import com.hospital.hospital.repo.StateRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateService {

    private final StateRepository repo;

    // ADD STATE
    public TbState addState(StateDTO dto) {
        TbState state = TbState.builder()
                .stateName(dto.getStateName())
                .status(dto.getStatus())
                .build();

        return repo.save(state);
    }

    // UPDATE STATE
    public TbState updateState(Long id, StateDTO dto) {
        TbState state = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("State not found"));

        state.setStateName(dto.getStateName());
        state.setStatus(dto.getStatus());

        return repo.save(state);
    }

    // GET ALL STATES
    public List<TbState> getAllStates() {
        return repo.findAll();
    }
}
