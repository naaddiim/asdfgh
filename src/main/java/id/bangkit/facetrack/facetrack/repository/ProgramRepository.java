package id.bangkit.facetrack.facetrack.repository;

import id.bangkit.facetrack.facetrack.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.bangkit.facetrack.facetrack.entity.Program;

import java.util.List;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Integer> {
    List<Program> findByUserOrderByCreatedAtAsc(User user);
}
