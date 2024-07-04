package id.bangkit.facetrack.facetrack.dto.response.programs;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import id.bangkit.facetrack.facetrack.dto.ProgramDTO;

@JsonPropertyOrder({ "status", "message", "data" })
public record AllProgramResponse(
        boolean status,
        String message,
        List<ProgramDTO> data) {
}
