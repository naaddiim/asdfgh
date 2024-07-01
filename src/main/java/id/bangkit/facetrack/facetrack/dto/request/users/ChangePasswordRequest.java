package id.bangkit.facetrack.facetrack.dto.request.users;

public record ChangePasswordRequest(
        String email,
        String newPassword
) {
}
