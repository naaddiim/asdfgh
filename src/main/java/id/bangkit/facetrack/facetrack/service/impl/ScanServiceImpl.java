package id.bangkit.facetrack.facetrack.service.impl;

import id.bangkit.facetrack.facetrack.controller.FileController;
import id.bangkit.facetrack.facetrack.dto.ScanDTO;
import id.bangkit.facetrack.facetrack.entity.*;
import id.bangkit.facetrack.facetrack.exception.InvalidScanInputException;
import id.bangkit.facetrack.facetrack.exception.ProgramNotFoundException;
import id.bangkit.facetrack.facetrack.exception.SaveImageException;
import id.bangkit.facetrack.facetrack.mappers.MapTo;
import id.bangkit.facetrack.facetrack.repository.ProblemRepository;
import id.bangkit.facetrack.facetrack.repository.ProgramRepository;
import id.bangkit.facetrack.facetrack.repository.ScanRepository;
import id.bangkit.facetrack.facetrack.service.FileStorageService;
import id.bangkit.facetrack.facetrack.service.ScanService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScanServiceImpl implements ScanService {
    private final ScanRepository scanRepository;
    private final ProgramRepository programRepository;
    private final ProblemRepository problemRepository;
    private final FileStorageService fileStorageService;
    private final MapTo<Scan, ScanDTO> scanMapper;

    @Override
    public ScanDTO createScan(MultipartFile file, int programId, String problemId, String jumlah) {
        String pathGambar = createPathGambar(file);
        List<NumberOfProblem> numberOfProblems = generateListNumberOfProblem(problemId, jumlah);
        Scan newScan = Scan.builder()
                .gambar(pathGambar)
                .build();
        Program program = programRepository
                .findById(programId).orElseThrow(
                        () -> new ProgramNotFoundException("Program tidak ditemukan"));
        List<NumberOfProblem> associatedNumberOfProblems = associateNumberOfProblemWithNewScan(newScan,
                numberOfProblems);
        newScan.setProgram(program);
        newScan.setNumberOfProblems(associatedNumberOfProblems);
        return scanMapper.mapTo(scanRepository.save(newScan));
    }

    private List<NumberOfProblem> associateNumberOfProblemWithNewScan(Scan newScan,
            List<NumberOfProblem> numberOfProblems) {
        List<NumberOfProblem> newNumberOfProblems = new ArrayList<>();
        numberOfProblems.stream().forEach(element -> {
            NumberOfProblem numberOfProblem = NumberOfProblem.builder().jumlah(element.getJumlah()).build();
            numberOfProblem.setScan(newScan);
            Problem problem = problemRepository.findById(element.getProblem().getProblemId()).get();
            numberOfProblem.setProblem(problem);
            newNumberOfProblems.add(numberOfProblem);
        });
        return newNumberOfProblems;
    }

    private List<NumberOfProblem> generateListNumberOfProblem(String problemId, String jumlah) {
        if (problemId.length() != jumlah.length()) {
            throw new InvalidScanInputException("panjang problemId harus sama dengan jumlah");
        }
        String[] splitProblemId = problemId.split(",");
        String[] splitJumlah = jumlah.split(",");
        List<NumberOfProblem> numberOfProblems = new ArrayList<>();
        for (int i = 0; i < splitProblemId.length; i++) {
            Problem problem = Problem.builder().problemId(Integer.parseInt(splitProblemId[i])).build();
            NumberOfProblem numberOfProblem = NumberOfProblem
                    .builder()
                    .jumlah(Integer.parseInt(splitJumlah[i]))
                    .problem(problem)
                    .build();
            numberOfProblems.add(numberOfProblem);
        }
        return numberOfProblems;
    }

    private String createPathGambar(MultipartFile file) {
        try {
            return MvcUriComponentsBuilder
                    .fromMethodName(FileController.class, "getFile", fileStorageService.save(file)).build().toString();
        } catch (Exception e) {
            throw new SaveImageException("Gambar tidak bisa di save");
        }
    }
}
