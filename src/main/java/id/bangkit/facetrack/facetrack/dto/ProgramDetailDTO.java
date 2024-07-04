package id.bangkit.facetrack.facetrack.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import id.bangkit.facetrack.facetrack.entity.Scan;
import id.bangkit.facetrack.facetrack.entity.Skincare;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonPropertyOrder({ "id" })
public class ProgramDetailDTO {
    @JsonProperty("id")
    private int programId;
    private String namaProgram;
    private boolean isDone = false;
    private boolean isActive = true;
    private List<Skincare> skincares;
    private List<Scan> scans;
}
