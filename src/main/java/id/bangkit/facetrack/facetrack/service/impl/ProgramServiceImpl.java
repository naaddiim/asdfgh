package id.bangkit.facetrack.facetrack.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import id.bangkit.facetrack.facetrack.entity.User;
import id.bangkit.facetrack.facetrack.exception.ProgramNotFoundException;
import id.bangkit.facetrack.facetrack.exception.UnauthorizedNewProgramException;
import id.bangkit.facetrack.facetrack.mappers.MapTo;
import id.bangkit.facetrack.facetrack.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import id.bangkit.facetrack.facetrack.dto.ProgramDTO;
import id.bangkit.facetrack.facetrack.dto.ProgramDetailDTO;
import id.bangkit.facetrack.facetrack.dto.request.programs.CreateProgramRequest;
import id.bangkit.facetrack.facetrack.entity.Program;
import id.bangkit.facetrack.facetrack.entity.Skincare;
import id.bangkit.facetrack.facetrack.repository.ProgramRepository;
import id.bangkit.facetrack.facetrack.service.ProgramService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {
    private final ProgramRepository programRepository;
    private final UserRepository userRepository;
    private final MapTo<Program, ProgramDTO> programMapper;
    private final MapTo<Program, ProgramDetailDTO> programDetailMapper;

    @Override
    public ProgramDTO createProgram(CreateProgramRequest request) {
        Program newProgram = Program.builder().namaProgram(request.namaProgram()).build();
        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Skincare> skincares = new ArrayList<>();
        request.skincare().stream().forEach(element -> {
            Skincare skincare = Skincare.builder().nama(element.getNama()).build();
            skincare.setProgram(newProgram);
            skincares.add(skincare);
        });
        newProgram.setSkincares(skincares);
        newProgram.setUser(currentUser);
        return programMapper.mapTo(programRepository.save(newProgram));
    }

    @Override
    public List<ProgramDTO> getAllProgram() {
        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        return programRepository.findByUserOrderByCreatedAtAsc(currentUser).stream()
                .map(programMapper::mapTo)
                .toList();
    }

    @Override
    public ProgramDetailDTO getProgramById(int programId) {
        return programDetailMapper.mapTo(programRepository.findById(programId)
                .orElseThrow(
                        () -> new ProgramNotFoundException("program not found")));
    }

    @Override
    public ProgramDTO updateProgram(int programId) {
        Program program = programRepository.findById(programId).orElseThrow(
                () -> new ProgramNotFoundException("Program not found"));
        program.setActive(false);
        program.setDone(true);
        program.setUpdatedAt(new Date());
        program.setDoneAt(new Date());
        return programMapper.mapTo(programRepository.save(program));
    }

    @Override
    public void checkUserAvailability() {
        User currentUser = userRepository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        List<Program> list = programRepository.findByUserOrderByCreatedAtAsc(currentUser).stream()
                .filter(element -> element.isActive() == true)
                .toList();
        if (list.size() == 0) {
            throw new UnauthorizedNewProgramException("tidak bisa membuat program baru");
        }
    }

}
