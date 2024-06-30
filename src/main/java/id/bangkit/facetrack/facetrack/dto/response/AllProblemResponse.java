package id.bangkit.facetrack.facetrack.dto.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import id.bangkit.facetrack.facetrack.dto.ProblemDTO;

@JsonPropertyOrder({ "status", "message", "data" })
public record AllProblemResponse(
        boolean status,
        String message,
        List<ProblemDTO> data) {
}
