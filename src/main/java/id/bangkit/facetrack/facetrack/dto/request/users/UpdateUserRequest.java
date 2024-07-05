package id.bangkit.facetrack.facetrack.dto.request.users;

import id.bangkit.facetrack.facetrack.entity.Gender;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotEmpty(message = "Nama tidak boleh kosong")
        @Size(min = 4, message = "Panjang nama minimal 4 karakter")
        String nama,
        @NotEmpty(message = "nomor telepon tidak boleh kosong")
        @Size(min = 10, message = "Panjang nomor telepon minimal 10 karakter")
        String noTelp,
        @NotNull(message = "Gender tidak boleh kosong")
        Gender gender
) {
}
