package id.bangkit.facetrack.facetrack.repository;

import id.bangkit.facetrack.facetrack.entity.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OtpRepository extends JpaRepository<OTP, Integer> {
    Optional<OTP> findFirstByEmailAndIsUsedOrderByCreatedAtDesc(String email, boolean isUsed);
}
