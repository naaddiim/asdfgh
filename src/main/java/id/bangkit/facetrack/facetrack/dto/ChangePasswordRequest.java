package id.bangkit.facetrack.facetrack.dto;

public record ChangePasswordRequest(
        String email,
        String newPassword
) {
}
