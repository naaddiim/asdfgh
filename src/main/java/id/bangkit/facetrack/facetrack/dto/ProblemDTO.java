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
public class ProblemDTO {
    @JsonProperty("id")
    private int problemId;
    private String nama;
    private String deskripsi;
    private String saran;
}
