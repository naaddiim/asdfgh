package id.bangkit.facetrack.facetrack.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import id.bangkit.facetrack.facetrack.entity.Skincare;

@Repository
public interface SkincareRepository extends JpaRepository<Skincare, Integer>{
    
}
