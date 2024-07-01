package id.bangkit.facetrack.facetrack.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonPropertyOrder({ "id" })
public class ProgramDTO {
    @JsonProperty("id")
    private int programId;
    private String namaProgram;
    private boolean isDone = false;
    private boolean isActive = true;
}
