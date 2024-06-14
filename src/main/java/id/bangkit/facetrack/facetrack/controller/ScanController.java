package id.bangkit.facetrack.facetrack.controller;

import id.bangkit.facetrack.facetrack.dto.APIResponse;
import id.bangkit.facetrack.facetrack.dto.CreateProgramRequest;
import id.bangkit.facetrack.facetrack.dto.CreateScanRequest;
import id.bangkit.facetrack.facetrack.entity.Program;
import id.bangkit.facetrack.facetrack.entity.Scan;
import id.bangkit.facetrack.facetrack.service.ScanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/scans")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Scan Problem", description = "Endpoint for scan")
public class ScanController {
    private final ScanService scanService;

    @Operation(summary = "Create a new Scan", description = "create a new scan based on programId and list of problems occured")
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> postNewScan(
            @Parameter(description = "image file with a format of png")
            @RequestParam("file") MultipartFile file,
            @Parameter(description = "Program Id that this scan associated with")
            @RequestParam("programId") int programId,
            @Parameter(description = "multiple number value of problemId inside one string seperated with \" , \" ")
            @RequestParam("problemId") String problemId,
            @Parameter(description = "multiple number value amount of a problem occured inside one string seperated with \" , \" ")
            @RequestParam("jumlah") String jumlah
    ) {
        Scan scan = scanService.createScan(file, programId, problemId, jumlah);
        return APIResponse.generateResponse(true, "Berhasil memposting scan", scan);
    }

}
