package id.bangkit.facetrack.facetrack.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonPropertyOrder({ "id" })
public class ScanDTO {
    @JsonProperty("id")
    private int scanId;
    private String gambar;
    private List<NumberOfProblemDTO> numberOfProblems;
    private Date createdAt;
}
