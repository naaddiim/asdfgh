package id.bangkit.facetrack.facetrack.dto.response.programs;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import id.bangkit.facetrack.facetrack.dto.ProgramDetailDTO;

@JsonPropertyOrder({ "status", "message", "data" })
public record ProgramDetailResponse(
        boolean status,
        String message,
        ProgramDetailDTO data) {
}
