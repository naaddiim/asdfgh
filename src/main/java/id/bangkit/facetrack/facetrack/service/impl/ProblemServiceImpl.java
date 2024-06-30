package id.bangkit.facetrack.facetrack.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import id.bangkit.facetrack.facetrack.dto.ProblemDTO;
import id.bangkit.facetrack.facetrack.entity.Problem;
import id.bangkit.facetrack.facetrack.mappers.Mapper;
import id.bangkit.facetrack.facetrack.repository.ProblemRepository;
import id.bangkit.facetrack.facetrack.service.ProblemService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProblemServiceImpl implements ProblemService {
    private final ProblemRepository problemRepository;
    private final Mapper<Problem, ProblemDTO> problemMapper;

    @Override
    public List<ProblemDTO> findAllProblem() {
        return problemRepository.findAll().stream().map(problemMapper::mapTo).toList();
    }

}
