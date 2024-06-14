package id.bangkit.facetrack.facetrack.controller;

import id.bangkit.facetrack.facetrack.entity.FileInfo;
import id.bangkit.facetrack.facetrack.entity.NumberOfProblems;
import id.bangkit.facetrack.facetrack.service.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/file")
@Tag(name = "File", description = "Endpoint for serving static resources")
@Slf4j
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
