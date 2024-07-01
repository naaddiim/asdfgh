package id.bangkit.facetrack.facetrack.dto.response.users;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "status", "message", "accessToken" })
public record AuthenticationResponse(
                boolean status,
                String message,
                @JsonProperty("access_token") 
                String accessToken) {
}
