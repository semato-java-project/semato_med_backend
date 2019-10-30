package semato.semato_med.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Physician {

    @Id
    private long id;

    private String medicalDegrees;

    private String title;

    private String note;

    private String image_url;

    @OneToOne
    @JoinColumn (name = "id", referencedColumnName = "id")
    @MapsId
    private User user;

    @ManyToMany
    @JoinTable(
            name = "physician_speciality",
            joinColumns = {@JoinColumn(name = "physician_id")},
            inverseJoinColumns = {@JoinColumn(name = "speciality_id")}
    )
    private Set<Speciality> specialities;

}
