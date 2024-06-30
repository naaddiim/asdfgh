package id.bangkit.facetrack.facetrack.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "problems")
public class Problem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "id_problem")
    private int problemId;
    @Column(name = "nama_problem")
    private String nama;
    private String deskripsi;
    private String saran;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "problem", cascade = CascadeType.ALL) @JsonManagedReference(value = "problems")
    private List<NumberOfProblems> numberOfProblems;

    @Column(name = "created_at") @Temporal(TemporalType.TIMESTAMP) @Builder.Default
    private Date createdAt = new Date();

    @Column(name = "updated_at") @Temporal(TemporalType.TIMESTAMP) @Builder.Default
    private Date updatedAt = new Date();
}
