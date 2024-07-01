package id.bangkit.facetrack.facetrack.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import id.bangkit.facetrack.facetrack.entity.Gender;
import id.bangkit.facetrack.facetrack.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonPropertyOrder({ "id" })
public class UserDTO {
    @JsonProperty("id")
    private int userId;
    private String nama;
    private String email;
    @JsonProperty("no_telp")
    private String noTelp;
    private Role role;
    private Gender gender;
    private List<ProgramDTO> programs;
}
