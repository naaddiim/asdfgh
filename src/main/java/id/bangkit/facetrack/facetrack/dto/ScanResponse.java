package id.bangkit.facetrack.facetrack.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.bangkit.facetrack.facetrack.entity.NumberOfProblems;
import id.bangkit.facetrack.facetrack.entity.Program;
import jakarta.persistence.*;
import lombok.Builder;

import java.util.Date;
import java.util.List;

public record ScanResponse(
        int scanId,
        String gambar,
        List<CustomNumberOfProblems> numberOfProblems
) {
}
