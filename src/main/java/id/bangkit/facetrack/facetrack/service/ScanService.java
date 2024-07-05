package id.bangkit.facetrack.facetrack.service;


import id.bangkit.facetrack.facetrack.dto.ScanDTO;
import org.springframework.web.multipart.MultipartFile;

public interface ScanService {
    ScanDTO createScan(MultipartFile file, int programId, String problemId, String jumlah);
}
