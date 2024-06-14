package id.bangkit.facetrack.facetrack.dto;

import id.bangkit.facetrack.facetrack.entity.Gender;
import id.bangkit.facetrack.facetrack.entity.Role;

public record RegisterUserResponse(
        int id,
        String email,
        String accessToken
) {
}
