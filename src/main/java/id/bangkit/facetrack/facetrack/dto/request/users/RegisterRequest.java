package id.bangkit.facetrack.facetrack.dto.request.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotEmpty(message = "Email tidak boleh kosong")
        @Email(message = "harus merupakan format email yang valid")
        String email,
        @NotEmpty(message = "password tidak boleh kosong")
        @Size(min = 8, message = "minimal panjang password adalah 8 karakter")
        String password
) {
}
