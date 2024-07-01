package id.bangkit.facetrack.facetrack.dto.request.users;

public record ForgotPasswordRequest(
        String email,
        String otp
) {
}
