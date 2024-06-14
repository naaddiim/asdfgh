package id.bangkit.facetrack.facetrack.dto;

public record CustomProblemResponse(
        int problemId,
        String nama,
        String deskrpsi,
        String saran
) {
}
