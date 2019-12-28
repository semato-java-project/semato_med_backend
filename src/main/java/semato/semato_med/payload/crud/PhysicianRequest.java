package semato.semato_med.payload.crud;

import lombok.Getter;
import lombok.NonNull;
import semato.semato_med.model.Speciality;

import javax.validation.constraints.NotBlank;
import java.util.List;


@Getter
public class PhysicianRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String phone;

    @NotBlank
    private String medicalDegrees;

    @NotBlank
    private String title;

    private String note;

    private String image_url;

    private List<Long> specialitiesIds;
}
