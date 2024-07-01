package id.bangkit.facetrack.facetrack.dto.response.users;

import id.bangkit.facetrack.facetrack.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "status", "message", "data" })
public record CurrentUserResponse(
                boolean status,
                String message,
                UserDTO data) {
}
