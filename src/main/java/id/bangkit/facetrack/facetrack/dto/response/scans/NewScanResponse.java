package id.bangkit.facetrack.facetrack.dto.response.scans;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import id.bangkit.facetrack.facetrack.dto.ScanDTO;

@JsonPropertyOrder({ "status", "message", "data" })
public record NewScanResponse(
    boolean status,
    String message,
    ScanDTO data
) {

}
