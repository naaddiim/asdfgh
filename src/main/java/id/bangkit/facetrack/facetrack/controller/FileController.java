package id.bangkit.facetrack.facetrack.controller;

import id.bangkit.facetrack.facetrack.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
@Tag(name = "File", description = "Endpoint for serving static resources")
public class FileController {
    private final FileStorageService fileStorageService;

    @Operation(summary = "URL for static files", description = "URL endpoint to serve images")
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> getFile(@PathVariable String filename) {
        MediaType contentType = MediaType.IMAGE_PNG;
        Resource file = fileStorageService.load(filename);
        return ResponseEntity.ok().contentType(contentType)
                .body(new InputStreamResource(file));
    }

}
