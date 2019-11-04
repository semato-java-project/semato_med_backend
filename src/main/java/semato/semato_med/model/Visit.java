package semato.semato_med.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn (name = "patient_id", referencedColumnName = "id")
    private Patient patient;

    @ManyToOne
    @JoinColumn (name = "physician_id", referencedColumnName = "id")
    private Physician physician;

    @ManyToOne
    @JoinColumn (name = "clinic_id", referencedColumnName = "id")
    private Clinic clinic;

    private LocalDateTime dateTime;

    private VisitStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

}