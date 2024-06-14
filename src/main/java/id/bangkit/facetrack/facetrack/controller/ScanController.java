package id.bangkit.facetrack.facetrack.controller;

import id.bangkit.facetrack.facetrack.dto.APIResponse;
import id.bangkit.facetrack.facetrack.dto.CreateProgramRequest;
import id.bangkit.facetrack.facetrack.dto.CreateScanRequest;
import id.bangkit.facetrack.facetrack.entity.Program;
import id.bangkit.facetrack.facetrack.entity.Scan;
import id.bangkit.facetrack.facetrack.service.ScanService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> postNewScan(
            @RequestParam("file") MultipartFile file,
            @RequestParam("programId") int programId,
            @RequestParam("problemId") String problemId,
            @RequestParam("jumlah") String jumlah
    ) {
        Scan scan = scanService.createScan(file, programId, problemId, jumlah);
        return APIResponse.generateResponse(true, "Berhasil memposting scan", scan);
    }

}
