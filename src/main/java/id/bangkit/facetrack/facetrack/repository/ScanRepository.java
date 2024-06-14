package id.bangkit.facetrack.facetrack.repository;

import id.bangkit.facetrack.facetrack.entity.Scan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanRepository extends JpaRepository<Scan, Integer> {
}
