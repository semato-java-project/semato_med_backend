package semato.semato_med.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import semato.semato_med.payload.visit.ClinicListBySpecialityResponse;
import semato.semato_med.payload.visit.SpecialityListResponse;
import semato.semato_med.repository.SpecialityRepository;
import semato.semato_med.service.VisitHelper;

@RestController
@RequestMapping("/api/visit")
public class VisitController {

    @Autowired
    private SpecialityRepository specialityRepository;

    @Autowired
    private VisitHelper visitHelper;

    @GetMapping("/speciality/list/get")
    @PreAuthorize("hasRole('PATIENT')")
    public SpecialityListResponse getSpecialityList() {
        return new SpecialityListResponse(specialityRepository.findAll());
    }

    @GetMapping("/clinic/list/by/speciality/{specialityId}/get")
    @PreAuthorize("hasRole('PATIENT')")
    public ClinicListBySpecialityResponse getClinicListBySpeciality(@PathVariable Long specialityId) {
        return new ClinicListBySpecialityResponse(visitHelper.getClinicListBySpeciality(specialityRepository.findById(specialityId).get()));
    }

}
