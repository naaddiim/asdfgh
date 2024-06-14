package id.bangkit.facetrack.facetrack.repository;

import id.bangkit.facetrack.facetrack.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {
}
