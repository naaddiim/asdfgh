package id.bangkit.facetrack.facetrack.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import id.bangkit.facetrack.facetrack.dto.ProgramDTO;
import id.bangkit.facetrack.facetrack.entity.Program;
import id.bangkit.facetrack.facetrack.mappers.MapTo;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProgramMapperImpl implements MapTo<Program, ProgramDTO> {
    private final ModelMapper modelMapper;

    @Override
    public ProgramDTO mapTo(Program program) {
        return modelMapper.map(program, ProgramDTO.class);
    }
    
}
