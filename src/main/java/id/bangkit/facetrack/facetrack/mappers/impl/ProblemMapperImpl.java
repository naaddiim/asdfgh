package id.bangkit.facetrack.facetrack.mappers.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import id.bangkit.facetrack.facetrack.dto.ProblemDTO;
import id.bangkit.facetrack.facetrack.entity.Problem;
import id.bangkit.facetrack.facetrack.mappers.MapTo;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProblemMapperImpl implements MapTo<Problem, ProblemDTO> {
    private final ModelMapper modelMapper;

    @Override
    public ProblemDTO mapTo(Problem problem) {
        return modelMapper.map(problem, ProblemDTO.class);
    }

}
