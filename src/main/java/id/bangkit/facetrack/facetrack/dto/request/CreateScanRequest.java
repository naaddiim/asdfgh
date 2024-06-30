package id.bangkit.facetrack.facetrack.dto.request;

import id.bangkit.facetrack.facetrack.entity.NumberOfProblems;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateScanRequest(
        @Min(value = 1, message = "programId tidak boleh kosong")
        int programId,
        @NotEmpty(message = "gambar tidak boleh kosong")
        String gambar,
        @Size(min = 1, message = "Problems tidak boleh kosong")
        List<NumberOfProblems> numberOfProblems
) {
}
