package id.bangkit.facetrack.facetrack.dto;

import id.bangkit.facetrack.facetrack.entity.Gender;
import id.bangkit.facetrack.facetrack.entity.Program;
import id.bangkit.facetrack.facetrack.entity.Role;

import java.util.List;

public record CustomUserResponse(
        int userId,
        String email,
        String nama,
        Gender gender,
        String noTelp,
        Role role
) {
}
