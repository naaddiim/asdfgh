package id.bangkit.facetrack.facetrack.controller;

import id.bangkit.facetrack.facetrack.dto.ProblemDTO;
import id.bangkit.facetrack.facetrack.dto.response.APIResponse;
import id.bangkit.facetrack.facetrack.dto.response.problems.AllProblemResponse;
import id.bangkit.facetrack.facetrack.entity.Problem;
import id.bangkit.facetrack.facetrack.repository.ProblemRepository;
import id.bangkit.facetrack.facetrack.service.ProblemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
@Tag(name = "Problem", description = "Endpoint for problems")
public class ProblemController {
    private final ProblemService problemService;

    @Operation(summary = "Get all Problem", description = "get all information about all problem")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All Problem", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = AllProblemResponse.class)) }),
    })
    @GetMapping()
    public AllProblemResponse getAllProblem() {
        List<ProblemDTO> list = problemService.findAllProblem();
        return new AllProblemResponse(true, "List semua problem", list);
    }
}
