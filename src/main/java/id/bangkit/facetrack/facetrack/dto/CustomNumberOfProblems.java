package id.bangkit.facetrack.facetrack.dto;

import id.bangkit.facetrack.facetrack.entity.Problem;

public record CustomNumberOfProblems(
        int problemNumberId,
        int jumlah,
        CustomProblemResponse problem
) {
}
