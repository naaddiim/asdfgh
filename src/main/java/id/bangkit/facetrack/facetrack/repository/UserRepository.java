package id.bangkit.facetrack.facetrack.repository;

import id.bangkit.facetrack.facetrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
}
