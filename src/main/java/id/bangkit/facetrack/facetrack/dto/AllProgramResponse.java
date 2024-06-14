package id.bangkit.facetrack.facetrack.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.bangkit.facetrack.facetrack.entity.Scan;
import id.bangkit.facetrack.facetrack.entity.Skincare;
import id.bangkit.facetrack.facetrack.entity.User;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.Date;
import java.util.List;

public record AllProgramResponse(
        int programId,
        String namaProgram,
        boolean isDone,
        boolean isActive,
        CustomUserResponse user,
        Date createdAt,
        Date updatedAt,
        Date doneAt,
        List<SkincareResponse> skincare,
        List<ScanResponse> scan
) {
}
