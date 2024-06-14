package id.bangkit.facetrack.facetrack.controller;

import id.bangkit.facetrack.facetrack.dto.APIResponse;
import id.bangkit.facetrack.facetrack.dto.CustomProblemResponse;
import id.bangkit.facetrack.facetrack.entity.Problem;
import id.bangkit.facetrack.facetrack.repository.ProblemRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/problems")
@RequiredArgsConstructor
@Tag(name = "Problem", description = "Endpoint for problems")
public class ProblemController {
    private final ProblemRepository problemRepository;

    @Operation(summary = "Get all Problem", description = "get all information about all problem")
    @GetMapping()
    public Map<String, Object> getAllProblem(){
        List<CustomProblemResponse> list = problemRepository.findAll().stream().map(this::mapToProblemResponse).toList();
        return APIResponse.generateResponse(true, "List semua problem", list);
    }

    private CustomProblemResponse mapToProblemResponse(Problem problem) {
        return new CustomProblemResponse(problem.getProblemId(), problem.getNama(), problem.getDeskripsi(), problem.getSaran());
    }
}
