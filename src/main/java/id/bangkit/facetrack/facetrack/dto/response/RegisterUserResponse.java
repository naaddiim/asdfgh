package id.bangkit.facetrack.facetrack.dto.response;

import id.bangkit.facetrack.facetrack.entity.Gender;
import id.bangkit.facetrack.facetrack.entity.Role;

public record RegisterUserResponse(
        int id,
        String email,
        String accessToken
) {
}
