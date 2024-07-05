package id.bangkit.facetrack.facetrack.controller;

import id.bangkit.facetrack.facetrack.dto.ProgramDTO;
import id.bangkit.facetrack.facetrack.dto.ProgramDetailDTO;
import id.bangkit.facetrack.facetrack.dto.request.programs.CreateProgramRequest;
import id.bangkit.facetrack.facetrack.dto.response.programs.AllProgramResponse;
import id.bangkit.facetrack.facetrack.dto.response.programs.CheckAvailabilityResponse;
import id.bangkit.facetrack.facetrack.dto.response.programs.NewProgramResponse;
import id.bangkit.facetrack.facetrack.dto.response.programs.ProgramDetailResponse;
import id.bangkit.facetrack.facetrack.dto.response.programs.UpdateProgramResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import id.bangkit.facetrack.facetrack.service.ProgramService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/v1/programs")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Program", description = "Endpoint for program")
public class ProgramController {
    private final ProgramService programService;

    @Operation(summary = "Check User availability", description = "check wether the user can add a new program or not")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User can create new program", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CheckAvailabilityResponse.class))
            }),
    })
    @GetMapping("/availability")
    public CheckAvailabilityResponse canUserPostNewProgram() {
        programService.checkUserAvailability();
        return new CheckAvailabilityResponse(true, "Boleh membuat program baru");
    }

    @Operation(summary = "Create a new program", description = "Create a new program with skincare information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endpoint for creating new program", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NewProgramResponse.class))
            }),
    })
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public NewProgramResponse postNewProgram(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "A request to create a new program") @RequestBody @Valid CreateProgramRequest request) {
        ProgramDTO newProgram = programService.createProgram(request);
        return new NewProgramResponse(true, "Berhasil buat program", newProgram);
    }

    @Operation(summary = "Update the program", description = "Update status active of a program")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endpoint for update the status of program", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UpdateProgramResponse.class))
            }),
    })
    @PutMapping(value = "/{programId}")
    public UpdateProgramResponse updateFinishProgram(
            @Parameter(description = "programId that used to query on database") @PathVariable int programId) {
        ProgramDTO updatedProgram = programService.updateProgram(programId);
        return new UpdateProgramResponse(true, "Program berhasil diupdate", updatedProgram);
    }

    @Operation(summary = "Get all program", description = "Get all information about all program")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endpoint for get all programs based on logged in user", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AllProgramResponse.class))
            }),
    })
    @GetMapping()
    public AllProgramResponse getAllProgram() {
        List<ProgramDTO> list = programService.getAllProgram();
        return new AllProgramResponse(true, "List Semua program untuk user ini", list);
    }

    @Operation(summary = "Get program by id", description = "Get all information about program by id")
    @GetMapping("/{programId}")
    public ProgramDetailResponse getProgramById(@PathVariable int programId) {
        ProgramDetailDTO programResponse = programService.getProgramById(programId);
        return new ProgramDetailResponse(true, "List Semua program untuk user ini", programResponse);
    }
}
