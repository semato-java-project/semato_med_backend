package semato.semato_med.payload.visit;

import semato.semato_med.model.Clinic;

import java.util.LinkedList;
import java.util.List;

public class ClinicListBySpecialityResponse {

    private List<Clinic> clinicList;

    public ClinicListBySpecialityResponse(List<Clinic> clinicList) {
        this.clinicList = clinicList;
    }

    public List<ClinicResponse> getClinicList() {

        List<ClinicResponse> response = new LinkedList<ClinicResponse>();

        for (Clinic clinic: clinicList) {
            response.add(new ClinicResponse(clinic));
        }

        return response;
    }

}
