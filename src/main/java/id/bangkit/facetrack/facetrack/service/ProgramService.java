package id.bangkit.facetrack.facetrack.service;

import java.util.List;

import id.bangkit.facetrack.facetrack.dto.request.CreateProgramRequest;
import id.bangkit.facetrack.facetrack.entity.Program;

public interface ProgramService {

    Program createProgram(CreateProgramRequest request);

    List<Program> getAllProgram();

    Program getProgramById(int programId);

    Program updateProgram(int programId);

    void checkUserAvailability();
}
