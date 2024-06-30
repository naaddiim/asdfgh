package id.bangkit.facetrack.facetrack.dto.request;

public record ForgotPasswordRequest(
        String email,
        String otp
) {
}
