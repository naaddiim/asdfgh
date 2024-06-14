package id.bangkit.facetrack.facetrack.dto;

public record ForgotPasswordRequest(
        String email,
        String otp
) {
}
