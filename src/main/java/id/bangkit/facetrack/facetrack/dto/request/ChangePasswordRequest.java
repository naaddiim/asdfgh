package id.bangkit.facetrack.facetrack.dto.request;

public record ChangePasswordRequest(
        String email,
        String newPassword
) {
}
