package id.bangkit.facetrack.facetrack.entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "programs")
public class Program {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_program")
    private int programId;

    @Column(name = "nama_program")
    private String namaProgram;

    @Column(name = "is_done")
    @Builder.Default
    private boolean isDone = false;

    @Column(name = "is_active")
    @Builder.Default
    private boolean isActive = true;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    @JsonBackReference(value = "programs")
    private User user;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date updatedAt = new Date();

    @Column(name = "done_at")
    @Temporal(TemporalType.TIMESTAMP)
    @Builder.Default
    private Date doneAt = null;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "program", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "skincares")
    private List<Skincare> skincares;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "program", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "scans")
    private List<Scan> scans;

}
