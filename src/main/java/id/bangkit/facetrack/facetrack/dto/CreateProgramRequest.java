package id.bangkit.facetrack.facetrack.dto;

import java.util.List;

import id.bangkit.facetrack.facetrack.entity.Scan;
import id.bangkit.facetrack.facetrack.entity.Skincare;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record CreateProgramRequest(
        @NotEmpty(message = "Nama program tidak boleh kosong")
        @Size(min = 8, message = "Panjang nama program minimal 8 karakter")
        String namaProgram,
        @Size(min = 1, message = "Skincare tidak boleh kosong")
        List<Skincare> skincare) {
}
