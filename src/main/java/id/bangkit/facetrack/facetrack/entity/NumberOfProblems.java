package id.bangkit.facetrack.facetrack.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Builder
@Table(name = "number_of_problems")
public class NumberOfProblems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_number_of_problem")
    private int problemNumberId;

    private int jumlah;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "problem_id")
    @JsonBackReference(value = "problems")
    private Problem problem;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "scan_id")
    @JsonBackReference(value = "number-of-problems")
    private Scan scan;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date updatedAt = new Date();
}
