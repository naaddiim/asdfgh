package id.bangkit.facetrack.facetrack.controller;

import id.bangkit.facetrack.facetrack.dto.*;
import id.bangkit.facetrack.facetrack.entity.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import id.bangkit.facetrack.facetrack.service.ProgramService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/programs")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Program", description = "Endpoint for program")
public class ProgramController {
    private final ProgramService programService;

    @Operation(summary = "Check User availability", description = "check wether the user can add a new program or not")
    @GetMapping("/availability")
    public ResponseEntity<Map<String, Object>> canUserPostNewProgram() {
        boolean checkResult = programService.checkUserAvailability();
        if (!checkResult) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(APIResponse.generateResponse(checkResult, "Tidak Boleh membuat program baru", null));
        }
        return ResponseEntity.ok().body(APIResponse.generateResponse(checkResult, "Boleh membuat program baru", null));
    }

    @Operation(summary = "Create a new program", description = "Create a new program with skincare information")
    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> postNewProgram(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A request to create a new program")
            @RequestBody @Valid CreateProgramRequest request) {
        Program newProgram = programService.createProgram(request);
        return APIResponse.generateResponse(true, "Berhasil buat program", newProgram);
    }

    @Operation(summary = "Update the program", description = "Update status active of a program")
    @PutMapping(value = "/{programId}")
    public Map<String, Object> updateFinishProgram(
            @Parameter(description = "programId that used to query on database")
            @PathVariable int programId) {
        Program updatedProgram = programService.updateProgram(programId);
        return APIResponse.generateResponse(true, "Program berhasil diupdate", updatedProgram);
    }


    @Operation(summary = "Get all program", description = "Get all information about all program")
    @GetMapping()
    public Map<String, Object> getAllProgram() {
        List<AllProgramResponse> list = programService.getAllProgram().stream()
                .map(this::mapToResponse)
                .toList();
        return APIResponse.generateResponse(true, "List Semua program untuk user ini", list);
    }

    @Operation(summary = "Get program by id", description = "Get all information about program by id")
    @GetMapping("/{programId}")
    public Map<String, Object> getProgramById(@PathVariable int programId) {
        AllProgramResponse programResponse = mapToResponse(programService.getProgramById(programId));
        return APIResponse.generateResponse(true, "List Semua program untuk user ini", programResponse);
    }

    private AllProgramResponse mapToResponse(Program program) {
        List<SkincareResponse> skincares = program.getSkincare().stream()
                .map(this::mapToSkinCareRespose)
                .toList();

        List<ScanResponse> scans = program.getScan().stream()
                .map(this::mapToScanResponse)
                .toList();
        return new AllProgramResponse(
                program.getProgramId(),
                program.getNamaProgram(),
                program.isDone(),
                program.isActive(),
                mapToUserResponse(program.getUser()),
                program.getCreatedAt(),
                program.getUpdatedAt(),
                program.getDoneAt(),
                skincares,
                scans
        );
    }

    private ScanResponse mapToScanResponse(Scan scan) {
        List<CustomNumberOfProblems> customNumberOfProblems = scan.getNumberOfProblems()
                .stream()
                .map(this::mapToNumberOfProblemsResponse)
                .toList();
        return new ScanResponse(scan.getScanId(),
                scan.getGambar(),
                customNumberOfProblems
        );
    }

    private CustomNumberOfProblems mapToNumberOfProblemsResponse(NumberOfProblems numberOfProblems) {
        return new CustomNumberOfProblems(
                numberOfProblems.getProblemNumberId(),
                numberOfProblems.getJumlah(),
                mapToProblemResponse(numberOfProblems.getProblem())
        );
    }

    private CustomProblemResponse mapToProblemResponse(Problem problem) {
        return new CustomProblemResponse(
                problem.getProblemId(),
                problem.getNama(),
                problem.getDeskripsi(),
                problem.getSaran()
        );
    }

    private SkincareResponse mapToSkinCareRespose(Skincare skincare) {
        return new SkincareResponse(skincare.getSkincareId(), skincare.getNama());
    }

    private CustomUserResponse mapToUserResponse(User user) {
        return new CustomUserResponse(
                user.getUserId(),
                user.getEmail(),
                user.getNama(),
                user.getGender(),
                user.getNoTelp(),
                user.getRole()
        );
    }
}
