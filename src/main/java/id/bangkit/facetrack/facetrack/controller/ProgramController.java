package id.bangkit.facetrack.facetrack.controller;

import id.bangkit.facetrack.facetrack.dto.*;
import id.bangkit.facetrack.facetrack.dto.request.CreateProgramRequest;
import id.bangkit.facetrack.facetrack.dto.response.APIResponse;
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
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(APIResponse.generateResponse(checkResult, "Tidak Boleh membuat program baru", null));
        }
        return ResponseEntity.ok().body(APIResponse.generateResponse(checkResult, "Boleh membuat program baru", null));
    }

    @Operation(summary = "Create a new program", description = "Create a new program with skincare information")
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> postNewProgram(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A request to create a new program") @RequestBody @Valid CreateProgramRequest request) {
        Program newProgram = programService.createProgram(request);
        return APIResponse.generateResponse(true, "Berhasil buat program", newProgram);
    }

    @Operation(summary = "Update the program", description = "Update status active of a program")
    @PutMapping(value = "/{programId}")
    public Map<String, Object> updateFinishProgram(
            @Parameter(description = "programId that used to query on database") @PathVariable int programId) {
        Program updatedProgram = programService.updateProgram(programId);
        return APIResponse.generateResponse(true, "Program berhasil diupdate", updatedProgram);
    }

    @Operation(summary = "Get all program", description = "Get all information about all program")
    @GetMapping()
    public Map<String, Object> getAllProgram() {
        List<Program> list = programService.getAllProgram();
        return APIResponse.generateResponse(true, "List Semua program untuk user ini", list);
    }

    @Operation(summary = "Get program by id", description = "Get all information about program by id")
    @GetMapping("/{programId}")
    public Map<String, Object> getProgramById(@PathVariable int programId) {
        Program programResponse = programService.getProgramById(programId);
        return APIResponse.generateResponse(true, "List Semua program untuk user ini", programResponse);
    }
}
