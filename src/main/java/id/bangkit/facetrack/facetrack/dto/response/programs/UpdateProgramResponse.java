package id.bangkit.facetrack.facetrack.dto.response.programs;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import id.bangkit.facetrack.facetrack.dto.ProgramDTO;

@JsonPropertyOrder({ "status", "message", "data" })
public record UpdateProgramResponse(
    boolean status,
    String message,
    ProgramDTO data
) {

}
