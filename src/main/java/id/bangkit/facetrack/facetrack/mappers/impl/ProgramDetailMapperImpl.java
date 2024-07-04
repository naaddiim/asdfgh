package id.bangkit.facetrack.facetrack.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import id.bangkit.facetrack.facetrack.dto.ProgramDetailDTO;
import id.bangkit.facetrack.facetrack.entity.Program;
import id.bangkit.facetrack.facetrack.mappers.Mapper;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProgramDetailMapperImpl implements Mapper<Program, ProgramDetailDTO> {
    private final ModelMapper modelMapper;

    @Override
    public ProgramDetailDTO mapTo(Program program) {
        return modelMapper.map(program, ProgramDetailDTO.class);
    }

    @Override
    public Program mapFrom(ProgramDetailDTO programDetailDTO) {
        return modelMapper.map(programDetailDTO, Program.class);
    }
    
}
