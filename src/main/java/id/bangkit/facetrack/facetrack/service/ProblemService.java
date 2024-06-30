package id.bangkit.facetrack.facetrack.service;

import java.util.List;

import id.bangkit.facetrack.facetrack.dto.ProblemDTO;

public interface ProblemService {
    List<ProblemDTO> findAllProblem();
}
