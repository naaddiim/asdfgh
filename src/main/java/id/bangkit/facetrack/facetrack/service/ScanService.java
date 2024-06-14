package id.bangkit.facetrack.facetrack.service;


import id.bangkit.facetrack.facetrack.dto.CreateScanRequest;
import id.bangkit.facetrack.facetrack.entity.Program;
import id.bangkit.facetrack.facetrack.entity.Scan;
import org.springframework.web.multipart.MultipartFile;

public interface ScanService {
    Scan createScan(MultipartFile file, int programId, String problemId, String jumlah);
}
