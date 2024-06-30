package id.bangkit.facetrack.facetrack.service.impl;

import id.bangkit.facetrack.facetrack.controller.FileController;
import id.bangkit.facetrack.facetrack.dto.request.CreateScanRequest;
import id.bangkit.facetrack.facetrack.entity.*;
import id.bangkit.facetrack.facetrack.exception.ProgramNotFoundException;
import id.bangkit.facetrack.facetrack.exception.SaveImageException;
import id.bangkit.facetrack.facetrack.repository.ProblemRepository;
import id.bangkit.facetrack.facetrack.repository.ProgramRepository;
import id.bangkit.facetrack.facetrack.repository.ScanRepository;
import id.bangkit.facetrack.facetrack.service.FileStorageService;
import id.bangkit.facetrack.facetrack.service.ScanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScanServiceImpl implements ScanService {
    private final ScanRepository scanRepository;
    private final ProgramRepository programRepository;
    private final ProblemRepository problemRepository;
    private final FileStorageService fileStorageService;

    @Override
    public Scan createScan(MultipartFile file, int programId, String problemId, String jumlah) {
        String pathGambar = "";
        try {
            pathGambar = MvcUriComponentsBuilder
                    .fromMethodName(FileController.class, "getFile", fileStorageService.save(file)).build().toString();
        } catch (Exception e) {
            throw new SaveImageException("Gambar tidak bisa di save");
        }
        String[] splitProblemId = problemId.split(",");
        String[] splitJumlah = jumlah.split(",");
        List<NumberOfProblems> numberOfProblemsz = new ArrayList<>();

        for (int i = 0; i < splitProblemId.length; i++) {
            Problem problem = Problem.builder().problemId(Integer.parseInt(splitProblemId[i])).build();
            NumberOfProblems numberOfProblems1 = NumberOfProblems
                    .builder()
                    .jumlah(Integer.parseInt(splitJumlah[i]))
                    .problem(problem)
                    .build();
            numberOfProblemsz.add(numberOfProblems1);
        }
        CreateScanRequest request = new CreateScanRequest(programId, pathGambar, numberOfProblemsz);

        Scan newScan = Scan.builder()
                .gambar(request.gambar())
                .build();
        Program programScan = programRepository.findById(request.programId()).orElseThrow(
                () -> new ProgramNotFoundException("Program tidak ditemukan")
        );
        List<NumberOfProblems> numberOfProblems = new ArrayList<>();
        request.numberOfProblems().stream().forEach(element -> {
            NumberOfProblems numberOfProblems1 = NumberOfProblems.builder().jumlah(element.getJumlah()).build();
            numberOfProblems1.setScan(newScan);
            Problem problem = problemRepository.findById(element.getProblem().getProblemId()).get();
            numberOfProblems1.setProblem(problem);
            numberOfProblems.add(numberOfProblems1);
        });
        newScan.setProgram(programScan);
        newScan.setNumberOfProblems(numberOfProblems);
        return scanRepository.save(newScan);
    }
}
