package id.bangkit.facetrack.facetrack.dto.response.errors;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.v3.oas.annotations.media.Schema;

@JsonPropertyOrder({ "status", "error" })
public record ErrorResponse(
        @Schema(example = "false") boolean status,
        @Schema(example = "some error messages") String error) {

}
