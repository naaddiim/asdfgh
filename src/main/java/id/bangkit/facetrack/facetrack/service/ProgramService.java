package id.bangkit.facetrack.facetrack.service;

import java.util.List;

import id.bangkit.facetrack.facetrack.dto.ProgramDTO;
import id.bangkit.facetrack.facetrack.dto.ProgramDetailDTO;
import id.bangkit.facetrack.facetrack.dto.request.CreateProgramRequest;

public interface ProgramService {

    ProgramDTO createProgram(CreateProgramRequest request);

    List<ProgramDTO> getAllProgram();

    ProgramDetailDTO getProgramById(int programId);

    ProgramDTO updateProgram(int programId);

    void checkUserAvailability();
}
