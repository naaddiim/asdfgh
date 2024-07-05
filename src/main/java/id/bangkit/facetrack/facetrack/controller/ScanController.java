package id.bangkit.facetrack.facetrack.controller;

import id.bangkit.facetrack.facetrack.dto.ScanDTO;
import id.bangkit.facetrack.facetrack.dto.response.scans.NewScanResponse;
import id.bangkit.facetrack.facetrack.service.ScanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/scans")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Scan Problem", description = "Endpoint for scan")
public class ScanController {
    private final ScanService scanService;

    @Operation(summary = "Create a new Scan", description = "create a new scan based on programId and list of problems occured")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Creating new scan associated with programs", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = NewScanResponse.class))
            }),
    })
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public NewScanResponse postNewScan(
            @Parameter(description = "image file with a format of png") @RequestParam("file") MultipartFile file,
            @Parameter(description = "Program Id that this scan associated with") @RequestParam("programId") int programId,
            @Parameter(description = "multiple number value of problemId inside one string seperated with \" , \" ") @RequestParam("problemId") String problemId,
            @Parameter(description = "multiple number value amount of a problem occured inside one string seperated with \" , \" ") @RequestParam("jumlah") String jumlah) {
        ScanDTO newScan = scanService.createScan(file, programId, problemId, jumlah);
        return new NewScanResponse(true, "Berhasil memposting scan", newScan);
    }

}
