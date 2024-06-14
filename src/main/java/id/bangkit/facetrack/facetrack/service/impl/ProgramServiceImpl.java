package id.bangkit.facetrack.facetrack.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.bangkit.facetrack.facetrack.entity.User;
import id.bangkit.facetrack.facetrack.exception.ProgramNotFoundException;
import id.bangkit.facetrack.facetrack.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import id.bangkit.facetrack.facetrack.dto.CreateProgramRequest;
import id.bangkit.facetrack.facetrack.entity.Program;
import id.bangkit.facetrack.facetrack.entity.Skincare;
import id.bangkit.facetrack.facetrack.repository.ProgramRepository;
import id.bangkit.facetrack.facetrack.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final UserRepository userRepository;

    @Override
    public Program createProgram(CreateProgramRequest request) {
        Program newProgram = Program.builder().namaProgram(request.namaProgram()).build();
        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Skincare> skincares = new ArrayList<>();
        request.skincare().stream().forEach(element -> {
            Skincare skincare = Skincare.builder().nama(element.getNama()).build();
            skincare.setProgram(newProgram);
            skincares.add(skincare);
        });
        newProgram.setSkincare(skincares);
        newProgram.setUser(currentUser);
        return programRepository.save(newProgram);
    }

    @Override
    public List<Program> getAllProgram() {
        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return programRepository.findByUserOrderByCreatedAtAsc(currentUser);
    }

    @Override
    public Program getProgramById(int programId) {
        return programRepository.findById(programId).orElseThrow(
                () -> new ProgramNotFoundException("program not found")
        );
    }

    @Override
    public Program updateProgram(int programId) {
        Program program = programRepository.findById(programId).orElseThrow(
                () -> new ProgramNotFoundException("Program not found")
        );
        program.setActive(false);
        program.setDone(true);
        program.setUpdatedAt(new Date());
        program.setDoneAt(new Date());
        return programRepository.save(program);
    }

    @Override
    public boolean checkUserAvailability() {
        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Program> list = programRepository.findByUserOrderByCreatedAtAsc(currentUser).stream()
                .filter(element -> element.isActive() == true)
                .toList();
        return list.size() == 0;
    }

}
